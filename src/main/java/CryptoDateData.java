public class CryptoDateData {

    private String currencyCode;
    private String marketCode;
    private String date;
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;
    private double marketCap;

    public CryptoDateData() {
        this.currencyCode = null;
        this.marketCode = null;
        this.date = null;
        this.open = Double.MAX_VALUE;
        this.high = Double.MAX_VALUE;
        this.low = Double.MAX_VALUE;
        this.close = Double.MAX_VALUE;
        this.volume = Double.MAX_VALUE;
        this.marketCap = Double.MAX_VALUE;
    }

    /**
     * CryptoDateData is used to represent the cryptocurrency data associated with getDigitalCurrency Methods
     * @param currencyCode cryptocurrency symbol, ex "XRP"
     * @param marketCode exchange market, ex "USD"
     * @param date time frame in which trades occured
     * @param open price cryptocurrency starts with that day
     * @param high highest price cryptocurrency hits in that day
     * @param low lowest price cryptocurrency hits in that day
     * @param close price cryptocurrency ends with that day
     * @param volume amount traded in last 24 hours
     * @param marketCap circulating supply of the cryptocurrency
     */
    public CryptoDateData(String currencyCode, String marketCode, String date, double open, double high, double low, double close, double volume, double marketCap) {
        this.currencyCode = currencyCode;
        this.marketCode = marketCode;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.marketCap = marketCap;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Date: " + this.getDate() + "\n");
        stringBuilder.append("Currency Code: " + this.getCurrencyCode() + " | ");
        stringBuilder.append("Market Code: " + this.getMarketCode() + "\n");
        stringBuilder.append("Open: " + this.getOpen() + " | ");
        stringBuilder.append("High: " + this.getHigh() + " | ");
        stringBuilder.append("Low: " + this.getLow() + " | ");
        stringBuilder.append("Close: " + this.getClose() + "\n");
        stringBuilder.append("Volume: " + this.getVolume() + " | ");
        stringBuilder.append("Market Cap: " + this.getMarketCap() + "\n");
        return stringBuilder.toString();
    }
}