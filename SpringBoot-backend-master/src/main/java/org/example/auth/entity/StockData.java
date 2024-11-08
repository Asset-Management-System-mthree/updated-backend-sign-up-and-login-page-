package org.example.auth.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(
        name = "stock_data",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"stock_symbol", "date"})}
)
public class StockData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_symbol", nullable = false)
    private String stockSymbol;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "open_price")
    private BigDecimal open;

    @Column(name = "high_price")
    private BigDecimal high;

    @Column(name = "low_price")
    private BigDecimal low;

    @Column(name = "close_price")
    private BigDecimal close;

    @Column(name = "volume")
    private Long volume;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }
}
