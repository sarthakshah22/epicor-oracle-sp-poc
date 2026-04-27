package com.example.demo.orchestrator;

import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

@Component
public class GenericProcedureExecutor {

    private final DataSource dataSource;

    public GenericProcedureExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> List<T> execute(String procedureCall,
                              List<Object> inputs,
                              Function<ResultSet, T> mapper) throws Exception {

        List<T> results = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            CallableStatement cs = conn.prepareCall(procedureCall);

            int index = 1;
            for (Object input : inputs) {
                cs.setObject(index++, input);
            }

            cs.registerOutParameter(index, Types.OTHER);

            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(index);

            while (rs.next()) {
                results.add(mapper.apply(rs));
            }

            conn.commit();
        }

        return results;
    }

//    public <T> List<T> executeWithCursor(String procedureName,
//                                         List<Object> inputs,
//                                         Function<ResultSet, T> mapper) throws Exception {
//
//        List<T> results = new ArrayList<>();
//
//        try (Connection conn = dataSource.getConnection()) {
//
//            conn.setAutoCommit(false); // REQUIRED
//
//            String cursorName = "mycursor_" + System.currentTimeMillis();
//
//            // Build CALL statement
//            String callSql = "{ call " + procedureName + "(?, ?, ?) }";
//
//            try (CallableStatement cs = conn.prepareCall(callSql)) {
//
//                int index = 1;
//                for (Object input : inputs) {
//                    cs.setObject(index++, input);
//                }
//
//                // Pass cursor name (NOT registerOutParameter)
//                cs.setString(index, cursorName);
//
//                cs.execute();
//
//                // Now fetch from cursor
//                try (Statement stmt = conn.createStatement()) {
//                    ResultSet rs = stmt.executeQuery("FETCH ALL FROM " + cursorName);
//
//                    while (rs.next()) {
//                        results.add(mapper.apply(rs));
//                    }
//                }
//            }
//
//            conn.commit();
//        }
//
//        return results;
//    }

    public <T> List<T> executeWithCursor(String procedureName,
                                         List<Object> inputs,
                                         Function<ResultSet, T> mapper) throws Exception {

        List<T> results = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {

            conn.setAutoCommit(false); // REQUIRED

            String cursorName = "mycursor_" + System.currentTimeMillis();

            String callSql = "CALL " + procedureName + "(?, ?, CAST(? AS REFCURSOR))";

            try (CallableStatement cs = conn.prepareCall(callSql)) {

                int index = 1;
                for (Object input : inputs) {
                    cs.setObject(index++, input);
                }

                // Pass cursor name
                cs.setString(index, cursorName);

                cs.execute();

                try (Statement stmt = conn.createStatement()) {
                    ResultSet rs = stmt.executeQuery("FETCH ALL FROM " + cursorName);

                    while (rs.next()) {
                        results.add(mapper.apply(rs));
                    }
                }
            }

            conn.commit();
        }

        return results;
    }
}
