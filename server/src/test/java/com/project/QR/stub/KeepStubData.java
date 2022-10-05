package com.project.QR.stub;

import com.project.QR.business.entity.Business;
import com.project.QR.helper.page.RestPage;
import com.project.QR.keep.dto.KeepRequestDto;
import com.project.QR.keep.dto.KeepResponseDto;
import com.project.QR.keep.entity.Keep;
import com.project.QR.member.entity.Member;
import com.project.QR.qrcode.entity.QrCode;
import com.project.QR.qrcode.entity.QrType;
import net.nurigo.sdk.message.model.Count;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.lang.annotation.Target;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KeepStubData {
  private static final Member member = MemberStubData.member();
  private static final QrCode qrCode = QrCodeStubData.qrCode();
  private static final Business business = BusinessStubData.business();

  /*
    public static Keep keep() {
      Keep keep = new Keep();
      keep.setKeepId(1L);
      keep.setQrCode(qrCode);
      keep.setTarget("target");
      keep.setInfo("info");
      keep.setCount(10);
      return keep;
    }
    public static Keep keep(long keepId) {
      Keep keep = new Keep();
      keep.setKeepId(keepId);
      keep.setQrCode(qrCode);
      keep.setTarget("target");
      keep.setInfo("info");
      keep.setCount(10);
      return keep;
    }
  */
public static KeepResponseDto.KeepInfoDto keepInfoDto(String target, String info, int count) {
  return KeepResponseDto.KeepInfoDto.builder()
          .keepId(1L)
          .target(target)
          .info(info)
          .count(count)
          .createdAt(LocalDateTime.now())
          .modifiedAt(LocalDateTime.now())
          .build();
}
  public static KeepResponseDto.KeepInfoDto keepInfoDto(Keep keep) {
    return KeepResponseDto.KeepInfoDto.builder()
            .keepId(1L)
            .target(keep.getTarget())
            .info(keep.getInfo())
            .count(keep.getCount())
            .createdAt(LocalDateTime.now())
            .modifiedAt(LocalDateTime.now())
            .build();
  }

  public static List<KeepResponseDto.KeepInfoDto> keepInfoDtoList() {
    List<KeepResponseDto.KeepInfoDto> keepInfoDtoList = new ArrayList<>();
    keepInfoDtoList.add(keepInfoDto("사과", "원산지:충주", 10));
    keepInfoDtoList.add(keepInfoDto("귤", "원산지: 제주", 11));
    keepInfoDtoList.add(keepInfoDto("샤인머스켓", "원산지:영동", 12));
    return keepInfoDtoList;
  }

  public static Keep keep(long keepId, String target, String info, int count) {
    Keep keep = new Keep();
    keep.setKeepId(keepId);
    keep.setTarget(target);
    keep.setInfo(info);
    keep.setCount(count);
    keep.setQrCode(qrCode);
    return keep;
  }

  public static List<Keep> keepList() {
    List<Keep> keepList = new ArrayList<>();
    keepList.add(keep(1L, "사과", "원산지: 충주", 10));
    keepList.add(keep(2L, "귤", "원산지: 제주", 11));
    keepList.add(keep(3L, "샤인머스켓", "원산지: 영동", 12));
    return keepList;
  }

  public static KeepRequestDto.CreateKeepDto createKeepDto() {
    return KeepRequestDto.CreateKeepDto.builder()
            .businessId(1L)
            .memberId(1L)
            .qrCodeId(1L)
            .target("사과")
            .info("충주산")
            .count(10)
            .build();
  }

  public static RestPage<Keep> getKeepPage(int page, int size) {
    return new RestPage<>(new PageImpl<>(List.of(
            keep(1L, "사과","원산지: 충주", 10),
            keep(2L, "귤","원산지: 제주", 11),
            keep(3L, "샤인머스켓","원산지: 영동", 12)
    ), PageRequest.of(page, size, Sort.by("KEEP_ID").descending()), 3));
  }

  public static List<KeepResponseDto.KeepInfoDto> keepInfoDtoList(List<Keep> keepList) {
    return keepList.stream()
            .map(KeepStubData::keepInfoDto)
            .collect(Collectors.toList());
  }

  public static KeepRequestDto.UpdateKeepDto updateKeepDto() {
    return KeepRequestDto.UpdateKeepDto.builder()
            .businessId(1L)
            .memberId(1L)
            .qrCodeId(1L)
            .keepId(1L)
            .target("사과")
            .info("원산지: 충주")
            .count(20)
            .build();
  }
}
