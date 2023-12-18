package org.yascode.firstsystem.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.yascode.firstsystem.dto.ApplicationCsv;
import org.yascode.firstsystem.entity.Application;

import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;
    private final CsvFileReader csvFileReader;
    private final JdbcCursorItemReader<ApplicationCsv> dbItemReader;
    private final JdbcPagingItemReader<ApplicationCsv> applicationJdbcPagingItemReader;
    private ApplicationItemProcessor<ApplicationCsv, Application> applicationItemProcessor;

    private ApplicationItemWriter applicationItemWriter;

    public BatchConfiguration(PlatformTransactionManager transactionManager,
                              JobRepository jobRepository,
                              CsvFileReader csvFileReader,
                              JdbcCursorItemReader<ApplicationCsv> dbItemReader,
                              JdbcPagingItemReader<ApplicationCsv> applicationJdbcPagingItemReader,
                              ApplicationItemProcessor<ApplicationCsv, Application> applicationItemProcessor,
                              ApplicationItemWriter applicationItemWriter) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
        this.csvFileReader = csvFileReader;
        this.dbItemReader = dbItemReader;
        this.applicationJdbcPagingItemReader = applicationJdbcPagingItemReader;
        this.applicationItemProcessor = applicationItemProcessor;
        this.applicationItemWriter = applicationItemWriter;
    }

    @Bean
    public ItemReader<ApplicationCsv> myCsvFileReader() {
        String filePath;
        ListItemReader listItemReader = null;
        try {
            filePath = new ClassPathResource("data/applications-data.csv").getFile().getAbsolutePath();
            listItemReader = new ListItemReader(csvFileReader.readApplicationsFromCsv(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listItemReader;
    }

    @Bean
    public Job importApplicationsJob() {
        return new JobBuilder("importApplicationsJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<ApplicationCsv, Application>chunk(10, transactionManager)
                .reader(applicationJdbcPagingItemReader)
                .processor(applicationItemProcessor)
                .writer(applicationItemWriter)
                .build();
    }

    /*@Bean
    public ApplicationItemWriter applicationItemWriter() {
        return new ApplicationItemWriter(rabbitTemplate);
    }*/

}
