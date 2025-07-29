package com.example.EmiCalculator.model;


import jakarta.persistence.Entity;

import java.util.List;
import java.util.Map;

public class EmiResponse {
    private double originalEmi;
    private double totalPayment;
    private double totalInterest;
    private double prepaySavings;
    private List<Map<String, Object>> amortizationSchedule;

    public EmiResponse() {

    }

    public double getOriginalEmi() {
        return originalEmi;
    }

    public void setOriginalEmi(double originalEmi) {
        this.originalEmi = originalEmi;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public double getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(double totalInterest) {
        this.totalInterest = totalInterest;
    }

    public double getPrepaySavings() {
        return prepaySavings;
    }

    public void setPrepaySavings(double prepaySavings) {
        this.prepaySavings = prepaySavings;
    }

    public List<Map<String, Object>> getAmortizationSchedule() {
        return amortizationSchedule;
    }

    public void setAmortizationSchedule(List<Map<String, Object>> amortizationSchedule) {
        this.amortizationSchedule = amortizationSchedule;
    }

    public EmiResponse(double originalEmi, double totalPayment, double totalInterest, double prepaySavings, List<Map<String, Object>> amortizationSchedule) {
        this.originalEmi = originalEmi;
        this.totalPayment = totalPayment;
        this.totalInterest = totalInterest;
        this.prepaySavings = prepaySavings;
        this.amortizationSchedule = amortizationSchedule;
    }
}
