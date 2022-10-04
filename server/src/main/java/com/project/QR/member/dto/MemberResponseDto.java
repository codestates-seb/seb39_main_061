package com.project.QR.member.dto;

<<<<<<< HEAD
import com.project.QR.sector.entity.Sector;
=======
import com.project.QR.business.dto.BusinessResponseDto;
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class MemberResponseDto {
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class MemberInfoDto {
    private String email;
    private List<String> service;
    private String profileImg;
<<<<<<< HEAD
    private Sector sector;
    private String businessName;
=======
>>>>>>> 4a643a9cd68a9baa8350400c74326bc2b6abe33d
    private String phone;
    private String name;
  }
}
