package com.project.QR.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MultiResponseWithMessageDto<T> {
  private List<T> data;
  private String message;
}
