package com.example.eccomerce.service;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyService {
    // ⭐️ CONCEPT: Collections (Using a Map for efficient lookup)
    private final Map<String, Double> rates = new HashMap<>();

    public CurrencyService() {
        // Initialize with default rates (simulating data from an external API call)
        rates.put("EUR", 0.92);
        rates.put("GBP", 0.79);
        rates.put("INR", 83.50);
        System.out.println("CurrencyService initialized with default rates.");
    }

    // ⭐️ CONCEPT: Third-Party API Call (Simulated)
    public double getRate(String currencyCode) {
        // In a real application, you would use RestTemplate or WebClient here
        // to call an API like OpenExchangeRates or Fixer.
        System.out.println("INFO: Simulating API call for rate: " + currencyCode);
        return rates.getOrDefault(currencyCode, 1.0); // Default to 1.0 (USD)
    }

    // Admin function to update the rate (requires ADMIN role via @PreAuthorize in controller)
    public void updateRate(String currencyCode, double rate) {
        rates.put(currencyCode.toUpperCase(), rate);
        System.out.println("Rate updated for " + currencyCode);
    }
}