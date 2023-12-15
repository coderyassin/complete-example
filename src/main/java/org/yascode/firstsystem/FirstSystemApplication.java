package org.yascode.firstsystem;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.yascode.firstsystem.batch.CsvFileReader;
import org.yascode.firstsystem.entity.Application;
import org.yascode.firstsystem.repository.ApplicationRepository;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class FirstSystemApplication {

    private final CsvFileReader csvFileReader;
    private final ApplicationRepository applicationRepository;

    public FirstSystemApplication(CsvFileReader csvFileReader, ApplicationRepository applicationRepository) {
        this.csvFileReader = csvFileReader;
        this.applicationRepository = applicationRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(FirstSystemApplication.class, args);
    }

    //@PostConstruct
    /*public void insertData() throws IOException {
        String filePath = "C:\\Users\\Yassin\\Technical_Expert\\Distributed_High_Performance_Computing\\Event_Driven_Distributed_Processing_With_Spring_Cloud_Stream\\projects\\rabbitmq\\complete-example\\first-system\\src\\main\\resources\\data\\applications-data.csv";
        List<Application> applications = csvFileReader.readApplicationsFromCsv(filePath);
        applicationRepository.saveAll(applications);
    }*/

}
