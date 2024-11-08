package org.example.auth.controller;

import org.example.auth.service.StockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class StockController {

    private final StockService stockService;

    // Autowire the StockService
    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // Endpoint to get predicted stock price
    @GetMapping("/stock/predict")
    public String getPredictedStockPrice(@RequestParam String ticker) {
        // Call the Flask API through the StockService
        String predictedPrice = stockService.getPredictedStockPrice(ticker);

        return predictedPrice;
    }
}

