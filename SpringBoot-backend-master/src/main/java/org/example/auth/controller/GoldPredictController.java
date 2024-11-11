package org.example.auth.controller;



import org.example.auth.service.GoldService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoldPredictController {

    private final GoldService goldService;

    public GoldPredictController(GoldService goldService) {
        this.goldService = goldService;
    }

    @GetMapping("/gold-prediction")
    public String getGoldPrediction() {
        // Call GoldService to get the predicted price
        return goldService.getPredictedGoldPrice();
    }
}

