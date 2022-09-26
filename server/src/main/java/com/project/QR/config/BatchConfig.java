package com.project.QR.config;

import com.project.QR.file.service.FileSystemStorageService;
import com.project.QR.qrcode.repository.QrCodeRepository;
import com.project.QR.qrcode.service.QrCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BatchConfig {
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final QrCodeRepository qrCodeRepository;
  private final QrCodeService qrCodeService;
  private final FileSystemStorageService fileSystemStorageService;

  @Bean
  public Job deleteJob() {
    Job job = jobBuilderFactory.get("deleteJob")

  }

}
