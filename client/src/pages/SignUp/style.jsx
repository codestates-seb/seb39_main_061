import styled from "styled-components";

export const Buttons = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  gap: 20px;
`;

export const Button = styled.button`
  width: 120px;
  height: 50px;
  border-style: none;
  background-color: #256d85;
  border-radius: 10px;
  color: white;
  font-size: 18px;
  margin-bottom: 25px;
  cursor: pointer;
  margin-right: 5px;
  margin-left: 5px;
`;

export const Container = styled.main`
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
export const Form = styled.form`
  display: flex;
  width: 450px;
  height: 700px;
  justify-content: space-around;
  algin-items: center;
  flex-direction: column;
  background-color: white;
  border-radius: 15px;
  padding: 30px 60px;

  @media (max-width: 768px) {
    height: 100vh;
  }
`;

export const Title = styled.h1`
  font-size: 30px;
  text-align: center;
  color: #256d85;
`;
