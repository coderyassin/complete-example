package org.yascode.firstsystem.rowMapper;

import org.springframework.jdbc.core.RowMapper;
import org.yascode.firstsystem.dto.ApplicationCsv;
import org.yascode.firstsystem.entity.Application;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationRowMapper implements RowMapper<ApplicationCsv> {
    public static final String ID_COLUMN = "ID";
    public static final String CODE_COLUMN = "CODE";
    public static final String NAME_COLUMN = "NAME";
    @Override
    public ApplicationCsv mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ApplicationCsv.builder()
                .id(rs.getLong(ID_COLUMN))
                .name(rs.getString(CODE_COLUMN))
                .code(rs.getString(NAME_COLUMN))
                .build();
    }
}
