package com.example.demo.orchestrator;

import com.example.demo.dto.CustomerPaymentDTO;
import com.example.demo.dto.CustomerSummaryDTO;
import com.example.demo.mapper.CustomerMapper;
import com.example.demo.mapper.PaymentMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerOrchestrator {

    private final GenericProcedureExecutor executor;

    public CustomerOrchestrator(GenericProcedureExecutor executor) {
        this.executor = executor;
    }

    public Map<String, Object> orchestrate(String city, double minAmount, String status) throws Exception {

        // SP1 Call
        List<CustomerSummaryDTO> orderSummary =
                executor.executeWithCursor(
                        "get_customer_order_summary_procedure",
                        Arrays.asList(city, minAmount),
                        new CustomerMapper()
                );

        // SP2 Call
        List<CustomerPaymentDTO> paymentSummary =
                executor.executeWithCursor(
                        "get_customer_payment_summary",
                        Arrays.asList(city, status),
                        new PaymentMapper()
                );

        // Combine response
        Map<String, Object> response = new HashMap<>();
        response.put("orderSummary", orderSummary);
        response.put("paymentSummary", paymentSummary);

        return response;
    }
}