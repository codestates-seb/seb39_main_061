package com.project.QR.member.entity;

public enum Sector {
  SECTOR_1("농업, 임업 및 어업"),
  SECTOR_2("전기, 가스 및 수도사업"),
  SECTOR_3("하수·폐기물 처리, 원료재생 및 환경복원업"),
  SECTOR_4("건설업"),
  SECTOR_5("숙박 및 음식점업"),
  SECTOR_6("출판, 영상, 방송통신 및 정보서비스업"),
  SECTOR_7("금융 및 보험업"),
  SECTOR_8("부동산 및 임대업"),
  SECTOR_9("전문, 과학 및 기술 서비스업"),
  SECTOR_10("공공행정, 국방 및 사회보장 행정"),
  SECTOR_11("교육 서비스업"),
  SECTOR_12("보건 및 사회복지사업"),
  SECTOR_13("예술, 스포츠 및 여가관련 서비스업"),
  SECTOR_14("협회 및 단체, 수리 및 기타 개인서비스업"),
  SECTOR_15("기타");

  private final String type;

  Sector(String type) {
    this.type = type;
  }
}
