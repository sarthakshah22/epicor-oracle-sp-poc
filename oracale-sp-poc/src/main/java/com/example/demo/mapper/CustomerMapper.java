package com.example.demo.mapper;

import com.example.demo.dto.CustomerSummaryDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerMapper implements RowMapper<CustomerSummaryDTO> {

    @Override
    public CustomerSummaryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

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