package org.yascode.firstsystem.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class BatchJobRunner {

    Logger logger = LoggerFactory.getLogger(getClass());

    private JobLauncher jobLauncher;
    private Job importApplicationsJob;

    public BatchJobRunner(JobLauncher jobLauncher,
                          Job importApplicationsJob) {
        this.jobLauncher = jobLauncher;
        this.importApplicationsJob = importApplicationsJob;
    }

    private JobParameters jobParameters() {
        return new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

    }

    @Scheduled(cron = "0 19 21 * * *")
    public void jobExecution() {
        try {
            JobExecution jobExecution = jobLauncher.run(importApplicationsJob, jobParameters());
            logger.info("Job Exit Status : {}", jobExecution.getStatus());
        } catch (JobExecutionAlreadyRunningException
                 | JobRestartException
                 | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

}
