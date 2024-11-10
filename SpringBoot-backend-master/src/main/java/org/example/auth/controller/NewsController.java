package org.example.auth.controller;

import org.example.auth.entity.NewsArticle;
import org.example.auth.service.AlphaVantageNewsData;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private AlphaVantageNewsData alphaVantageNewsData;

    @GetMapping("/fetch")
    public String fetchAndSaveFinancialNews() {
        alphaVantageNewsData.fetchAndStoreNewsData();
        return "Financial news fetched and saved successfully";
    }

    @GetMapping("/financial")
    public List<NewsArticle> getFinancialNews() {
        return alphaVantageNewsData.getFinancialNews();
    }
}
