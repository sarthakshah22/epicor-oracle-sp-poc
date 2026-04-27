#!/bin/bash

PROJECT_NAME=postgres-sp-poc
BASE_PACKAGE=com.example.demo

echo "Creating project: $PROJECT_NAME"

mkdir -p $PROJECT_NAME
cd $PROJECT_NAME

# Create folders
mkdir -p src/main/java/com/example/demo/{api,service,orchestrator,mapper,dto,exception}
mkdir -p src/main/resources

# ---------------- POM ----------------
cat > pom.xml <<EOF
<project xmlns="http://maven.apache.org/POM/4.0.0">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.example</groupId>
  <artifactId>postgres-sp-poc</artifactId>
  <version>0.0.1-SNAPSHOT</version>

  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.7.18</version>
  </parent>

  <properties>
    <java.version>11</java.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>

    <dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>postgresql</artifactId>
    </dependency>

    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <optional>true</optional>
    </dependency>
  </dependencies>
</project>
EOF

# ---------------- APPLICATION ----------------
cat > src/main/java/com/example/demo/Application.java <<EOF
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
EOF

# ---------------- PROPERTIES ----------------
cat > src/main/resources/application.properties <<EOF
spring.datasource.url=jdbc:postgresql://localhost:5432/yourdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword
logging.level.root=INFO
EOF

# ---------------- DTO ----------------
cat > src/main/java/com/example/demo/dto/CustomerSummaryDTO.java <<EOF
package com.example.demo.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class CustomerSummaryDTO {
    private int id;
    private String name;
    private String city;
    private int totalOrders;
    private double totalAmount;
    private Timestamp lastOrderDate;
}
EOF

# ---------------- MAPPER ----------------
cat > src/main/java/com/example/demo/mapper/CustomerMapper.java <<EOF
package com.example.demo.mapper;

import com.example.demo.dto.CustomerSummaryDTO;
import java.sql.ResultSet;

public class CustomerMapper {

    public static CustomerSummaryDTO map(ResultSet rs) throws Exception {
        CustomerSummaryDTO dto = new CustomerSummaryDTO();
        dto.setId(rs.getInt("id"));
        dto.setName(rs.getString("name"));
        dto.setCity(rs.getString("city"));
        dto.setTotalOrders(rs.getInt("total_orders"));
        dto.setTotalAmount(rs.getDouble("total_amount"));
        dto.setLastOrderDate(rs.getTimestamp("last_order_date"));
        return dto;
    }
}
EOF

# ---------------- EXCEPTION ----------------
cat > src/main/java/com/example/demo/exception/GlobalExceptionHandler.java <<EOF
package com.example.demo.exception;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
EOF

# ---------------- GENERIC EXECUTOR ----------------
cat > src/main/java/com/example/demo/orchestrator/GenericProcedureExecutor.java <<EOF
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
}
EOF

# ---------------- SERVICE ----------------
cat > src/main/java/com/example/demo/service/CustomerService.java <<EOF
package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.*;
import com.example.demo.dto.CustomerSummaryDTO;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.orchestrator.GenericProcedureExecutor;

@Service
public class CustomerService {

    private final GenericProcedureExecutor executor;

    public CustomerService(GenericProcedureExecutor executor) {
        this.executor = executor;
    }

    public List<CustomerSummaryDTO> getSummary(String city, double minAmount) throws Exception {
        return executor.execute(
            "{ call get_customer_order_summary(?, ?, ?) }",
            Arrays.asList(city, minAmount),
            rs -> {
                try {
                    return CustomerMapper.map(rs);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        );
    }
}
EOF

# ---------------- CONTROLLER ----------------
cat > src/main/java/com/example/demo/api/CustomerController.java <<EOF
package com.example.demo.api;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.example.demo.dto.CustomerSummaryDTO;
import com.example.demo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/customer-summary")
    public List<CustomerSummaryDTO> getSummary(@RequestParam String city,
                                               @RequestParam double minAmount) throws Exception {
        return service.getSummary(city, minAmount);
    }
}
EOF

echo "✅ Project created successfully!"
echo "👉 Next steps:"
echo "cd $PROJECT_NAME"
echo "mvn spring-boot:run"