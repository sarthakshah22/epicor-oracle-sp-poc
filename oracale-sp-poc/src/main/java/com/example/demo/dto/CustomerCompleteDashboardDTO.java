package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Complete customer dashboard response containing summary, payment, and risk analytics data")
public class CustomerCompleteDashboardDTO {

    @Schema(description = "Customer order summary data")
    private List<CustomerSummaryDTO> customerSummary;

    @Schema(description = "Customer payment summary data")
    private List<CustomerPaymentDTO> paymentSummary;

    @Schema(description = "Customer risk analytics data")
    private List<CustomerRiskAnalyticsDTO> riskAnalytics;

    public List<CustomerSummaryDTO> getCustomerSummary() {
        return customerSummary;
    }

    public void setCustomerSummary(List<CustomerSummaryDTO> customerSummary) {
        this.customerSummary = customerSummary;
    }

    public List<CustomerPaymentDTO> getPaymentSummary() {
        return paymentSummary;
    }

    public void setPaymentSummary(List<CustomerPaymentDTO> paymentSummary) {
        this.paymentSummary = paymentSummary;
    }

    public List<CustomerRiskAnalyticsDTO> getRiskAnalytics() {
        return riskAnalytics;
    }

    public void setRiskAnalytics(List<CustomerRiskAnalyticsDTO> riskAnalytics) {
        this.riskAnalytics = riskAnalytics;
    }
}
