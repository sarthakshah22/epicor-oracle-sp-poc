package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Customer payment summary returned from the payment summary stored procedure")
public class CustomerPaymentDTO {

    @Schema(description = "Customer identifier", example = "101")
    private int id;

    @Schema(description = "Customer name", example = "Amit Sharma")
    private String name;

    @Schema(description = "Total number of orders", example = "4")
    private int totalOrders;

    @Schema(description = "Total amount associated with the payment summary", example = "12450.75")
    private double totalAmount;

    @Schema(description = "Payment status", example = "COMPLETED")
    private String status;

    public CustomerPaymentDTO() {}

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

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
