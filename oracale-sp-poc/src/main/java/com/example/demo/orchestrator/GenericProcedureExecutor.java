package com.example.demo.orchestrator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

import oracle.jdbc.OracleTypes;

@Component
@Slf4j
public class GenericProcedureExecutor {

    private final DataSource dataSource;

    public GenericProcedureExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> List<T> executeWithCursor(String procedureName,
                                         List<Object> inputs,
                                         RowMapper<T> mapper) {

        List<T> results = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            String callSql = "{call " + procedureName + "(" + String.join(",", Collections.nCopies(inputs.size() + 1, "?")) + ")}";

            try (CallableStatement cs = conn.prepareCall(callSql)) {

                int i = 1;
                for (Object input : inputs) {
                    cs.setObject(i++, input);
                }

                cs.registerOutParameter(i, OracleTypes.CURSOR);

                log.info("Executing procedure: {}", procedureName);

                cs.execute();

                ResultSet rs = (ResultSet) cs.getObject(i);

                while (rs.next()) {
                    results.add(mapper.mapRow(rs, rs.getRow()));
                }

                rs.close();
            }

        } catch (Exception ex) {
            log.error("Error executing procedure {}", procedureName, ex);
            throw new RuntimeException(ex);
        }

        return results;
    }
}