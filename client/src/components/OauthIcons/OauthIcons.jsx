import React from "react";

import naverLogo from "../../assets/naver-logo.png";
import kakaoLogo from "../../assets/kakao-logo.png";
import googleLogo from "../../assets/google-logo.png";
import { baseURL, clientURL } from "../../api/axios";
import styled from "styled-components";

const OauthIcons = () => {
  const path = {
    kakao: `${baseURL}/login/oauth2/authorize/kakao?redirect_uri=${clientURL}/oauth2/redirect`,
    google: `${baseURL}/login/oauth2/authorize/google?redirect_uri=${clientURL}/oauth2/redirect`,
  };
  return (
    <Container>
      <div>
        <a href="#!">
          <Icon src={naverLogo} alt="naver logo" />
        </a>
      </div>
      <div>
        <a href={path.kakao}>
          <Icon src={kakaoLogo} alt="kakao logo" />
        </a>
      </div>
      <div>
        <a href={path.google}>
          <Icon src={googleLogo} alt="google logo" />
        </a>
      </div>
    </Container>
  );
};

export default OauthIcons;

const Container = styled.div`
  display: flex;
  width: 100%;
  justify-content: center;
  gap: 25px;
`;

const Icon = styled.img`
  width: 45px;
  height: 45px;
`;
