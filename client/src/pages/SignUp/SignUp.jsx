import React from "react";
import { useState, useEffect } from "react";
import { useNavigate, Link } from "react-router-dom";
import styled from "styled-components";
import { useSelector, useDispatch } from "react-redux";

import { signUpReq } from "../../api/services/auth";
import { modalActions } from "../../store/modal";
import FormInput from "../../components/FormInput/FormInput";
import OauthIcons from "../../components/OauthIcons/OauthIcons";
import { inputs } from "../../constants";
import Modal from "../../components/Modal/Modal";
import { Container, Form, Title, Buttons, Button } from "./style.jsx";

const SignUp = () => {
  const [userValue, setUserValue] = useState({
    email: "",
    password: "",
    confirmPassword: "",
    ownerName: "",
    phone: "",
    businessName: "",
  });

  const [signFormStatus, setSignFormStatus] = useState("signin");
  const [focused, setFocused] = useState(false);
  const navigate = useNavigate();
  const isModalOpen = useSelector((state) => state.modal.isModalOpen);
  const dispatch = useDispatch();

  const handleOnChange = (e) => {
    setUserValue((prev) => ({ ...prev, [e.target.id]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    const { email, password, businessName, ownerName, phone } = userValue;
    e.preventDefault();
    try {
      await signUpReq({
        email,
        password,
        name: ownerName,
        businessName,
        phone,
      });

      dispatch(modalActions.setIsModalOpen(true));
      setTimeout(() => {
        navigate("/login");
        dispatch(modalActions.setIsModalOpen(false));
      }, 1000);
    } catch (err) {
      alert("회원가입에 실패하였습니다. 잠시후 다시 시도해주세요");
    }
  };

  return (
    <Container>
      <Form onSubmit={handleSubmit}>
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
          <Button>회원가입</Button>
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
