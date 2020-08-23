import keysAndPasswords.java.AlphaVantageApiKey;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class Cryptocurrencies {

    final static String apiKey = AlphaVantageApiKey.getApiKey();

    /**
     * getCurrentExchangeRate is used to retrieve the real-time exchange rate for any pair of cryptocurrency and/or physical currency
     * @param fromCurrency one side of the exchange rate, ex "XRP"
     * @param toCurrency other side of the exchange rate, ex "USD"
     * @return information containing the exchange rate of fromCurrency and toCurrency
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject getCurrentExchangeRate(String fromCurrency, String toCurrency) throws IOException, JSONException {

        return AlphaVantageConnector.GetUrlRequest("https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=" + fromCurrency
                + "&to_currency=" + toCurrency + "&apikey=" + apiKey);
    }

    /**
     * getCryptoRating is used to retrieve a Fundamental Crypto Asset Score (FCSA) of a cryptocurrency.
     * This score is determined from various factors such as Market Maturity and User Activity.
     * The scores ranges from 0-1000 with
     * Superb -> 900-1000
     * Attractive -> 750-899
     * Basic -> 650-749
     * Caution -> 500-649
     * Fragile -> Below 500
     * @param symbol
     * @return information related to the FCSA of a cryptocurrency
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject getCryptoRating(String symbol) throws IOException, JSONException {
        return AlphaVantageConnector.GetUrlRequest("https://www.alphavantage.co/query?function=CRYPTO_RATING&symbol=" + symbol + "&apikey=" + apiKey);
    }

    /**
     * getDigitalCurrencyDaily is used to retrieve past daily cryptocurrency data from Alpha Vantage Api
     * @param symbol cryptocurrency symbol, ex "XRP"
     * @param market exchange market, ex "USD"
     * @return information containing past daily cryptocurrency data as a JSONObject
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject getDigitalCurrencyDaily(String symbol, String market) throws IOException, JSONException {
        return AlphaVantageConnector.GetUrlRequest("https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_DAILY&symbol=" + symbol
                + "&market=" + market + "&apikey=" + apiKey);
    }

    /**
     * getDigitalCurrencyWeekly is used to retrieve past weekly cryptocurrency data from Alpha Vantage Api
     * @param symbol cryptocurrency symbol, ex "XRP"
     * @param market exchange market, ex "USD"
     * @return information containing past weekly cryptocurrency data as a JSONObject
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject getDigitalCurrencyWeekly(String symbol, String market) throws IOException, JSONException {
        return AlphaVantageConnector.GetUrlRequest("https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_WEEKLY&symbol=" + symbol
                + "&market=" + market + "&apikey=" + apiKey);
    }

    /**
     * getDigitalCurrencyMonthly is used to retrieve past monthly cryptocurrency data from Alpha Vantage Api
     * @param symbol cryptocurrency symbol, ex "XRP"
     * @param market exchange market, ex "USD"
     * @return information containing past monthlu cryptocurrency data as a JSONObject
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject getDigitalCurrencyMonthly(String symbol, String market) throws IOException, JSONException {
        return AlphaVantageConnector.GetUrlRequest("https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_MONTHLY&symbol=" + symbol
                + "&market=" + market + "&apikey=" + apiKey);
    }

    /**
     * getDigitalCurrencyMetaData is used to retrieve MetaData component from getDigitalCurrency Methods
     * @param json JSONObject return of getDigitalCurrency Methods
     * @return JSONObject representing Meta Data of getDigitalCurrency Methods
     */
    public static JSONObject getDigitalCurrencyMetaData(JSONObject json) {
        return (JSONObject) json.get("Meta Data");
    }

    /**
     * getDigitalCurrencyMetaData is used to retrieve Time Series component from getDigitalCurrency Methods
     * @param json JSONObject return of getDigitalCurrency Methods
     * @return JSONObject representing Time Series of getDigitalCurrency Methods
     */
    public static JSONObject getDigitalCurrencyTimeSeries(JSONObject json) {
        return (JSONObject) json.get("Time Series (Digital Currency Daily)");
    }
}
