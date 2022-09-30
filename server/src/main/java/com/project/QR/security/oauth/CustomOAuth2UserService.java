package com.project.QR.security.oauth;

<<<<<<< HEAD
import com.project.QR.exception.OAuthProcessingException;
import com.project.QR.member.entity.AuthProvider;
=======
import com.project.QR.business.entity.Business;
import com.project.QR.exception.OAuthProcessingException;
import com.project.QR.member.entity.AuthProvider;
import com.project.QR.member.entity.EmailVerified;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
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

<<<<<<< HEAD
=======
import java.util.Map;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
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

<<<<<<< HEAD
    if (optionalMember.isPresent()) {		// 이미 가입된 경우
      member = optionalMember.get();
      if (authProvider != member.getProvider()) {
        throw new OAuthProcessingException("Wrong Match Auth Provider");
      }
    } else {			// 가입되지 않은 경우
=======
    if (optionalMember.isPresent()) {
      member = optionalMember.get();
      if (authProvider != member.getProvider()) {
        if(member.getEmailVerified().equals(EmailVerified.Y))
          member = updateMember(member, authProvider);
        else
          member = updateInvalidMember(member, userInfo, authProvider);
      }
    } else {
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
      member = createMember(userInfo, authProvider);
    }
    return MemberDetails.create(member, oAuth2User.getAttributes());
  }

<<<<<<< HEAD
=======
  private Member updateInvalidMember(Member member, OAuth2UserInfo userInfo, AuthProvider authProvider) {
    member.setEmailVerified(EmailVerified.Y);
    member.setProvider(authProvider);
    member.setRole("ROLE_RESERVATION");
    member.setProfileImg(userInfo.getImageUrl());
    return member;
  }

>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
  private Member createMember(OAuth2UserInfo userInfo, AuthProvider authProvider) {
    Member member = Member.builder()
      .email(userInfo.getEmail())
      .profileImg(userInfo.getImageUrl())
      .role("ROLE_GUEST")
      .provider(authProvider)
<<<<<<< HEAD
      .build();
    return memberRepository.save(member);
  }
=======
      .emailVerified(EmailVerified.N)
      .build();
    return memberRepository.save(member);
  }

  private Member updateMember(Member member, AuthProvider authProvider) {
    member.setProvider(authProvider);
    Business business = member.getBusiness();
    member.setBusiness(business);
    return memberRepository.save(member);
  }
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
}

