package com.example.demo.mapper;

import com.example.demo.dto.CustomerPaymentDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PaymentMapper implements RowMapper<CustomerPaymentDTO> {

    @Override
    public CustomerPaymentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        CustomerPaymentDTO dto = new CustomerPaymentDTO();

        dto.setId(rs.getInt("id"));
        dto.setName(rs.getString("name"));
        dto.setTotalOrders(rs.getInt("total_orders"));
        dto.setTotalAmount(rs.getDouble("total_amount"));
        dto.setStatus(rs.getString("status"));

        return dto;
    }
}