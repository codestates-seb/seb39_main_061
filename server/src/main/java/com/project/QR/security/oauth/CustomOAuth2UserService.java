package com.project.QR.security.oauth;

import com.project.QR.exception.OAuthProcessingException;
import com.project.QR.member.entity.AuthProvider;
import com.project.QR.member.entity.EmailVerified;
import com.project.QR.member.entity.Member;
import com.project.QR.member.repository.MemberRepository;
import com.project.QR.security.MemberDetails;
import com.project.QR.security.oauth.user.OAuth2UserInfo;
import com.project.QR.security.oauth.user.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
  private final MemberRepository memberRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

    return process(oAuth2UserRequest, oAuth2User);
  }

  private OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
    AuthProvider authProvider = AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toLowerCase());
    OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(authProvider, oAuth2User.getAttributes());

    if (userInfo.getEmail().isEmpty()) {
      throw new OAuthProcessingException("Email not found from OAuth2 provider");
    }
    Optional<Member> optionalMember = memberRepository.findByEmail(userInfo.getEmail());
    Member member;

    if (optionalMember.isPresent()) {		// 이미 가입된 경우
      member = optionalMember.get();
      if (authProvider != member.getProvider()) {
        throw new OAuthProcessingException("Wrong Match Auth Provider");
      }
    } else {			// 가입되지 않은 경우
      member = createMember(userInfo, authProvider);
    }
    return MemberDetails.create(member, oAuth2User.getAttributes());
  }

  private Member createMember(OAuth2UserInfo userInfo, AuthProvider authProvider) {
    Member member = Member.builder()
      .email(userInfo.getEmail())
      .profileImg(userInfo.getImageUrl())
      .role("ROLE_GUEST")
      .provider(authProvider)
      .emailVerified(EmailVerified.N)
      .build();
    return memberRepository.save(member);
  }
}

