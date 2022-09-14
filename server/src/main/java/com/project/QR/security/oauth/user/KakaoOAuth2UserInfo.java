package com.project.QR.security.oauth.user;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo{

  /** 카카오는 Integer 로 받아져서 그런지 (String) 또는 (Long) 으로 cascading 이 되지 않는다... 그래서 Integer 로 받아준다 */
  private Long id;

  public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
    super((Map<String, Object>) attributes.get("kakao_account"));
    this.id = Long.parseLong(String.valueOf(attributes.get("id")));
  }

  @Override
  public String getId() {
    return String.valueOf(this.id);
  }

  @Override
  public String getName() {
    return (String) ((Map<String, Object>) attributes.get("profile")).get("nickname");
  }

  @Override
  public String getEmail() {
    return (String) attributes.get("email");
  }

  @Override
  public String getImageUrl() {
    return (String) ((Map<String, Object>) attributes.get("profile")).get("thumbnail_image_url");
  }
}
