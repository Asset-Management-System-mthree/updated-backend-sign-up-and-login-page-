package org.example.auth.service;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class StockService {

    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    public StockService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://127.0.0.1:5000").build();
    }

    public String getPredictedStockPrice(String ticker) {
        // Create a map for JSON payload
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("ticker", ticker);

        logger.info("Sending request to Flask with ticker: {}", ticker);

        String response = this.webClient.post()
                .uri("/predict")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)  // Properly serialized JSON
                .retrieve()
                .bodyToMono(String.class)
                .block();

        logger.info("Received response from Flask API: {}", response);
        return response;
    }
}

