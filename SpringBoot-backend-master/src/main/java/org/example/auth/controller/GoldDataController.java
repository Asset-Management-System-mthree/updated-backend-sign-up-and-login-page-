package org.example.auth.controller;

import org.example.auth.service.GoldDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gold")
public class GoldDataController {

    @Autowired
    private GoldDataService goldDataService;

    @GetMapping("/fetch")
    public String fetchAndStoreGoldData() {
        goldDataService.fetchAndStoreGoldData();
        return "Gold data fetched and stored successfully.";
    }
}

