package com.project.QR.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SingleResponseWithMessageDto<T> {
  private T data;
  private String message;
}
