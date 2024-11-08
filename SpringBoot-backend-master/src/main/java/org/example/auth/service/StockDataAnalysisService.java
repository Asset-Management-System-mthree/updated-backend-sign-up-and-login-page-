package org.example.auth.service;

import org.example.auth.entity.StockData;
import org.example.auth.repo.StockDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockDataAnalysisService {

    @Autowired
    private StockDataRepository stockDataRepository;

    public Map<String, BigDecimal> getPercentageIncrease(String stockSymbol) {
        Map<String, BigDecimal> percentageChanges = new HashMap<>();
        LocalDate now = LocalDate.now();

        // Define intervals in months and their labels
        Map<String, Long> intervals = Map.of(
                "1 month", 1L,
                "3 months", 3L,
                "6 months", 6L,
                "1 year", 12L,
                "2 years", 24L,
                "3 years", 36L,
                "5 years", 60L
        );

        // Calculate percentage change for each interval
        for (Map.Entry<String, Long> interval : intervals.entrySet()) {
            LocalDate startDate = now.minusMonths(interval.getValue());
            percentageChanges.put(interval.getKey(), calculatePercentageChange(stockSymbol, startDate, now));
        }

        return percentageChanges;
    }

    private BigDecimal calculatePercentageChange(String stockSymbol, LocalDate startDate, LocalDate endDate) {
        List<StockData> stockData = stockDataRepository.findByStockSymbolAndDateBetween(stockSymbol, startDate, endDate);

        if (stockData.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal startPrice = stockData.get(0).getClose();
        BigDecimal endPrice = stockData.get(stockData.size() - 1).getClose();

        return endPrice.subtract(startPrice)
                .divide(startPrice, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
}
