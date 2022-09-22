package com.project.QR.stub;

import com.project.QR.reservation.dto.ReservationResponseDto;
import com.project.QR.reservation.entity.Check;

import java.util.ArrayList;
import java.util.List;

public class StatisticsStubData {

  public static ReservationResponseDto.StatisticsDto statisticsDto(Check check, String date, int count) {
    return ReservationResponseDto.StatisticsDto.builder()
      .deleted(check)
      .date(date)
      .count(count)
      .build();
  }

  public static List<ReservationResponseDto.StatisticsDto> week() {
    List<ReservationResponseDto.StatisticsDto> statisticsDtoList = new ArrayList<>();
    statisticsDtoList.add(statisticsDto(Check.N, "2022-09-15", 3));
    statisticsDtoList.add(statisticsDto(Check.Y, "2022-09-15", 1));
    statisticsDtoList.add(statisticsDto(Check.N, "2022-09-20", 1));
    statisticsDtoList.add(statisticsDto(Check.Y, "2022-09-18", 4));
    return statisticsDtoList;
  }

  public static List<ReservationResponseDto.StatisticsDto> month() {
    List<ReservationResponseDto.StatisticsDto> statisticsDtoList = new ArrayList<>();
    statisticsDtoList.add(statisticsDto(Check.N, "2022-09", 3));
    statisticsDtoList.add(statisticsDto(Check.Y, "2022-09", 1));
    statisticsDtoList.add(statisticsDto(Check.N, "2022-07-20", 1));
    statisticsDtoList.add(statisticsDto(Check.Y, "2022-05-18", 4));
    return statisticsDtoList;
  }

  public static List<ReservationResponseDto.StatisticsDto> time() {
    List<ReservationResponseDto.StatisticsDto> statisticsDtoList = new ArrayList<>();
    statisticsDtoList.add(statisticsDto(Check.N, "13", 3));
    statisticsDtoList.add(statisticsDto(Check.Y, "13", 1));
    statisticsDtoList.add(statisticsDto(Check.N, "15", 1));
    statisticsDtoList.add(statisticsDto(Check.Y, "20", 4));
    return statisticsDtoList;
  }


  public static ReservationResponseDto.StatisticsInfoDto statisticsInfoDto() {
    return ReservationResponseDto.StatisticsInfoDto.builder()
      .week(week())
      .month(month())
      .time(time())
      .build();
  }
}
