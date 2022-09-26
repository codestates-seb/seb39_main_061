package com.project.QR.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@EnableScheduling
public class BatchScheduler {
  @Autowired
  private JobLauncher jobLauncher;
  @Autowired
  private BatchConfig batchConfig;

  @Scheduled(cron = "0 0 0 * * *")
  public void runJob() {
    Map<String, JobParameter> confMap = new HashMap<>();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date time = new Date();
    String converted = format.format(time);

    confMap.put("requestDate", new JobParameter(converted));
    JobParameters jobParameters = new JobParameters(confMap);

    try {
      jobLauncher.run(batchConfig.deleteJob(),  jobParameters);
    } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
             | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {

      log.error(e.getMessage());
    }
  }
}
