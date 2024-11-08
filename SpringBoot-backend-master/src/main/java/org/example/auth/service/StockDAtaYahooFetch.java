package org.example.auth.service;

import org.example.auth.entity.StockData;
import org.example.auth.repo.StockDataRepository;
import org.example.auth.response.YahooFinanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StockDAtaYahooFetch {

    @Autowired
    private StockDataRepository stockDataRepository;

    private static final String YAHOO_FINANCE_API_URL = "https://query1.finance.yahoo.com/v8/finance/chart/";

    // List of stock symbols to fetch
    private static final List<String> STOCK_SYMBOLS = List.of(
            "AAPL", "MSFT", "GOOGL", "AMZN", "TSLA", "META", "BRK.B", "NVDA",
            "JPM", "JNJ", "V", "PG", "UNH", "HD", "MA", "DIS", "PYPL",
            "NFLX", "CMCSA", "INTC", "VZ", "PEP", "ADBE", "T", "CSCO",
            "XOM", "NKE", "MRK", "ABT", "CRM", "PFE", "TMO", "AVGO",
            "IBM", "WBA", "TXN", "AMGN", "MDLZ", "ISRG", "NOW", "HON",
            "QCOM", "SBUX", "LRCX", "GILD", "ATVI", "FISV", "CSX",
            "ADP", "BKNG", "SNPS", "KHC", "LNT", "DHR", "BIIB"
    );

    @Transactional
//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "0 35 0 * * *")// Every day at midnight (00:00)
    public void scheduleDailyFetch() {
        fetchAndStoreStockData(STOCK_SYMBOLS);
    }

    @Transactional
    public void fetchAndStoreStockData(List<String> stockSymbols) {
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (String symbol : stockSymbols) {
            futures.add(CompletableFuture.runAsync(() -> fetchAndStoreDataForSymbol(symbol)));
        }

        // Wait for all async tasks to complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    private void fetchAndStoreDataForSymbol(String symbol) {
        // Fetch data from Yahoo Finance
        String url = YAHOO_FINANCE_API_URL + symbol + "?range=5y&interval=1d";
        RestTemplate restTemplate = new RestTemplate();
        YahooFinanceResponse response = restTemplate.getForObject(url, YahooFinanceResponse.class);

        if (response != null && response.getChart() != null && response.getChart().getResult() != null) {
            List<StockData> stockDataList = new ArrayList<>();

            for (int i = 0; i < response.getChart().getResult()[0].getTimestamp().length; i++) {
                Long timestamp = response.getChart().getResult()[0].getTimestamp()[i];

                // Convert timestamp to LocalDate
                LocalDate stockDate = LocalDate.ofEpochDay(timestamp / 86400); // Convert seconds to days

                // Check if the date is valid before inserting
                if (stockDate.getYear() >= 1970 && stockDate.getYear() <= 9999) {
                    StockData stockData = new StockData();
                    stockData.setStockSymbol(symbol);
                    stockData.setDate(stockDate);
                    stockData.setOpen(BigDecimal.valueOf(response.getChart().getResult()[0].getIndicators().getQuote()[0].getOpen()[i]));
                    stockData.setHigh(BigDecimal.valueOf(response.getChart().getResult()[0].getIndicators().getQuote()[0].getHigh()[i]));
                    stockData.setLow(BigDecimal.valueOf(response.getChart().getResult()[0].getIndicators().getQuote()[0].getLow()[i]));
                    stockData.setClose(BigDecimal.valueOf(response.getChart().getResult()[0].getIndicators().getQuote()[0].getClose()[i]));
                    stockData.setVolume(response.getChart().getResult()[0].getIndicators().getQuote()[0].getVolume()[i]);

                    stockDataList.add(stockData);
                } else {
                    // Log or handle the invalid date case
                    System.out.println("Invalid date for symbol " + symbol + ": " + stockDate);
                }
            }

            // Save the stock data to the database in batch
            stockDataRepository.saveAll(stockDataList);
        }
    }
}
