package com.project.QR.config;

import com.project.QR.file.service.FileSystemStorageService;
import com.project.QR.qrcode.entity.QrCode;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfig {
  @Autowired
  private JobBuilderFactory jobBuilderFactory;
  @Autowired
  private StepBuilderFactory stepBuilderFactory;
  @Autowired
  private EntityManagerFactory entityManagerFactory;
  @Autowired
  private FileSystemStorageService fileSystemStorageService;
  @Value("${save-path}")
  private String rootPath;

  @Bean
  public Job deleteJob() {
    return jobBuilderFactory.get("deleteJob")
      .incrementer(new RunIdIncrementer())
      .start(deleteQrCodeStep())
      .build();
  }

  @Bean
  @JobScope  // thread safe 함
  public Step deleteQrCodeStep() {
    return stepBuilderFactory.get("deleteQrCodeStep")
      .<QrCode, QrCode> chunk(10)
      .reader(qrReaderExpired(null))
      .processor(deleteQrCode(null))
      .writer(qrWriterExpired(null))
      .build();
  }

  @StepScope
  private ItemWriter<QrCode> qrWriterExpired(@Value("#{jopParameters[requestDate]}") String requestDate) {
    log.info("==> writer value : {}", requestDate);
    return new JpaItemWriterBuilder<QrCode>()
      .entityManagerFactory(entityManagerFactory)
      .build();
  }

  @StepScope
  private ItemProcessor<QrCode, QrCode> deleteQrCode(@Value("#{jopParameters[requestDate]}") String requestDate) {
    log.info("==> processor value : {}", requestDate);
    return qrCode -> {

      if(StringUtils.hasText(qrCode.getQrCodeImg()))
        fileSystemStorageService.remove(rootPath.substring(0,5) + qrCode.getQrCodeImg());
      qrCode.setQrCodeImg(null);
      return qrCode;
    };
  }

  @StepScope
  private JpaPagingItemReader<QrCode> qrReaderExpired(@Value("#{jopParameters[requestDate]}") String requestDate) {
    log.info("==> reader value : {}", requestDate);

    return new JpaPagingItemReaderBuilder <QrCode>()
      .pageSize(10)
      .entityManagerFactory(entityManagerFactory)
      .queryString("SELECT q FROM QrCode q WHERE q.dueDate <= CURRENT_DATE")
      .name("qrReaderExpired")
      .build();
  }
}
