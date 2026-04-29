package com.example.demo.orchestrator;

import com.example.demo.dto.CustomerCompleteDashboardDTO;
import com.example.demo.dto.CustomerPaymentDTO;
import com.example.demo.dto.CustomerRiskAnalyticsDTO;
import com.example.demo.dto.CustomerSummaryDTO;
import com.example.demo.service.CustomerRiskAnalyticsService;
import com.example.demo.service.CustomerSummaryService;
import com.example.demo.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerOrchestrator {

    private final CustomerSummaryService customerSummaryService;
    private final PaymentService paymentService;
    private final CustomerRiskAnalyticsService customerRiskAnalyticsService;

    public CustomerOrchestrator(CustomerSummaryService customerSummaryService,
                                PaymentService paymentService,
                                CustomerRiskAnalyticsService customerRiskAnalyticsService) {
        this.customerSummaryService = customerSummaryService;
        this.paymentService = paymentService;
        this.customerRiskAnalyticsService = customerRiskAnalyticsService;
    }

    public Map<String, Object> orchestrate(String city, double minAmount, String status) {

        List<CustomerSummaryDTO> orderSummary = customerSummaryService.getSummary(city, minAmount);
        List<CustomerPaymentDTO> paymentSummary = paymentService.getPaymentSummary(city, status);

        Map<String, Object> response = new HashMap<>();
        response.put("orderSummary", orderSummary);
        response.put("paymentSummary", paymentSummary);

        return response;
    }

    public CustomerCompleteDashboardDTO orchestrateCompleteDashboard(String city,
                                                                    double minAmount,
                                                                    String status,
                                                                    LocalDateTime startDate,
                                                                    String riskLevel) {

        List<CustomerSummaryDTO> customerSummary = customerSummaryService.getSummary(city, minAmount);
        List<CustomerPaymentDTO> paymentSummary = paymentService.getPaymentSummary(city, status);
        List<CustomerRiskAnalyticsDTO> riskAnalytics =
                customerRiskAnalyticsService.getRiskAnalytics(city, minAmount, status, startDate, riskLevel);

        CustomerCompleteDashboardDTO response = new CustomerCompleteDashboardDTO();
        response.setCustomerSummary(customerSummary);
        response.setPaymentSummary(paymentSummary);
        response.setRiskAnalytics(riskAnalytics);

        return response;
    }
}
