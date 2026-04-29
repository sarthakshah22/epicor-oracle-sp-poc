package com.example.demo.service;

import com.example.demo.dto.CustomerSummaryDTO;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.orchestrator.GenericProcedureExecutor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomerSummaryService {

    private final GenericProcedureExecutor executor;
    private final CustomerMapper customerMapper;

    public CustomerSummaryService(GenericProcedureExecutor executor,
                                  CustomerMapper customerMapper) {
        this.executor = executor;
        this.customerMapper = customerMapper;
    }

    public List<CustomerSummaryDTO> getSummary(String city, double minAmount) {
        return executor.executeWithCursor(
                "get_customer_order_summary_procedure",
                Arrays.asList(city, minAmount),
                customerMapper
        );
    }
}
