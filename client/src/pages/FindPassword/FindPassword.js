import React from "react";
import styled from "styled-components";
import { Link, useNavigate } from "react-router-dom";
import { useRef, useState } from "react";
import { findPassword } from "../../api/services/auth";
import Modal from "../../components/Modal/Modal";

const FindPasswordPage = styled.div`
  width: 100vw;
  height: 100vh;
  background: linear-gradient(
    90deg,
    rgba(143, 227, 207, 1) 0%,
    rgba(37, 109, 133, 1) 50%,
    rgba(43, 72, 101, 1) 100%
  );
  display: flex;
  justify-content: center;
  align-items: center;
`;
const Container = styled.div`
  background-color: white;
  width: 600px;
  height: 400px;
  border-radius: 25px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;

const Title = styled.div`
  font-size: 35px;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 50px;
`;
const EmailTitle = styled.div`
  font-size: 18px;
  width: 50%;
  margin-bottom: 20px;
  padding-left: 10px;
`;
const EmailInput = styled.input`
  font-size: 15px;
  width: 50%;
  height: 40px;
  margin-bottom: 30px;
  border-radius: 10px;
  border: 2px solid rgba(0, 0, 0, 0.2);
  text-align: center;
`;
const ButtonContainer = styled.div`
  width: 50%;

  display: flex;
  justify-content: center;
  align-items: center;
`;
const Btn = styled.button`
  width: 70px;
  height: 40px;
  border-style: none;
  border-radius: 15px;
  background-color: #256d85;
  color: white;
  margin-right: 20px;
  margin-left: 20px;
  font-size: 17px;
`;

const FindPassword = () => {
  const emailRef = useRef();
  const navigate = useNavigate();
  const [isModalOpen, setIsModalOpen] = useState(false);

  const findEmailHandler = () => {
    const email = emailRef.current.value;
    findPassword(email).then((res) => {
      console.log("비번 찾기 전송 성공");
      setIsModalOpen(true);
      setTimeout(() => {
        navigate("/login");
      }, 3000);
    });
  };
  return (
    <FindPasswordPage>
      <Container>
        <Title>비밀번호 찾기</Title>
        <EmailTitle>이메일</EmailTitle>
        <EmailInput ref={emailRef}></EmailInput>
        <ButtonContainer>
          <Btn onClick={findEmailHandler}>찾기</Btn>
          <Link to="/login">
            <Btn>취소</Btn>
          </Link>
        </ButtonContainer>
      </Container>
      {isModalOpen && <Modal num={9}></Modal>}
    </FindPasswordPage>
  );
};

export default FindPassword;
