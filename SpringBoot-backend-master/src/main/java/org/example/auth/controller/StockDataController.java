package org.example.auth.controller;

import org.example.auth.entity.StockData;
import org.example.auth.service.StockDAtaYahooFetch;
import org.example.auth.service.StockDataAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/stocks")
public class StockDataController {

    @Autowired
    private StockDAtaYahooFetch stockDAtaYahooFetch;

    @Autowired
    private StockDataAnalysisService stockDataAnalysisService;

    @PostMapping("/fetch")
    public void fetchStockData() {
        // List of top 100 NASDAQ stock symbols (You can replace with the actual symbols)
        List<String> stockSymbols = Arrays.asList(
                "AAPL", "MSFT", "GOOGL", "AMZN", "TSLA", "META", "BRK.B", "NVDA",
                "JPM", "JNJ", "V", "PG", "UNH", "HD", "MA", "DIS", "PYPL",
                "NFLX", "CMCSA", "INTC", "VZ", "PEP", "ADBE", "T", "CSCO",
                "XOM", "NKE", "MRK", "ABT", "CRM", "PFE", "TMO", "AVGO",
                "IBM", "WBA", "TXN", "AMGN", "MDLZ", "ISRG", "NOW", "HON",
                "QCOM", "SBUX", "LRCX", "GILD", "ATVI", "FISV", "CSX",
                "ADP", "BKNG", "SNPS", "KHC", "LNT", "DHR", "BIIB"
        );

        stockDAtaYahooFetch.fetchAndStoreStockData(stockSymbols);
    }

    @GetMapping("/{symbol}/percentage-changes")
    public ResponseEntity<Map<String, BigDecimal>> getPercentageChanges(
            @PathVariable String symbol) {
        Map<String, BigDecimal> percentageChanges = stockDataAnalysisService.getPercentageIncrease(symbol);
        return ResponseEntity.ok(percentageChanges);
    }


    @PostMapping("/latest-price")
    public ResponseEntity<Map<String, Object>> getLatestStockPrice(@RequestBody Map<String, String> request) {
        String symbol = request.get("symbol");
        Optional<StockData> latestStockData = stockDAtaYahooFetch.getLatestStockData(symbol);

        if (latestStockData.isPresent()) {
            StockData stockData = latestStockData.get();
            Map<String, Object> response = Map.of(
                    "symbol", stockData.getStockSymbol(),
                    "date", stockData.getDate(),
                    "latestPrice", stockData.getClose()
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Stock data not found for symbol: " + symbol));
        }
    }



}
