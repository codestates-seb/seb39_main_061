import React from "react";
import { BsCheckCircleFill } from "react-icons/bs";
import styled from "styled-components";

const FormInput = (props) => {
  const {
    name,
    id,
    errorMessage,
    value,
    onChange,
    focused,
    setFocused,
    ...inputProps
  } = props;
  return (
    <Container>
      <Label htmlFor={id}>{name}</Label>
      <Input id={id} onChange={onChange} value={value} {...inputProps} />
      <BsCheckCircleFill />
    </Container>
  );
};

export default FormInput;

const Container = styled.div`
  display: flex;
  width: 100%;
  height: 40px;
  justify-content: space-between;
  margin: 10px 0px;
  align-items: center;

  & > svg {
    color: green;
    visibility: visible;
  }
`;

const Label = styled.label`
  text-align: start;
  width: 90px;
  font-size: 16px;
  display: flex;
  justify-content: start;
  align-items: center;
`;

const Input = styled.input`
  border: none;
  border-bottom: 1px solid rgba(0, 0, 0, 0.2);
  padding: 15px;
  margin-right: 5px;
  font-size: 14px;
`;
