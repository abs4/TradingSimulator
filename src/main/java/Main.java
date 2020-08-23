import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class Main {

    // Example Use
    public static void main(String[] args) throws IOException, JSONException, SQLException, ParseException {

        // Call AlphaVantage API and store that data into connected sql database
        JSONObject json = Cryptocurrencies.getDigitalCurrencyDaily("XRP","USD");
        SQLCryptocurrenciesRepository.fillSQLServer(json, -1, SQLCryptocurrenciesRepository.INSERT_RIPPLE_TIME_SERIES_KEYS);

        // Choose dates, strategy, and Crypto to test data
        String startDate = "2020-07-01";
        String endDate = "2020-07-31";
        SemiContrarianStrategy semiContrarianStrategy = new SemiContrarianStrategy();
        String crypto = SQLCryptocurrenciesRepository.RIPPLE_DATABASE_NAME;

        // Run simulation for results
        Simulation s = new Simulation(DateUtil.StringToDate(startDate), DateUtil.StringToDate(endDate), semiContrarianStrategy, 10000, crypto);
        System.out.println(crypto + " - " + startDate + " -> " + endDate + " : " + s.simulate());

    }
}
