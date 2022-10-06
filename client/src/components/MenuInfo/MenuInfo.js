import React from "react";
import { useState } from "react";
import { useEffect } from "react";
import styled from "styled-components";
import { getMenuList } from "../../api/services/menu";
import Modal from "react-modal";
import RegisterMenu from "../RegisterMenu/RegisterMenu";
import { useDispatch, useSelector } from "react-redux";
import { getBusinessInfo } from "../../api/services/store";
import { businessActions } from "../../store/business";
import { baseURL } from "../../api/axios";
import { menuActions } from "../../store/menu";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faArrowRight,
  faArrowLeft,
  faTrashCan,
  faPenToSquare,
} from "@fortawesome/free-solid-svg-icons";
import ConfirmModal from "../Modal/Modal";

const MenuContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
  padding: 0px 70px 0px 70px;
  width: 95%;
  height: 600px;
  position: relative;
`;
const MenuTitle = styled.h1`
  display: flex;
  justify-content: flex-start;
  font-size: 20px;
  width: 100%;
  color: #2b4865;
`;

const MenuItemContainer = styled.div`
  height: 100%;
  width: 98%;
  display: grid;
  place-items: center;
  grid-template-rows: 1fr 1fr;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr;
  animation: ${(props) => props.animation};
  @keyframes slide-left {
    from {
      margin-left: 100%;
    }

    to {
      margin-left: 0%;
    }
  }

  @keyframes slide-right {
    from {
      margin-right: 100%;
    }
    to {
      transform: translateX(0);
    }
  }
`;
const MenuItem = styled.div`
  display: flex;
  flex-direction: column;

  width: 190px;
  height: 220px;
  border-radius: 15px;
`;

const MenuImg = styled.img`
  background-image: linear-gradient(rgba(0, 0, 0, 0), rgba(0, 0, 0, 0));
  width: 100%;
  height: 70%;
  border-radius: 15px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.8);
  margin-bottom: 10px;

  ${MenuItem}:hover & {
    opacity: 0.3;
  }
`;
const MenuName = styled.span`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 15%;
  font-size: 17px;
  margin-bottom: 10px;
`;
const MenuPrice = styled.span`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 15%;
  font-size: 17px;
`;

const ImgBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 70%;
  border-radius: 15px;
  background-color: #f5f5f5;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.3);
`;
const PlusButton = styled.button`
  width: 30px;
  height: 30px;
  font-size: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-style: none;
  background-color: #f5f5f5;
`;

const ImgButton = styled.button`
  display: none;
  justify-content: center;
  align-items: center;
  width: 20px;
  height: 20px;
  outline: none;
  border-style: none;
  background-color: transparent;
  font-size: ${(props) => props.fontSize || "25px"};
  user-select: none;
  color: ${(props) => props.color || "black"};
  position: absolute;
  margin-left: ${(props) => props.marginLeft};
  margin-top: 65px;

  &:hover {
    font-size: 35px;
  }

  &:active {
    transform: scale(0.8);
  }
  ${MenuItem}:hover & {
    display: flex;
  }
`;

const MovePageBtn = styled.button`
  position: absolute;
  right: ${(props) => props.right || ""};
  left: ${(props) => props.left || ""};
  margin-right: ${(props) => props.marginRight || ""};
  margin-left: ${(props) => props.marginLeft || ""};
  font-size: 40px;
  margin-bottom: 50px;
  border-style: none;
  background-color: white; ;
`;

