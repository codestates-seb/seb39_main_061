package com.project.QR.config;

import com.project.QR.security.MemberDetailsService;
import com.project.QR.security.RestAuthenticationEntryPoint;
import com.project.QR.security.jwt.JwtAccessDeniedHandler;
import com.project.QR.security.jwt.JwtAuthenticationEntryPoint;
import com.project.QR.security.jwt.TokenAuthenticationFilter;
import com.project.QR.security.jwt.TokenProvider;
import com.project.QR.security.oauth.CustomOAuth2UserService;
import com.project.QR.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository;
import com.project.QR.security.oauth.OAuth2AuthenticationFailureHandler;
import com.project.QR.security.oauth.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
  securedEnabled = true,
  jsr250Enabled = true,
  prePostEnabled = true
)
public class SecurityConfig {
  private final CorsFilter corsFilter;
  private final TokenProvider tokenProvider;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
  private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
  private final CustomOAuth2UserService customOAuth2UserService;
  private final MemberDetailsService memberDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(memberDetailsService).passwordEncoder(passwordEncoder());
    AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

    http
      .cors()
      .and()
      .apply(new CustomDsl())
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .csrf().disable()
      .headers().frameOptions().disable()
      .and()
      .formLogin().disable()
      .httpBasic().disable()
      .exceptionHandling()
      .authenticationEntryPoint(new RestAuthenticationEntryPoint())
      .and()
      .authorizeRequests()
      .antMatchers("/auth/**", "/oauth2/**")
      .permitAll()
      .antMatchers("/api/v1/**")
      .access("hasRole('ROLE_RESERVATION')")
      .anyRequest()
      .permitAll()
      .and()
      .authenticationManager(authenticationManager)
      .oauth2Login()
      .authorizationEndpoint()
      .baseUri("/login/oauth2/authorize")
      .authorizationRequestRepository(cookieAuthorizationRequestRepository())
      .and()
      .redirectionEndpoint()
      .baseUri("/login/oauth2/callback/*")
      .and()
      .userInfoEndpoint()
      .userService(customOAuth2UserService)
      .and()
      .successHandler(oAuth2AuthenticationSuccessHandler)
      .failureHandler(oAuth2AuthenticationFailureHandler);


    http.exceptionHandling()
      .authenticationEntryPoint(jwtAuthenticationEntryPoint)	// 401
      .accessDeniedHandler(jwtAccessDeniedHandler);		// 403
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public TokenAuthenticationFilter tokenAuthenticationFilter() {
    return new TokenAuthenticationFilter(tokenProvider);
  }

  @Bean
  public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
    return new HttpCookieOAuth2AuthorizationRequestRepository();
  }

  public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {

    @Override
    public void configure(HttpSecurity builder) throws Exception {
      AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
      builder
        .addFilter(corsFilter)
        .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
  }
}
