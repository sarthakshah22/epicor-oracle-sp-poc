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

        return executor.executeWithCursor(
                "get_customer_order_summary_procedure",
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
