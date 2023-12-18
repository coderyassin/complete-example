package org.yascode.firstsystem.batch;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yascode.firstsystem.dto.ApplicationCsv;
import org.yascode.firstsystem.entity.Application;
import org.yascode.firstsystem.rowMapper.ApplicationMapper;
import org.yascode.firstsystem.rowMapper.ApplicationRowMapper;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    @Bean
    public JdbcPagingItemReader<ApplicationCsv> jdbcPagingItemReader() {
        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);

        return new JdbcPagingItemReaderBuilder<ApplicationCsv>()
                .name("jdbcPagingItemReader")
                .dataSource(dataSource)
                .fetchSize(100)
                .rowMapper(new ApplicationRowMapper())
                .sortKeys(sortKeys)
                .pageSize(500)
                .queryProvider(createQueryProvider())
                .build();
    }

    private PagingQueryProvider createQueryProvider() {
        SqlPagingQueryProviderFactoryBean queryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        queryProviderFactoryBean.setDataSource(dataSource);
        queryProviderFactoryBean.setSelectClause("select ID, CODE, NAME");
        queryProviderFactoryBean.setFromClause("FROM applications");
        queryProviderFactoryBean.setWhereClause("WHERE NAME LIKE 'RandomName1%'");
        queryProviderFactoryBean.setSortKeys(Collections.singletonMap("id", Order.ASCENDING));
        try {
            return queryProviderFactoryBean.getObject();
        } catch (Exception e) {
            throw new IllegalStateException("Error creating query provider", e);
        }
    }

}
