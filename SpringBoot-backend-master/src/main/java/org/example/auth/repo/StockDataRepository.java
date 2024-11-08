package org.example.auth.repo;

import org.example.auth.entity.StockData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface StockDataRepository extends JpaRepository<StockData, Long> {
    List<StockData> findByStockSymbolAndDateBetween(String stockSymbol, LocalDate startDate, LocalDate endDate);

}
