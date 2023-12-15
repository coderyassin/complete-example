package org.yascode.firstsystem.config;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.yascode.firstsystem.batch.CsvFileReader;
import org.yascode.firstsystem.dto.ApplicationCsv;
import org.yascode.firstsystem.entity.Application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseInitializer {
    private final CsvFileReader csvFileReader;
    private final JdbcTemplate jdbcTemplate;
    private final ModelMapper modelMapper;

    public DatabaseInitializer(CsvFileReader csvFileReader, JdbcTemplate jdbcTemplate, ModelMapper modelMapper) {
        this.csvFileReader = csvFileReader;
        this.jdbcTemplate = jdbcTemplate;
        this.modelMapper = modelMapper;
    }

    //@PostConstruct
    public void postConstruct() {

        String filePath;
        try {
            filePath = new ClassPathResource("data/applications-data.csv").getFile().getAbsolutePath();
            List<ApplicationCsv> applicationCsvs = csvFileReader.readApplicationsFromCsv(filePath);
            List<Application> applications = new ArrayList<>();
            applicationCsvs.forEach(applicationCsv -> applications.add(modelMapper.map(applicationCsv, Application.class)));

            String sql = "INSERT INTO applications(code, name) VALUES (?, ?)";

            jdbcTemplate.batchUpdate(sql,
                    applications,
                    applications.size(),
                    (ps, application) -> {
                        ps.setString(1, application.getCode());
                        ps.setString(2, application.getName());
                    });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
