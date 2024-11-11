package org.example.auth.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YahooFinanceResponse {

    private Chart chart;

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Chart {
        private Result[] result;

        public Result[] getResult() {
            return result;
        }

        public void setResult(Result[] result) {
            this.result = result;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Result {
        private long[] timestamp;
        private Indicators indicators;

        public long[] getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long[] timestamp) {
            this.timestamp = timestamp;
        }

        public Indicators getIndicators() {
            return indicators;
        }

        public void setIndicators(Indicators indicators) {
            this.indicators = indicators;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Indicators {
        private Quote[] quote;

        public Quote[] getQuote() {
            return quote;
        }

        public void setQuote(Quote[] quote) {
            this.quote = quote;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Quote {
        private Double[] open;
        private Double[] high;
        private Double[] low;
        private Double[] close;
        private Long[] volume;

        public Double[] getOpen() {
            return open;
        }

        public void setOpen(Double[] open) {
            this.open = open;
        }

        public Double[] getHigh() {
            return high;
        }

        public void setHigh(Double[] high) {
            this.high = high;
        }

        public Double[] getLow() {
            return low;
        }

        public void setLow(Double[] low) {
            this.low = low;
        }

        public Double[] getClose() {
            return close;
        }

        public void setClose(Double[] close) {
            this.close = close;
        }

        public Long[] getVolume() {
            return volume;
        }

        public void setVolume(Long[] volume) {
            this.volume = volume;
        }
    }
}