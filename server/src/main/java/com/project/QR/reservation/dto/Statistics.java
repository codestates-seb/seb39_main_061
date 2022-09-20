package com.project.QR.reservation.dto;

import com.project.QR.reservation.entity.Check;

import java.time.LocalDateTime;

public interface Statistics {
  Check getDeleted();
  String getDate();
  int getCount();
}
