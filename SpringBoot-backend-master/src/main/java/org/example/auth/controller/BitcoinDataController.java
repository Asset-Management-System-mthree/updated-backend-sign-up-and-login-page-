package org.example.auth.controller;

import org.example.auth.service.BitcoinDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BitcoinDataController {

    @Autowired
    private BitcoinDataService bitcoinDataService;

    @GetMapping("/api/fetch-bitcoin-data")
    public String fetchBitcoinData() {
        bitcoinDataService.fetchAndStoreBitcoinData();
        return "Current Bitcoin data fetched and saved successfully!";
    }
}

