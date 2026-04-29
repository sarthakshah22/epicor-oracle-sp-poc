package com.example.demo.service;

import com.example.demo.dto.CustomerPaymentDTO;
import com.example.demo.mapper.PaymentMapper;
import com.example.demo.orchestrator.GenericProcedureExecutor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PaymentService {

    private final GenericProcedureExecutor executor;
    private final PaymentMapper paymentMapper;

    public PaymentService(GenericProcedureExecutor executor,
                          PaymentMapper paymentMapper) {
        this.executor = executor;
        this.paymentMapper = paymentMapper;
    }

    public List<CustomerPaymentDTO> getPaymentSummary(String city, String status) {
        return executor.executeWithCursor(
                "get_customer_payment_summary",
                Arrays.asList(city, status),
                paymentMapper
        );
    }
}
