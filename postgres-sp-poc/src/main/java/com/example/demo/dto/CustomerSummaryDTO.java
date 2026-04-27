package com.example.demo.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class CustomerSummaryDTO {
    private int id;
    private String name;
    private String city;
    private int totalOrders;
    private double totalAmount;
    private Timestamp lastOrderDate;
}
