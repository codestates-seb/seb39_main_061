package com.project.QR.security.jwt;

import com.project.QR.security.MemberDetails;
import com.project.QR.dto.TokenDto;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {
  @Value("${app.auth.token.secret-key}")
  private String SECRET_KEY;
  @Value("${app.auth.token.refresh-cookie-key}")
  private String COOKIE_REFRESH_TOKEN_KEY;
  private final Long ACCESS_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60;		// 1hour
  private final Long REFRESH_TOKEN_EXPIRE_LENGTH = 1000L * 60 * 60 * 24 * 7;	// 1week
  private final String AUTHORITIES_KEY = "role";
  private final RedisTemplate<String, Object> redisTemplate;

  @PostConstruct
  protected void init() {
    this.SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
  }

  public TokenDto.TokenInfoDto createToken(Authentication authentication, HttpServletResponse response) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH);

    MemberDetails member = (MemberDetails) authentication.getPrincipal();

    String email = member.getUsername();
    String role = authentication.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));

    String accessToken = Jwts.builder()
      .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
      .setSubject(email)
      .claim(AUTHORITIES_KEY, role)
      .setIssuedAt(now)
      .setExpiration(validity)
      .compact();

    String refreshToken = Jwts.builder()
      .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
      .setIssuedAt(now)
      .setExpiration(validity)
      .compact();

    saveRefreshToken(authentication, refreshToken);

    ResponseCookie cookie = ResponseCookie.from(COOKIE_REFRESH_TOKEN_KEY, refreshToken)
      .httpOnly(true)
      .secure(true)
      .sameSite("Lax")
      .maxAge(REFRESH_TOKEN_EXPIRE_LENGTH/1000)
      .path("/")
      .build();

    response.addHeader("Set-Cookie", cookie.toString());

    return TokenDto.TokenInfoDto.builder()
      .accessToken(accessToken)
      .accessTokenExpiredAt(ACCESS_TOKEN_EXPIRE_LENGTH)
      .grantType("Bearer")
      .build();
  }

  public String createAccessToken(Authentication authentication) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH);

    MemberDetails member = (MemberDetails) authentication.getPrincipal();

    String email = member.getUsername();
    String role = authentication.getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .collect(Collectors.joining(","));

    return Jwts.builder()
      .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
      .setSubject(email)
      .claim(AUTHORITIES_KEY, role)
      .setIssuedAt(now)
      .setExpiration(validity)
      .compact();
  }

  public void createRefreshToken(Authentication authentication, HttpServletResponse response) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_LENGTH);

    String refreshToken = Jwts.builder()
      .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
      .setIssuedAt(now)
      .setExpiration(validity)
      .compact();

    saveRefreshToken(authentication, refreshToken);

    ResponseCookie cookie = ResponseCookie.from(COOKIE_REFRESH_TOKEN_KEY, refreshToken)
      .httpOnly(true)
      .secure(true)
      .sameSite("Lax")
      .maxAge(REFRESH_TOKEN_EXPIRE_LENGTH/1000)
      .path("/")
      .build();

    response.addHeader("Set-Cookie", cookie.toString());
  }

  private void saveRefreshToken(Authentication authentication, String refreshToken) {
    MemberDetails member = (MemberDetails) authentication.getPrincipal();
    String email = member.getUsername();

    redisTemplate.opsForValue()
      .set(email, refreshToken, REFRESH_TOKEN_EXPIRE_LENGTH, TimeUnit.MILLISECONDS);
  }

  // Access Token을 검사하고 얻은 정보로 Authentication 객체 생성
  public Authentication getAuthentication(String accessToken) {
    Claims claims = parseClaims(accessToken);

    Collection<? extends GrantedAuthority> authorities =
      Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    MemberDetails principal = new MemberDetails(Long.valueOf(claims.getSubject()), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, "", authorities);
  }

  public Boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
      return true;
    } catch (ExpiredJwtException e) {
      log.info("만료된 JWT 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("지원되지 않는 JWT 토큰입니다.");
    } catch (IllegalStateException e) {
      log.info("JWT 토큰이 잘못되었습니다");
    }
    return false;
  }

  // Access Token 만료시 갱신때 사용할 정보를 얻기 위해 Claim 리턴
  public Claims parseClaims(String accessToken) {
    try {
      return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(accessToken).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
}
