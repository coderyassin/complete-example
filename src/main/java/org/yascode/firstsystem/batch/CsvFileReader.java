package org.yascode.firstsystem.batch;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;
import org.yascode.firstsystem.dto.ApplicationCsv;
import org.yascode.firstsystem.entity.Application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Component
public class CsvFileReader {

    public List<ApplicationCsv> readApplicationsFromCsv(String filePath) throws IOException {
        try (Reader reader = new FileReader(filePath)) {
            CsvToBean<ApplicationCsv> csvToBean = new CsvToBeanBuilder<ApplicationCsv>(reader)
                    .withType(ApplicationCsv.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        }
    }

}
