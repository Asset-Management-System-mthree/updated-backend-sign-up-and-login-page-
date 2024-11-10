package org.example.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@RestController
public class NYSEDataController {

    private static final String NYSE_API_URL = "https://query1.finance.yahoo.com/v8/finance/chart/^NYA";
    private static final String NIFTY50_API_URL = "https://query1.finance.yahoo.com/v8/finance/chart/^NSEI";

    @GetMapping("/api/nyse")
    public ResponseEntity<?> getNYSEData() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(NYSE_API_URL, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching NYSE data: " + e.getMessage());
        }
    }

    @GetMapping("/api/nifty")
    public ResponseEntity<?> getNiftyData() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(NIFTY50_API_URL, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching Nifty data: " + e.getMessage());
        }
    }
}
