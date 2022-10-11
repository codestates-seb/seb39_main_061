import styled from "styled-components";
import { getUserFoodList } from "../../api/services/reservation-user";
import { useState, useEffect } from "react";
import React from "react";
import { imgURL } from "../../api/axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faArrowRight,
  faArrowLeft,
  faTrashCan,
  faPenToSquare,
  faXmark,
} from "@fortawesome/free-solid-svg-icons";

const MenuContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-direction: column;
  padding: 15px;
  width: 100%;
  height: 600px;
  position: relative;
`;
const MenuTitle = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: center;

  width: 100%;
  color: #2b4865;
  height: 10%;
`;
const SubTitle = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: center;
  font-size: 20px;

  color: #2b4865;
  height: 5%;
`;
const Title = styled.div`
  display: flex;
  justify-content: flex-end;
  align-items: center;
  font-size: 25px;
  margin-right: 115px;
  color: #2b4865;
  height: 5%;
`;

const MenuItemContainer = styled.div`
  height: 95%;
  width: 98%;
  display: grid;
  place-items: center;
  grid-template-rows: 1fr 1fr 1fr 1fr;
  grid-template-columns: 1fr 1fr;
  animation: ${(props) => props.animation};
  @keyframes slide-left {
    from {
      margin-left: 220%;
    }

    to {
      margin-left: 0%;
    }
  }

  @keyframes slide-right {
    from {
      margin-right: 220%;
    }
    to {
      transform: translateX(0);
    }
  }
`;
const MenuItem = styled.div`
  display: flex;
  flex-direction: column;

  width: 120px;
  height: 120px;
  border-radius: 15px;
`;

const MenuImg = styled.img`
  background-image: linear-gradient(rgba(0, 0, 0, 0), rgba(0, 0, 0, 0));
  width: 100%;
  height: 70%;
  border-radius: 15px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.8);
  margin-bottom: 5px;

  /* ${MenuItem}:hover & {
    opacity: 0.3;
  } */
`;
const MenuName = styled.span`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 15%;
  font-size: 10px;
  margin-bottom: 2px;
`;
const MenuPrice = styled.span`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 15%;
  font-size: 10px;
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
  font-size: 23px;
  margin-bottom: 50px;
  border-style: none;
  background-color: transparent;
`;

const UserMenu = ({ toggle, businessId }) => {
  const [pageNum, setPageNum] = useState(1);
  const [totalPage, setTotalPage] = useState(1);
  const [changeCSS, setChangeCSS] = useState(true);
  const [rightOrLeft, setRightOrLeft] = useState(false);
  const [menuList, setMenuList] = useState([]);

  useEffect(() => {
    setChangeCSS(true);
  }, [changeCSS]);

  useEffect(() => {
    getUserFoodList(businessId, pageNum).then((res) => {
      console.log(res);
      setMenuList(res.data.data);
      setTotalPage(res.data.pageInfo.totalPages);
    });
  }, [pageNum]);
  return (
    <MenuContainer>
      <MenuTitle>
        <Title>메뉴</Title>
        <SubTitle onClick={toggle}>
          <FontAwesomeIcon icon={faXmark} />
        </SubTitle>
      </MenuTitle>

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
              src={`${imgURL}${item.img}`}
            />

            <MenuName>{item.name}</MenuName>
            <MenuPrice>{item.price}원</MenuPrice>
          </MenuItem>
        ))}
        {pageNum > totalPage ||
          (menuList.length === 8 && (
            <MovePageBtn
              onClick={() => {
                setPageNum(pageNum + 1);
                setRightOrLeft(false);
                setChangeCSS(false);
              }}
              marginRight={"15px"}
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
            marginLeft={"15px"}
            left={"0px"}
          >
            <FontAwesomeIcon icon={faArrowLeft} />
          </MovePageBtn>
        )}
      </MenuItemContainer>
    </MenuContainer>
  );
};

export default UserMenu;
