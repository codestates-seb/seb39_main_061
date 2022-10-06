package com.project.QR.file.service;

import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class FileSystemStorageService implements StorageService {
  @Value("${save-path}")
  private String rootPath;
  @Value("${db-path}")
  private String dbPath;

  @Override
  public String store(MultipartFile multipartFile, String path) {
    StringBuilder uploadPath = new StringBuilder(rootPath + path);
    try {
      File file = new File(uploadPath.toString());
      if (!file.exists()) {
        boolean wasSuccessful = file.mkdirs();
        if (!wasSuccessful) {
          throw new BusinessLogicException(ExceptionCode.FOLDER_MAKE_FAIL);
        }
      }
      if(multipartFile.getSize() != 0) {
        StringBuffer fileName = new StringBuffer(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        fileName.replace(0, fileName.lastIndexOf("."), UUID.randomUUID().toString());
        Path savePath = Paths.get(new File(uploadPath.toString()) + "/" + fileName);
        multipartFile.transferTo(savePath);
        return dbPath + path + "/" + fileName;
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new BusinessLogicException(ExceptionCode.FILE_UPLOAD_FAIL);
    }
    return null;
  }

  @Override
  public void remove(String path) {
    File file = new File(path);
    if(!file.delete()) {
      log.error("FILE IS NOT EXIST");
    }
  }
}
