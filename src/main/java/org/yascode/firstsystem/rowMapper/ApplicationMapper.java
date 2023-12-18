package org.yascode.firstsystem.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import org.yascode.firstsystem.entity.Application;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationMapper implements RowMapper<Application> {

    public static final String ID_COLUMN = "id";
    public static final String CODE_COLUMN = "code";
    public static final String NAME_COLUMN = "name";
    @Override
    public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Application.builder()
                .id(rs.getLong(ID_COLUMN))
                .name(rs.getString(CODE_COLUMN))
                .code(rs.getString(NAME_COLUMN))
                .build();
    }

}
