package com.example.EmiCalculator.service;

import com.example.EmiCalculator.entity.EmiCalculation;
import com.example.EmiCalculator.model.EmiRequest;
import com.example.EmiCalculator.model.EmiResponse;
import com.example.EmiCalculator.repository.EmiRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class EmiService {
    @Autowired
    private EmiRepository emiRepository;

    public EmiResponse calculateEmi(EmiRequest req) {
        // Step 1: Convert currency
        double originalAmount = req.loanAmount;
        if (req.currency != null && !req.currency.equalsIgnoreCase("INR")) {
            req.loanAmount = convertCurrency(req.currency, "INR", req.loanAmount);
        }

        // Step 2: EMI calculation
        double rate = req.annualInterestRate / (12 * 100);
        int tenure = req.tenureMonths;

        double emi = (req.loanAmount * rate * Math.pow(1 + rate, tenure)) /
                (Math.pow(1 + rate, tenure) - 1);

        double balance = req.loanAmount;
        double currentEmi = emi;
        double stepUpPercent = 0;
        if (req.stepUp != null && Boolean.TRUE.equals(req.stepUp.get("enabled"))) {
            stepUpPercent = Double.parseDouble(req.stepUp.get("percent_increase_per_year").toString());
        }

        double prepayAmount = 0;
        int prepayMonth = -1;
        if (req.prepayment != null) {
            prepayAmount = Double.parseDouble(req.prepayment.get("amount").toString());
            prepayMonth = Integer.parseInt(req.prepayment.get("month").toString());
        }

        List<Map<String, Object>> schedule = new ArrayList<>();
        double totalPaid = 0;

        for (int month = 1; month <= tenure && balance > 0; month++) {
            if (stepUpPercent > 0 && month % 12 == 1 && month != 1) {
                currentEmi += currentEmi * (stepUpPercent / 100);
            }

            double interest = balance * rate;
            double principal = currentEmi - interest;

            if (balance < currentEmi) {
                principal = balance;
                currentEmi = principal + interest;
            }

            balance -= principal;

            if (month == prepayMonth) {
                balance -= prepayAmount;
                if (balance < 0) balance = 0;
            }

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("month", month);
            row.put("emi", round(currentEmi));
            row.put("principal", round(principal));
            row.put("interest", round(interest));
            row.put("balance", round(balance));
            schedule.add(row);

            totalPaid += currentEmi;
        }

        double totalInterest = totalPaid - req.loanAmount;
        double originalInterest = (emi * tenure) - req.loanAmount;
        double savings = originalInterest - totalInterest;

        EmiResponse res = new EmiResponse();
        res.setOriginalEmi(round(emi));
        res.setTotalPayment(round(totalPaid));
        res.setTotalInterest(round(totalInterest));
        res.setPrepaySavings(round(savings));
        res.setAmortizationSchedule(schedule);


        // Save to DB
        EmiCalculation record = new EmiCalculation();
        record.setOriginalEmi(res.getOriginalEmi());
        record.setTotalPayment(res.getTotalPayment());
        record.setTotalInterest(res.getTotalInterest());
        record.setPrepaySavings(res.getPrepaySavings());
        record.setLoanAmount(originalAmount);
        record.setCurrency(req.currency);
        record.setConvertedLoanAmount(req.loanAmount);
        emiRepository.save(record);

        return res;
    }

    private double convertCurrency(String from, String to, double amount) {
        try {
            String url = String.format("https://api.exchangerate.host/convert?from=%s&to=%s&amount=%f", from, to, amount);
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setRequestMethod("GET");

            Scanner scanner = new Scanner(con.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            scanner.close();

            JsonNode json = new ObjectMapper().readTree(response);
            return json.get("result").asDouble();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Currency conversion failed");
        }
    }

    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }
}
