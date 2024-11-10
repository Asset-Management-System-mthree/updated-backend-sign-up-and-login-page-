package org.example.auth.service;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GoldService {

    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(GoldService.class);

    public GoldService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://127.0.0.1:5000").build();
    }

    public String getPredictedGoldPrice() {
        // Send GET request to Flask for gold price prediction
        logger.info("Sending request to Flask for gold price prediction");

        String response = this.webClient.get()
                .uri("/predict_gold")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        logger.info("Received response from Flask API: {}", response);
        return response;
    }
}
