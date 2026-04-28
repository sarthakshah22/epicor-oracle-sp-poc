package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.*;

import com.example.demo.dto.CustomerSummaryDTO;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.orchestrator.GenericProcedureExecutor;

@Service
public class CustomerService {

    private final GenericProcedureExecutor executor;
    private final CustomerMapper customerMapper;

    public CustomerService(GenericProcedureExecutor executor,
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