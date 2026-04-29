package com.example.demo.service;

import com.example.demo.dto.CustomerRiskAnalyticsDTO;
import com.example.demo.mapper.CustomerRiskAnalyticsMapper;
import com.example.demo.orchestrator.GenericProcedureExecutor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerRiskAnalyticsService {

    private final GenericProcedureExecutor executor;
    private final CustomerRiskAnalyticsMapper customerRiskAnalyticsMapper;

    public CustomerRiskAnalyticsService(GenericProcedureExecutor executor,
                                        CustomerRiskAnalyticsMapper customerRiskAnalyticsMapper) {
        this.executor = executor;
        this.customerRiskAnalyticsMapper = customerRiskAnalyticsMapper;
    }

    public List<CustomerRiskAnalyticsDTO> getRiskAnalytics(String city,
                                                           double minAmount,
                                                           String status,
                                                           LocalDateTime startDate,
                                                           String riskLevel) {
        return executor.executeWithCursor(
                "get_customer_risk_analytics",
                Arrays.asList(city, minAmount, status, Timestamp.valueOf(startDate), riskLevel),
                customerRiskAnalyticsMapper
        );
    }
}
