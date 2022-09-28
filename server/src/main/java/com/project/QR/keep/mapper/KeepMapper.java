package com.project.QR.keep.mapper;

import com.project.QR.business.entity.Business;
import com.project.QR.keep.dto.KeepRequestDto;
import com.project.QR.keep.entity.Keep;
import com.project.QR.member.entity.Member;
import com.project.QR.qrcode.entity.QrCode;
import com.project.QR.keep.dto.KeepResponseDto;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface KeepMapper {
  default Keep createKeepDtoToKeep(KeepRequestDto.CreateKeepDto createKeepDto) {
    Business business = new Business();
    business.setBusinessId(createKeepDto.getBusinessId());
    Member member = new Member();
    business.setMember(member);
    QrCode qrCode = new QrCode();
    qrCode.setBusiness(business);
    qrCode.setQrCodeId(createKeepDto.getQrCodeId());
    Keep keep = new Keep();
    keep.setInfo(createKeepDto.getInfo());
    keep.setTarget(createKeepDto.getTarget());
    keep.setCount(createKeepDto.getCount());
    keep.setQrCode(qrCode);
    return keep;
  }

  default Keep updateKeepDtoToKeep(KeepRequestDto.UpdateKeepDto updateKeepDto) {
    Business business = new Business();
    business.setBusinessId(updateKeepDto.getBusinessId());
    Member member = new Member();
    business.setMember(member);
    QrCode qrCode = new QrCode();
    qrCode.setBusiness(business);
    qrCode.setQrCodeId(updateKeepDto.getQrCodeId());
    Keep keep = new Keep();
    keep.setKeepId(updateKeepDto.getKeepId());
    keep.setTarget(updateKeepDto.getTarget());
    keep.setInfo(updateKeepDto.getInfo());
    keep.setCount(updateKeepDto.getCount());
    keep.setQrCode(qrCode);
    return keep;
  }

  default KeepResponseDto.KeepInfoDto keepToKeepInfoDto(Keep keep) {
    return KeepResponseDto.KeepInfoDto.builder()
            .keepId(keep.getKeepId())
            .target(keep.getTarget())
            .info(keep.getInfo())
            .count(keep.getCount())
            .createdAt(keep.getCreatedAt())
            .modifiedAt(keep.getModifiedAt())
            .build();
  }

  default List<KeepResponseDto.KeepInfoDto> keepListToKeepInfoDtoList(List<Keep> keepList) {
    return keepList.stream()
            .map(this::keepToKeepInfoDto)
            .collect(Collectors.toList());
  }
}