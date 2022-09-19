package com.project.QR.security.jwt;

import com.project.QR.dto.TokenDto;
import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.member.entity.Member;
import com.project.QR.security.MemberDetails;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
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
  private final UserDetailsService userDetailsService;

  @PostConstruct
  protected void init() {
    this.SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
  }

  public TokenDto.TokenInfoDto createToken(Member member, HttpServletResponse response) {
    Claims claims = Jwts.claims().setSubject(member.getEmail());
    claims.put(AUTHORITIES_KEY, member.getRole());
    Date now = new Date();
    Date validity = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_LENGTH);

    String accessToken = Jwts.builder()
      .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
      .setClaims(claims)
      .setIssuedAt(now)
      .setExpiration(validity)
      .compact();

    String refreshToken = Jwts.builder()
      .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
      .setIssuedAt(now)
      .setExpiration(validity)
      .compact();

    saveRefreshToken(member.getEmail(), refreshToken);

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
      .grantType("Bearer")
      .build();
  }

  public TokenDto.TokenInfoDto createAccessToken(Authentication authentication) {
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
    return TokenDto.TokenInfoDto.builder()
      .accessToken(accessToken)
      .grantType("Bearer")
      .build();
  }

  private void saveRefreshToken(String email, String refreshToken) {
    redisTemplate.opsForValue()
      .set(email, refreshToken, REFRESH_TOKEN_EXPIRE_LENGTH, TimeUnit.MILLISECONDS);
    redisTemplate.expire(email, REFRESH_TOKEN_EXPIRE_LENGTH, TimeUnit.MILLISECONDS);
  }

  public Authentication getAuthentication(String accessToken) {
    Claims claims = parseClaims(accessToken);

    if(claims.get(AUTHORITIES_KEY) == null)
      throw new BusinessLogicException(ExceptionCode.ROLE_IS_NOT_EXISTS);
    UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());

    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
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

  public Claims parseClaims(String accessToken) {
    try {
      return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(accessToken).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
}
