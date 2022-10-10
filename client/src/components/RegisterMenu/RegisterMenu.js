import React, { useEffect, useState, useRef } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrashCan } from "@fortawesome/free-solid-svg-icons";
import styled from "styled-components";
import { editMenu, getMenu, postMenu } from "../../api/services/menu";
import { useDispatch, useSelector } from "react-redux";
import { imgURL } from "../../api/axios";
import { getBusinessInfo } from "../../api/services/store";
import { businessActions } from "../../store/business";

const RegisterMenuContainer = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  position: relative;
`;

const ImgBox = styled.div`
  background-image: url(${(props) => props.imageFile || ""});
  background-size: 340px 250px;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 340px;
  height: 250px;
  border-radius: 15px;
  background-color: #f5f5f5;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.3);
  margin-bottom: 30px;
  position: relative;
  overflow: hidden;

  &:hover {
    background-image: ${(props) => props.opacity},
      url(${(props) => props.imageFile});
  }
`;
const ImgButton = styled.button`
  position: absolute;
  display: ${(props) => props.display || "flex"};
  justify-content: center;
  align-items: center;
  width: 30px;
  height: 30px;
  outline: none;
  border-style: none;
  background-color: transparent;
  font-size: ${(props) => props.fontSize || "70px"};
  user-select: none;
  color: ${(props) => props.color || "black"};

  &:hover {
    font-size: 55px;
  }

  &:active {
    transform: scale(0.8);
  }
  ${ImgBox}:hover & {
    display: flex;
  }
`;
const Name = styled.span`
  display: flex;
  width: 200px;
  justify-content: flex-start;
  font-size: 18px;
  opacity: 0.8;
  margin-bottom: 18px;
`;
const Input = styled.input`
  display: flex;
  justify-content: flex-start;
  align-items: center;
  background-color: #f5f5f5;
  padding-left: 25px;
  font-size: 16px;
  margin-bottom: 20px;
  height: 45px;
  width: 220px;
  border-style: none;
  border: 2px solid rgba(0, 0, 0, 0.1);
  border-radius: 15px;
  outline: none;

  box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.2);

  &::-webkit-inner-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }
`;
const ButtonContainer = styled.div`
  margin-top: 20px;
  &:hover {
    opacity: 1;
  }
`;
const Btn = styled.button`
  background-color: #256d85;
  width: 90px;
  height: 37px;
  border-style: none;
  margin-right: 10px;
  margin-left: 10px;
  border-radius: 15px;
  box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.2);
  color: white;
  font-size: 15px;
`;

const ValidationMSG = styled.div`
  display: flex;
  justify-content: center;
  width: 80%;
  height: 15px;
  color: #2b4865;
  animation: ${(props) => props.animation};
  @keyframes gelatine {
    from,
    to {
      transform: scale(1, 1);
    }
    25% {
      transform: scale(0.9, 1.1);
    }
    50% {
      transform: scale(1.1, 0.9);
    }
    75% {
      transform: scale(0.95, 1.05);
    }
  }
`;

const RegisterMenu = ({
  isEdit,
  confirmModalToggle,
  menuModalToggle,
  setModalNum,
}) => {
  const selectFile = useRef("");
  const [imageFile, setImageFile] = useState(null);
  const [img, setImg] = useState("");
  const [name, setName] = useState("");
  const [price, setPrice] = useState("");
  const businessId = useSelector((state) => state.business.businessId);
  const [changePhoto, setChangePhoto] = useState(false);
  const [message, setMessage] = useState("");
  const menuList = useSelector((state) => state.menu.menuList);
  const [changeCSS, setChangeCSS] = useState(false);
  const menuId = useSelector((state) => state.menu.menuId);

  useEffect(() => {
    setChangeCSS(true);
  }, [changeCSS]);

  useEffect(() => {
    if (menuList.length !== 0 && isEdit === true) {
      getMenu(businessId, menuId).then((res) => {
        console.log(res);
        setImageFile(`${imgURL}${res.data.data.img}`);
        setImg(res.data.data.img);
        setName(res.data.data.name);
        setPrice(res.data.data.price);
      });
    }
  }, []);

  //
  const saveImageFile = (event) => {
    setImageFile(URL.createObjectURL(event.target.files[0]));
    setImg(event.target.files[0]);
  };
  const deleteImageFile = () => {
    URL.revokeObjectURL(imageFile);
    setImageFile("");
    setChangePhoto(true);
  };
  const menuSubmitHandler = () => {
    setChangeCSS(false);
    //유효성 검사
    if (name.length === 0) {
      setMessage("메뉴이름을 입력해주세요");
      return;
    }
    if (price.length === 0) {
      setMessage("가격을 입력해주세요");
      return;
    }

    const formData = new FormData();
    const value = {
      name: name,
      price: price,
    };

    formData.append(
      "data",
      new Blob([JSON.stringify(value)], { type: "application/json" })
    );

    if (isEdit === true) {
      if (changePhoto === true) {
        formData.append("file", img);
      } else {
        const newFile = new File([""], "");
        formData.append("file", newFile);
      }

      setModalNum(8);
      editMenu(businessId, menuId, formData).then((res) => {
        console.log("메뉴편집 성공", res);
        menuModalToggle();
        confirmModalToggle();
        setTimeout(() => {
          window.location.reload();
        }, 1500);
      });
    } else {
      if (!img) {
        setMessage("메뉴사진을 첨부해주세요");
        return;
      }
      formData.append("file", img);
      postMenu(businessId, formData).then((res) => {
        console.log("메뉴등록 성공", res);
        menuModalToggle();
        confirmModalToggle();
        setTimeout(() => {
          window.location.reload();
        }, 1500);
      });
    }
  };
  const menuNameChange = (e) => {
    setName(e.target.value);
  };
  const menuPriceChange = (e) => {
    setPrice(e.target.value);
  };

  return (
    <RegisterMenuContainer>
      {!imageFile ? (
        <ImgBox>
          <input
            type="file"
            style={{ display: "none" }}
            ref={selectFile}
            onChange={saveImageFile}
          ></input>
          <ImgButton onClick={() => selectFile.current.click()}>+</ImgButton>
        </ImgBox>
      ) : (
        <>
          <ImgBox
            opacity={"linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4))"}
            imageFile={imageFile}
          >
            <ImgButton
              color={"red"}
              fontSize={"40px"}
              display={"none"}
              onClick={() => deleteImageFile()}
            >
              <FontAwesomeIcon icon={faTrashCan} />
            </ImgButton>
          </ImgBox>
        </>
      )}
      <Name>메뉴이름</Name>
      <Input
        value={name}
        onChange={menuNameChange}
        placeholder="예: 토마토 파스타"
        maxLength={10}
      ></Input>
      <Name>가격</Name>
      <Input
        value={price}
        onChange={menuPriceChange}
        placeholder="예: 5000"
        type={"number"}
      ></Input>
      <ValidationMSG
        animation={changeCSS === true ? "gelatine 0.5s ease-out" : ""}
      >
        {message}
      </ValidationMSG>
      <ButtonContainer>
        <Btn onClick={menuSubmitHandler}>
          {isEdit === true ? "저장" : "등록"}
        </Btn>
        <Btn
          onClick={() => {
            menuModalToggle();
          }}
        >
          취소
        </Btn>
      </ButtonContainer>
    </RegisterMenuContainer>
  );
};

export default RegisterMenu;
