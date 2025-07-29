package com.example.EmiCalculator.entity;

import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
public class EmiCalculation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double originalEmi;
    private double totalPayment;
    private double totalInterest;
    private double prepaySavings;

    private double loanAmount;
    private String currency;
    private double convertedLoanAmount;

    private LocalDateTime timestamp;

    public EmiCalculation() {

    }



    @PrePersist
    public void onCreate() {
        timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getConvertedLoanAmount() {
        return convertedLoanAmount;
    }

    public void setConvertedLoanAmount(double convertedLoanAmount) {
        this.convertedLoanAmount = convertedLoanAmount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public EmiCalculation(Long id, double originalEmi, double totalPayment, double totalInterest, double prepaySavings, double loanAmount, String currency, double convertedLoanAmount, LocalDateTime timestamp) {
        this.id = id;
        this.originalEmi = originalEmi;
        this.totalPayment = totalPayment;
        this.totalInterest = totalInterest;
        this.prepaySavings = prepaySavings;
        this.loanAmount = loanAmount;
        this.currency = currency;
        this.convertedLoanAmount = convertedLoanAmount;
        this.timestamp = timestamp;
    }

}
