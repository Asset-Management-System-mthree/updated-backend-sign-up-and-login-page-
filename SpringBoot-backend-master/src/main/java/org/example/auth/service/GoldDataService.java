package org.example.auth.service;

import org.example.auth.entity.GoldData;
import org.example.auth.repo.GoldDataRepository;
import org.example.auth.response.YahooFinanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoldDataService {

    private static final String YAHOO_FINANCE_API_URL = "https://query1.finance.yahoo.com/v8/finance/chart/";

    @Autowired
    private GoldDataRepository goldDataRepository;

    public void fetchAndStoreGoldData() {
        String symbol = "GC=F"; // Yahoo Finance symbol for gold futures
        String url = YAHOO_FINANCE_API_URL + symbol + "?range=5y&interval=1d";

        RestTemplate restTemplate = new RestTemplate();
        YahooFinanceResponse response = restTemplate.getForObject(url, YahooFinanceResponse.class);

        // Log the response to see what you're getting
        System.out.println("Response: " + response);

        if (response != null && response.getChart() != null && response.getChart().getResult() != null) {
            List<GoldData> goldDataList = new ArrayList<>();

            for (int i = 0; i < response.getChart().getResult()[0].getTimestamp().length; i++) {
                Long timestamp = response.getChart().getResult()[0].getTimestamp()[i];

                // Convert timestamp to LocalDate with correct time zone
                LocalDate goldDate = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();

                // Check if the date is valid before inserting
                if (goldDate.getYear() >= 1970 && goldDate.getYear() <= 9999) {
                    GoldData goldData = new GoldData();
                    goldData.setSymbol(symbol);
                    goldData.setDate(goldDate);

                    // Safely retrieve values and handle possible nulls
                    BigDecimal open = response.getChart().getResult()[0].getIndicators().getQuote()[0].getOpen()[i] != null
                            ? BigDecimal.valueOf(response.getChart().getResult()[0].getIndicators().getQuote()[0].getOpen()[i])
                            : BigDecimal.ZERO;
                    BigDecimal high = response.getChart().getResult()[0].getIndicators().getQuote()[0].getHigh()[i] != null
                            ? BigDecimal.valueOf(response.getChart().getResult()[0].getIndicators().getQuote()[0].getHigh()[i])
                            : BigDecimal.ZERO;
                    BigDecimal low = response.getChart().getResult()[0].getIndicators().getQuote()[0].getLow()[i] != null
                            ? BigDecimal.valueOf(response.getChart().getResult()[0].getIndicators().getQuote()[0].getLow()[i])
                            : BigDecimal.ZERO;
                    BigDecimal close = response.getChart().getResult()[0].getIndicators().getQuote()[0].getClose()[i] != null
                            ? BigDecimal.valueOf(response.getChart().getResult()[0].getIndicators().getQuote()[0].getClose()[i])
                            : BigDecimal.ZERO;
                    Long volume = response.getChart().getResult()[0].getIndicators().getQuote()[0].getVolume()[i] != null
                            ? response.getChart().getResult()[0].getIndicators().getQuote()[0].getVolume()[i]
                            : 0L;

                    // Set fields on goldData
                    goldData.setOpen(open);
                    goldData.setHigh(high);
                    goldData.setLow(low);
                    goldData.setClose(close);
                    goldData.setVolume(volume);

                    goldDataList.add(goldData);
                } else {
                    System.out.println("Invalid date for symbol " + symbol + ": " + goldDate);
                }
            }

            // Save the gold data to the database in batch
            goldDataRepository.saveAll(goldDataList);
            System.out.println("Saved gold data: " + goldDataList.size() + " records.");
        } else {
            System.out.println("No data found for gold.");
        }
    }
}
