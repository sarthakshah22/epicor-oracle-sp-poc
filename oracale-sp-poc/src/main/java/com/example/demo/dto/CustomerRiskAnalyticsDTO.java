package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Timestamp;

@Schema(description = "Customer risk analytics returned from the risk analytics stored procedure")
public class CustomerRiskAnalyticsDTO {

    @Schema(description = "Customer identifier", example = "101")
    private int id;

    @Schema(description = "Customer name", example = "Amit Sharma")
    private String name;

    @Schema(description = "Customer city", example = "Mumbai")
    private String city;

    @Schema(description = "Total number of transactions considered in the analytics", example = "7")
    private int totalTransactions;

    @Schema(description = "Total amount of successful transactions", example = "8450.50")
    private double totalSuccessAmount;

    @Schema(description = "Count of failed transactions", example = "2")
    private int failedTxnCount;

    @Schema(description = "Average transaction amount", example = "1207.21")
    private double avgTxnAmount;

    @Schema(description = "Most recent transaction timestamp", example = "2026-04-29T10:30:00Z")
    private Timestamp lastTxnDate;

    @Schema(description = "Calculated risk score for the customer", example = "12")
    private int riskScore;

    @Schema(description = "Customer category derived from transaction amount", example = "GOLD")
    private String customerCategory;

    @Schema(description = "Fraud indicator derived from risk and failed transaction counts", example = "NORMAL")
    private String fraudIndicator;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(int totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public double getTotalSuccessAmount() {
        return totalSuccessAmount;
    }

    public void setTotalSuccessAmount(double totalSuccessAmount) {
        this.totalSuccessAmount = totalSuccessAmount;
    }

    public int getFailedTxnCount() {
        return failedTxnCount;
    }

    public void setFailedTxnCount(int failedTxnCount) {
        this.failedTxnCount = failedTxnCount;
    }

    public double getAvgTxnAmount() {
        return avgTxnAmount;
    }

    public void setAvgTxnAmount(double avgTxnAmount) {
        this.avgTxnAmount = avgTxnAmount;
    }

    public Timestamp getLastTxnDate() {
        return lastTxnDate;
    }

    public void setLastTxnDate(Timestamp lastTxnDate) {
        this.lastTxnDate = lastTxnDate;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    public String getCustomerCategory() {
        return customerCategory;
    }

    public void setCustomerCategory(String customerCategory) {
        this.customerCategory = customerCategory;
    }

    public String getFraudIndicator() {
        return fraudIndicator;
    }

    public void setFraudIndicator(String fraudIndicator) {
        this.fraudIndicator = fraudIndicator;
    }
}
