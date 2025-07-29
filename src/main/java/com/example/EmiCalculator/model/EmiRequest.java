package com.example.EmiCalculator.model;

import lombok.Data;

import java.util.Map;


public class EmiRequest {
    public double loanAmount;
    public String currency;
    public double annualInterestRate;
    public int tenureMonths;
    public Map<String, Object> prepayment;
    public Map<String, Object> stepUp;

}
