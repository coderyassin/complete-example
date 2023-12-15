package org.yascode.firstsystem.batch;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yascode.firstsystem.dto.ApplicationCsv;
import org.yascode.firstsystem.entity.Application;
import org.yascode.firstsystem.rowMapper.ApplicationRowMapper;

import javax.sql.DataSource;

@Configuration
public class DBItemReader {

    private final DataSource dataSource;

    public DBItemReader(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcCursorItemReader<ApplicationCsv> dbItemReader() {
        return new JdbcCursorItemReaderBuilder<ApplicationCsv>()
                .dataSource(dataSource)
                .name("creditReader")
                .sql("select ID, CODE, NAME from applications")
                .rowMapper(new ApplicationRowMapper())
                .build();

    }

    /*@Bean
    public DataSource postgresqlDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://192.168.120.17:5432/first-system-ds");
        dataSourceBuilder.username("yascode");
        dataSourceBuilder.password("YassMel97");
        return dataSourceBuilder.build();
    }*/

}
