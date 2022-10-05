package com.project.QR.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Primary
@Service
@RequiredArgsConstructor
public class AWSS3FileService implements StorageService{
  private final AmazonS3Client amazonS3Client;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  @Override
  public String store(MultipartFile multipartFile, String path) {
    StringBuffer fileName = new StringBuffer(Objects.requireNonNull(multipartFile.getOriginalFilename()));
    String fileExtension = fileName.substring(fileName.lastIndexOf("."));
    fileName.replace(0, fileName.lastIndexOf("."), UUID.randomUUID().toString());
    fileName = new StringBuffer().append(path).append("/").append(fileName);
    System.out.println(fileExtension);
    if(fileExtension.equalsIgnoreCase(".jpg") || fileExtension.equalsIgnoreCase(".png")) {
      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentType(multipartFile.getContentType());
      try(InputStream inputStream = multipartFile.getInputStream()) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName.toString(), inputStream, objectMetadata)
          .withCannedAcl(CannedAccessControlList.PublicRead));
        return fileName.toString();
      } catch (IOException e) {
        throw new BusinessLogicException(ExceptionCode.FILE_UPLOAD_FAIL);
      }
    }
    throw new BusinessLogicException(ExceptionCode.FILE_EXTENSION_INVALID);
  }

  @Override
  public void remove(String path) {
    amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, path));
  }
}
