package com.project.QR.config;

import com.project.QR.file.service.FileSystemStorageService;
import com.project.QR.helper.event.MemberRegistrationApplicationEvent;
import com.project.QR.helper.event.QrCodeExpiredApplicationEvent;
import com.project.QR.helper.event.QrCodeRemoveApplicationEvent;
import com.project.QR.qrcode.entity.QrCode;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
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
  @Autowired
  private ApplicationEventPublisher publisher;


  @Bean
  public Job expiredJob() {
    return jobBuilderFactory.get("expiredJob")
      .incrementer(new RunIdIncrementer())
      .start(expiredQrCodeStep())
      .build();
  }

  @Bean
  @JobScope
  public Step expiredQrCodeStep() {
    return stepBuilderFactory.get("expiredQrCodeStep")
      .<QrCode, QrCode> chunk(10)
      .reader(expiredQrReader(null))
      .processor(expiredQr(null))
      .writer(expiredQrWriter(null))
      .build();
  }

  @StepScope
  private ItemWriter<QrCode> expiredQrWriter(@Value("#{jopParameters[requestDate]}") String requestDate) {
    log.info("==> writer value : {}", requestDate);

    return new JpaItemWriterBuilder<QrCode>()
      .entityManagerFactory(entityManagerFactory)
      .build();
  }

  @StepScope
  private ItemProcessor<QrCode,? extends QrCode> expiredQr(@Value("#{jopParameters[requestDate]}") String requestDate) {
    log.info("==> processor value : {}", requestDate);
    return qrCode -> {
      publisher.publishEvent(new QrCodeExpiredApplicationEvent(this, qrCode));
      return qrCode;
    };
  }

  @StepScope
  private ItemReader<QrCode> expiredQrReader(@Value("#{jopParameters[requestDate]}") String requestDate) {
    log.info("==> reader value : {}", requestDate);

    return new JpaPagingItemReaderBuilder <QrCode>()
      .pageSize(10)
      .entityManagerFactory(entityManagerFactory)
      .queryString("SELECT q FROM QrCode q WHERE q.dueDate = CURRENT_DATE - 1")
      .name("expiredQrReader")
      .build();
  }


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
      .reader(deleteQrReader(null))
      .processor(deleteQrCode(null))
      .writer(deleteQrWriter(null))
      .build();
  }

  @StepScope
  private ItemWriter<QrCode> deleteQrWriter(@Value("#{jopParameters[requestDate]}") String requestDate) {
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
      publisher.publishEvent(new QrCodeRemoveApplicationEvent(this, qrCode));
      return qrCode;
    };
  }

  @StepScope
  private JpaPagingItemReader<QrCode> deleteQrReader(@Value("#{jopParameters[requestDate]}") String requestDate) {
    log.info("==> reader value : {}", requestDate);

    return new JpaPagingItemReaderBuilder <QrCode>()
      .pageSize(10)
      .entityManagerFactory(entityManagerFactory)
      .queryString("SELECT q FROM QrCode q WHERE q.dueDate <= CURRENT_DATE")
      .name("deleteQrReader")
      .build();
  }
}
