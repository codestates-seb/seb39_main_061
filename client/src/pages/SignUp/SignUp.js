import React from "react";
import { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import Modal from "../../components/Modal/Modal";
import { signUpReq } from "../../api/services/auth";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { modalActions } from "../../store/modal";

import FormInput from "../../components/SignFormInput/SignFormInput";
import OauthIcons from "../../components/OauthIcons/OauthIcons";

import { inputs } from "../../constant";
import styled from "styled-components";

const SignUp = () => {
  const [userValue, setUserValue] = useState({
    email: "",
    password: "",
    confirmPassword: "",
    owenerName: "",
    phone: "",
    businessName: "",
  });
  const [signFormStatus, setSignFormStatus] = useState("signin");
  const [focused, setFocused] = useState(false);

  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);
  const isModalOpen = useSelector((state) => state.modal.isModalOpen);

  const handleOnChange = (e) => {
    setUserValue((prev) => ({ ...prev, [e.target.id]: e.target.value }));
  };

  // const SignUpHandler = async (event) => {
  //   event.preventDefault();
  //
  //   const email = emailRef.current.value;
  //   const password = PWRef.current.value;
  //   const confirmPassword = confirmPWRef.current.value;
  //   const name = OwenerNameRef.current.value;
  //   const businessName = businessNameRef.current.value;
  //
  //   setIsLoading(true);
  //   setChangeCSS(false);

  // const check = await validationInput();

  // if (check === true) {
  //   setValidationMSG("");
  //   try {
  //     signUpReq(email, password, name, businessName, phone);
  //     setIsLoading(false);
  //     dispatch(modalActions.setIsModalOpen(true));
  //     setTimeout(() => {
  //       navigate("/login");
  //       dispatch(modalActions.setIsModalOpen(false));
  //     }, 1500);
  //   } catch (err) {
  //     let errorMessage = err.error.message;
  //     alert(errorMessage);
  //   }
  // } else {
  //   setIsLoading(false);
  //   return;
  // }
  // };

  return (
    <Container>
      <Form /* onSubmit={SignUpHandler}  */>
        <Title>회원가입</Title>
        {inputs.map((input) => (
          <FormInput
            key={input.id}
            {...input}
            value={userValue[input.name]}
            onChange={handleOnChange}
          />
        ))}
        <OauthIcons />
        <Buttons>
          {!isLoading && <Button>회원가입</Button>}
          {isLoading && <Button>요청중</Button>}
          <Link to="/login">
            <Button>취소</Button>
          </Link>
        </Buttons>
        {isModalOpen && <Modal num={1} />}
      </Form>
    </Container>
  );
};
export default SignUp;

const Buttons = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  gap: 20px;
`;

const Button = styled.button`
  width: 120px;
  height: 50px;
  border-style: none;
  background-color: #256d85;
  border-radius: 25px;
  color: white;
  font-size: 18px;
  margin-bottom: 25px;
  cursor: pointer;
  margin-right: 5px;
  margin-left: 5px;
`;

const Container = styled.main`
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(
    90deg,
    rgba(143, 227, 207, 1) 0%,
    rgba(37, 109, 133, 1) 50%,
    rgba(43, 72, 101, 1) 100%
  );
`;
const Form = styled.form`
  display: flex;
  width: 500px;
  height: 700px;
  justify-content: space-around;
  algin-items: center;
  flex-direction: column;
  background-color: white;
  border-radius: 15px;
  padding: 30px 70px;
`;

const Title = styled.h1`
  font-size: 30px;
  text-align: center;
  color: #256d85;
`;
