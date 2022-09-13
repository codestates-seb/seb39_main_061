package com.project.QR.security.oauth.user;

import com.project.QR.member.entity.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

  public static OAuth2UserInfo getOAuth2UserInfo(AuthProvider authProvider, Map<String, Object> attributes) {
    switch (authProvider) {
      case google: return new GoogleOAuth2UserInfo(attributes);
      case naver: return new NaverOAuth2UserInfo(attributes);
      case kakao: return new KakaoOAuth2UserInfo(attributes);
      default: throw new IllegalArgumentException("Invalid Provider Type.");
    }
  }
}