const MenuInfo = () => {
  const [empthyEle, setEmpthyEle] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [pageNum, setPageNum] = useState(1);
  const [totalPage, setTotalPage] = useState(1);
  const dispatch = useDispatch();
  const menuList = useSelector((state) => state.menu.menuList);
  const [isConfirmModalOpen, setIsConfirmModalOpen] = useState(false);
  const [isEdit, setIsEdit] = useState(false);
  const [menuId, setMenuId] = useState(0);
  const [modalNum, setModalNum] = useState(6);
  const [changeCSS, setChangeCSS] = useState(true);
  const [rightOrLeft, setRightOrLeft] = useState(false);

  useEffect(() => {
    setChangeCSS(true);
  }, [changeCSS]);

  useEffect(() => {
    getBusinessInfo().then((res) => {
      dispatch(businessActions.setBusinessId(res.data.data.businessId));
      getMenuList(res.data.data.businessId, pageNum).then((res) => {
        setTotalPage(res.data.pageInfo.totalPages);
        dispatch(menuActions.setMenuList(res.data.data));
        setEmpthyEle(res.data.data.length !== 10);
      });
    });
  }, [pageNum, menuId]);

  const menuModalToggle = () => {
    setIsModalOpen(!isModalOpen);
  };
  const confirmModalToggle = () => {
    setIsConfirmModalOpen(!isConfirmModalOpen);
  };
  const modalStyles = {
    overlay: {
      backgroundColor: "rgba(0,0,0,0.2)",
      marginLeft: "270px",
    },
    content: {
      marginLeft: "690px",
      left: "0",
      margin: "auto",
      width: "550px",
      height: "650px",
      padding: "0",
      borderRadius: "20px",
    },
  };
  const editModalHandler = (menuId) => {
    console.log("메뉴아이디는?", menuId);
    setMenuId(menuId);
    setIsEdit(true);
    setModalNum(6);
    setIsModalOpen(!isModalOpen);
  };

  return (
    <MenuContainer>
      <MenuTitle>메뉴 정보</MenuTitle>

      <MenuItemContainer
        animation={
          changeCSS && !rightOrLeft
            ? "slide-left 1s ease-in-out"
            : "slide-right 1s ease-in-out"
        }
      >
        {menuList.map((item, idx) => (
          <MenuItem key={item.menuId}>
            <MenuImg
              opacity={
                "linear-gradient(rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0.2))"
              }
              src={`${baseURL}${item.img}`}
            />
            <ImgButton
              color={"#2b4865"}
              fontSize={"30px"}
              display={"none"}
              marginLeft={"55px"}
              onClick={() => {
                editModalHandler(item.menuId);
              }}
            >
              <FontAwesomeIcon icon={faPenToSquare} />
            </ImgButton>
            <ImgButton
              color={"red"}
              fontSize={"30px"}
              marginLeft={"110px"}
              display={"none"}
              onClick={() => {
                setMenuId(item.menuId);
                setModalNum(7);
                confirmModalToggle(true);
              }}
            >
              <FontAwesomeIcon icon={faTrashCan} />
            </ImgButton>
            <MenuName>{item.name}</MenuName>
            <MenuPrice>{item.price}원</MenuPrice>
          </MenuItem>
        ))}
        {empthyEle && (
          <MenuItem>
            <ImgBox>
              <PlusButton onClick={menuModalToggle}>+</PlusButton>
            </ImgBox>
          </MenuItem>
        )}
        {pageNum > totalPage ||
          (menuList.length === 10 && (
            <MovePageBtn
              onClick={() => {
                setPageNum(pageNum + 1);
                setRightOrLeft(false);
                setChangeCSS(false);
              }}
              marginRight={"80px"}
              right={"0px"}
            >
              <FontAwesomeIcon icon={faArrowRight} />
            </MovePageBtn>
          ))}
        {pageNum !== 1 && (
          <MovePageBtn
            onClick={() => {
              setPageNum(pageNum + -1);
              setRightOrLeft(true);
              setChangeCSS(false);
            }}
            marginLeft={"80px"}
            left={"0px"}
          >
            <FontAwesomeIcon icon={faArrowLeft} />
          </MovePageBtn>
        )}
      </MenuItemContainer>
      <Modal
        isOpen={isModalOpen}
        ariaHideApp={false}
        onRequestClose={() => {
          menuModalToggle();
          setIsEdit(false);
        }}
        style={modalStyles}
      >
        <RegisterMenu
          confirmModalToggle={confirmModalToggle}
          menuModalToggle={menuModalToggle}
          setPageNum={setPageNum}
          setModalNum={setModalNum}
          isEdit={isEdit === true ? true : false}
          menuId={menuId}
        />
      </Modal>
      {isConfirmModalOpen && (
        <ConfirmModal
          setIsConfirmModalOpen={setIsConfirmModalOpen}
          num={modalNum}
          menuId={menuId}
        />
      )}
    </MenuContainer>
  );
};

export default MenuInfo;
