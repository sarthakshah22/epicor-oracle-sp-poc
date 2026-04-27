package com.example.demo.mapper;

import com.example.demo.dto.CustomerSummaryDTO;
import java.sql.ResultSet;

public class CustomerMapper {

    public static CustomerSummaryDTO map(ResultSet rs) throws Exception {
        CustomerSummaryDTO dto = new CustomerSummaryDTO();
        dto.setId(rs.getInt("id"));
        dto.setName(rs.getString("name"));
        dto.setCity(rs.getString("city"));
        dto.setTotalOrders(rs.getInt("total_orders"));
        dto.setTotalAmount(rs.getDouble("total_amount"));
        dto.setLastOrderDate(rs.getTimestamp("last_order_date"));
        return dto;
    }
}
