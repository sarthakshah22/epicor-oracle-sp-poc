package com.example.demo.mapper;

import com.example.demo.dto.CustomerRiskAnalyticsDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRiskAnalyticsMapper implements RowMapper<CustomerRiskAnalyticsDTO> {

    @Override
    public CustomerRiskAnalyticsDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerRiskAnalyticsDTO dto = new CustomerRiskAnalyticsDTO();

        dto.setId(rs.getInt("id"));
        dto.setName(rs.getString("name"));
        dto.setCity(rs.getString("city"));
        dto.setTotalTransactions(rs.getInt("total_transactions"));
        dto.setTotalSuccessAmount(rs.getDouble("total_success_amount"));
        dto.setFailedTxnCount(rs.getInt("failed_txn_count"));
        dto.setAvgTxnAmount(rs.getDouble("avg_txn_amount"));
        dto.setLastTxnDate(rs.getTimestamp("last_txn_date"));
        dto.setRiskScore(rs.getInt("risk_score"));
        dto.setCustomerCategory(rs.getString("customer_category"));
        dto.setFraudIndicator(rs.getString("fraud_indicator"));

        return dto;
    }
}
