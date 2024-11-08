package org.example.auth.service;

import org.example.auth.entity.BitcoinData;
import org.example.auth.repo.BitcoinDataRepository;
import org.example.auth.response.YahooFinanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BitcoinDataService {

    @Autowired
    private BitcoinDataRepository bitcoinDataRepository;

    private static final String YAHOO_FINANCE_URL = "https://query1.finance.yahoo.com/v8/finance/chart/BTC-USD?range=5y&interval=1d";

    public void fetchAndStoreBitcoinData() {
        // Define the symbol for Bitcoin
        String symbol = "BTC-USD"; // Yahoo Finance symbol for Bitcoin
        // Fetch data from Yahoo Finance
        String url = "https://query1.finance.yahoo.com/v8/finance/chart/BTC-USD?range=5y&interval=1d";
        RestTemplate restTemplate = new RestTemplate();
        YahooFinanceResponse response = restTemplate.getForObject(url, YahooFinanceResponse.class);

        if (response != null && response.getChart() != null && response.getChart().getResult() != null) {
            List<BitcoinData> bitcoinDataList = new ArrayList<>();

            for (int i = 0; i < response.getChart().getResult()[0].getTimestamp().length; i++) {
                Long timestamp = response.getChart().getResult()[0].getTimestamp()[i];

                // Convert timestamp to LocalDate
                LocalDate bitcoinDate = LocalDate.ofEpochDay(timestamp / 86400); // Convert seconds to days

                // Check if the date is valid before inserting
                if (bitcoinDate.getYear() >= 1970 && bitcoinDate.getYear() <= 9999) {
                    BitcoinData bitcoinData = new BitcoinData();
                    bitcoinData.setSymbol(symbol);
                    bitcoinData.setDate(bitcoinDate);
                    bitcoinData.setOpen(BigDecimal.valueOf(response.getChart().getResult()[0].getIndicators().getQuote()[0].getOpen()[i]));
                    bitcoinData.setHigh(BigDecimal.valueOf(response.getChart().getResult()[0].getIndicators().getQuote()[0].getHigh()[i]));
                    bitcoinData.setLow(BigDecimal.valueOf(response.getChart().getResult()[0].getIndicators().getQuote()[0].getLow()[i]));
                    bitcoinData.setClose(BigDecimal.valueOf(response.getChart().getResult()[0].getIndicators().getQuote()[0].getClose()[i]));
                    bitcoinData.setVolume(response.getChart().getResult()[0].getIndicators().getQuote()[0].getVolume()[i]);

                    bitcoinDataList.add(bitcoinData);
                } else {
                    // Log or handle the invalid date case
                    System.out.println("Invalid date for symbol " + symbol + ": " + bitcoinDate);
                }
            }

            // Save the Bitcoin data to the database in batch
            bitcoinDataRepository.saveAll(bitcoinDataList);
        } else {
            System.out.println("No data found for Bitcoin.");
        }
    }


}

