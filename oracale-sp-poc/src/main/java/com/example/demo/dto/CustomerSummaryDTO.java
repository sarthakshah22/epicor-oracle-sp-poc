package com.example.demo.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;

@Schema(description = "Customer order summary returned from the order summary stored procedure")
public class CustomerSummaryDTO {

    @Schema(description = "Customer identifier", example = "101")
    private int id;

    @Schema(description = "Customer name", example = "Amit Sharma")
    private String name;

    @Schema(description = "Customer city", example = "Mumbai")
    private String city;

    @Schema(description = "Total number of orders", example = "4")
    private int totalOrders;

    @Schema(description = "Total order amount", example = "12450.75")
    private double totalAmount;

    @Schema(description = "Timestamp of the last order", example = "2026-04-28T14:30:00Z")
    private Timestamp lastOrderDate;

    public CustomerSummaryDTO() {}

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

    public Timestamp getLastOrderDate() {
        return lastOrderDate;
    }

    public void setLastOrderDate(Timestamp lastOrderDate) {
        this.lastOrderDate = lastOrderDate;
    }
}
