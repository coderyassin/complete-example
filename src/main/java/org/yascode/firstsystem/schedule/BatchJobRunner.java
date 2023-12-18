package org.yascode.firstsystem.schedule;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class BatchJobRunner {

    Logger logger = LoggerFactory.getLogger(getClass());
    private final ConfigurableApplicationContext applicationContext;
    private JobLauncher jobLauncher;
    private Job importApplicationsJob;

    public BatchJobRunner(ConfigurableApplicationContext applicationContext, JobLauncher jobLauncher,
                          Job importApplicationsJob) {
        this.applicationContext = applicationContext;
        this.jobLauncher = jobLauncher;
        this.importApplicationsJob = importApplicationsJob;
    }

    private JobParameters jobParameters() {
        return new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

    }

    @Scheduled(fixedDelay = 1000)
    public void runJobOnStartup() {
        jobExecution();
    }

    public void jobExecution() {
        try {
            JobExecution jobExecution = jobLauncher.run(importApplicationsJob, jobParameters());
            logger.info("Job Exit Status : {}", jobExecution.getStatus());
            shutdownApplication();
        } catch (JobExecutionAlreadyRunningException
                 | JobRestartException
                 | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    private void shutdownApplication() {
        logger.info("Application termination...");

        try {
            applicationContext.getBean(HikariDataSource.class).close();
        } catch (Exception e) {
            logger.error("Error closing Hikari connection pool", e);
        }

        applicationContext.close();
    }

}
