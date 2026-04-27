package com.example.demo.api;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.example.demo.dto.CustomerSummaryDTO;
import com.example.demo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("/customer-summary")
    public List<CustomerSummaryDTO> getSummary(@RequestParam String city,
                                               @RequestParam double minAmount) throws Exception {
        return service.getSummary(city, minAmount);
    }
}
