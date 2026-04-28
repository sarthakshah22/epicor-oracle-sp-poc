package com.example.demo.api;

import com.example.demo.orchestrator.CustomerOrchestrator;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.example.demo.dto.CustomerSummaryDTO;
import com.example.demo.service.CustomerService;

@RestController
@RequestMapping("/api")
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
    public Map<String, Object> dashboard(
            @RequestParam String city,
            @RequestParam double minAmount,
            @RequestParam String status) throws Exception {

        return orchestrator.orchestrate(city, minAmount, status);
    }


    //http://localhost:8080/api/customer-summary?city=Mumbai&minAmount=500
    //Directly calling SP1 without orchestration
    @GetMapping("/customer-summary")
    public List<CustomerSummaryDTO> getSummary(@RequestParam String city,
                                               @RequestParam double minAmount) throws Exception {
        return service.getSummary(city, minAmount);
    }


}
