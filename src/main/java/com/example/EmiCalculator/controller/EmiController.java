package com.example.EmiCalculator.controller;

import com.example.EmiCalculator.model.EmiRequest;
import com.example.EmiCalculator.model.EmiResponse;
import com.example.EmiCalculator.service.EmiService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/emi")
public class EmiController {
    @Autowired
    private EmiService emiService;

    @PostMapping("/calculate")
    @Operation(summary = "Calculate EMI", description = "Returns EMI and schedule after currency conversion")
    public EmiResponse calculateEmi(@RequestBody EmiRequest request) {
        return emiService.calculateEmi(request);
    }
}
