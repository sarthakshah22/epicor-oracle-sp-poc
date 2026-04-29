package com.example.demo.api;

import com.example.demo.dto.CustomerCompleteDashboardDTO;
import com.example.demo.dto.CustomerRiskAnalyticsDTO;
import com.example.demo.dto.CustomerSummaryDTO;
import com.example.demo.orchestrator.CustomerOrchestrator;
import com.example.demo.service.CustomerRiskAnalyticsService;
import com.example.demo.service.CustomerSummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Customer API", description = "Endpoints for customer order and payment summaries")
public class CustomerController {

    private final CustomerSummaryService customerSummaryService;
    private final CustomerRiskAnalyticsService customerRiskAnalyticsService;

    private final CustomerOrchestrator orchestrator;

    public CustomerController(CustomerSummaryService customerSummaryService,
                              CustomerRiskAnalyticsService customerRiskAnalyticsService,
                              CustomerOrchestrator orchestrator) {
        this.customerSummaryService = customerSummaryService;
        this.customerRiskAnalyticsService = customerRiskAnalyticsService;
        this.orchestrator = orchestrator;
    }

    //http://localhost:8080/api/customer-dashboard?city=Mumbai&minAmount=500&status=COMPLETED
    //Calling both SP1 and SP2 through orchestration
    @GetMapping("/customer-dashboard")
    @Operation(
            summary = "Get customer dashboard",
            description = "Calls two Oracle stored procedures and combines order and payment summaries in one response."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer dashboard generated successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to generate customer dashboard")
    })
    public Map<String, Object> dashboard(
            @Parameter(description = "City to filter customers by", example = "Mumbai")
            @RequestParam String city,
            @Parameter(description = "Minimum total order amount", example = "500")
            @RequestParam double minAmount,
            @Parameter(description = "Payment status to filter by", example = "COMPLETED")
            @RequestParam String status) {

        return orchestrator.orchestrate(city, minAmount, status);
    }


    //http://localhost:8080/api/customer-summary?city=Mumbai&minAmount=500
    //Directly calling SP1 without orchestration
    @GetMapping("/customer-summary")
    @Operation(
            summary = "Get customer order summary",
            description = "Calls the customer order summary Oracle stored procedure and returns the mapped results."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer summary fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to fetch customer summary")
    })
    public List<CustomerSummaryDTO> getSummary(
            @Parameter(description = "City to filter customers by", example = "Mumbai")
            @RequestParam String city,
            @Parameter(description = "Minimum total order amount", example = "500")
            @RequestParam double minAmount) {
        return customerSummaryService.getSummary(city, minAmount);
    }

    // http://localhost:8080/api/customer-risk-analytics?city=Mumbai&minAmount=500&status=SUCCESS&startDate=2026-04-01T00:00:00&riskLevel=HIGH
    @GetMapping("/customer-risk-analytics")
    @Operation(
            summary = "Get customer risk analytics",
            description = "Calls the customer risk analytics Oracle stored procedure and returns risk-related customer analytics."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer risk analytics fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to fetch customer risk analytics")
    })
    public List<CustomerRiskAnalyticsDTO> getRiskAnalytics(
            @Parameter(description = "City to filter customers by", example = "Mumbai")
            @RequestParam String city,
            @Parameter(description = "Minimum transaction amount", example = "500")
            @RequestParam double minAmount,
            @Parameter(description = "Transaction status to filter by", example = "SUCCESS")
            @RequestParam String status,
            @Parameter(description = "Start timestamp for transaction filtering in ISO format", example = "2026-04-01T00:00:00")
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Risk level to filter by", example = "HIGH")
            @RequestParam String riskLevel) {
        return customerRiskAnalyticsService.getRiskAnalytics(city, minAmount, status, startDate, riskLevel);
    }

    // http://localhost:8080/api/customer-complete-dashboard?city=Mumbai&minAmount=500&status=SUCCESS&startDate=2026-04-01T00:00:00&riskLevel=HIGH
    @GetMapping("/customer-complete-dashboard")
    @Operation(
            summary = "Get complete customer dashboard",
            description = "Calls customer summary, payment summary, and customer risk analytics procedures in sequence and returns all results in one response."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Complete customer dashboard generated successfully"),
            @ApiResponse(responseCode = "500", description = "Failed to generate complete customer dashboard")
    })
    public CustomerCompleteDashboardDTO getCompleteDashboard(
            @Parameter(description = "City to filter customers by", example = "Mumbai")
            @RequestParam String city,
            @Parameter(description = "Minimum transaction amount", example = "500")
            @RequestParam double minAmount,
            @Parameter(description = "Transaction status to filter by", example = "SUCCESS")
            @RequestParam String status,
            @Parameter(description = "Start timestamp for transaction filtering in ISO format", example = "2026-04-01T00:00:00")
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Risk level to filter by", example = "HIGH")
            @RequestParam String riskLevel) {
        return orchestrator.orchestrateCompleteDashboard(city, minAmount, status, startDate, riskLevel);
    }

}
