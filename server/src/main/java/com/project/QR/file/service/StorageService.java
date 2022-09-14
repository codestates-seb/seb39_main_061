package com.project.QR.file.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
  /**
   * 파일 저장 후 경로 리턴
   */
  String store(MultipartFile multipartFile, String path);

  /**
   * 파일 삭제
   */
  void remove(String path);
}
