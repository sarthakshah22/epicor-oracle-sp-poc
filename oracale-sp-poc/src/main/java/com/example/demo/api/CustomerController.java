package com.example.demo.api;

import com.example.demo.dto.CustomerSummaryDTO;
import com.example.demo.orchestrator.CustomerOrchestrator;
import com.example.demo.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Customer API", description = "Endpoints for customer order and payment summaries")
public class CustomerController {

    private final CustomerService service;

    private final CustomerOrchestrator orchestrator;

    public CustomerController(CustomerService service, CustomerOrchestrator orchestrator) {
        this.service = service;
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
            @RequestParam String status) throws Exception {

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
        return service.getSummary(city, minAmount);
    }


}
