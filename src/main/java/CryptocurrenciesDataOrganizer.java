import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CryptocurrenciesDataOrganizer {

    /**
     * organizeMetaData is a helper function that parses and arranges Meta Data of getDigitalCurrency Methods
     * @param json JSONObject return of getDigitalCurrency Methods
     * @return Array of parsed Strings representing in order Meta Data
     */
    public static String[] organizeMetaData(JSONObject json) {
        String metaData = Cryptocurrencies.getDigitalCurrencyMetaData(json).toString();
        metaData = metaData.replaceAll("\"", "");
        metaData = metaData.replaceAll("[{]", "");
        metaData = metaData.replaceAll("}", "");
        String[] splitMetaData = metaData.split(",");
        Arrays.sort(splitMetaData);
        return splitMetaData;
    }

    /**
     * organizeTimeSeriesData is a helper function that parses and arranges Time Series of getDigitalCurrency Methods
     * @param json JSONObject return of getDigitalCurrency Methods
     * @return ArrayList of parsed Strings representing in order Time Series
     */
    public static ArrayList<String> organizeTimeSeriesData(JSONObject json) {
        ArrayList<String> days = new ArrayList<String>(json.keySet());
        Collections.sort(days);
        return days;
    }

    /**
     * organizeTimeSeriesLineData is a helper function that parses and arranges each Day/Week/Month of getDigitalCurrency Methods
     * @param timeSeries In order (daily, weekly, monthly) Time Series [Use value returned from organizeTimeSeriesData method]
     * @param day the specific date that should be parsed further
     * @return Array of parsed Strings representing in order Day/Week/Month data
     */
    public static String[] organizeTimeSeriesLineData(JSONObject timeSeries, String day) {
        String timeSeriesString = timeSeries.get(day).toString();
        timeSeriesString = timeSeriesString.replaceAll("\"", "");
        timeSeriesString = timeSeriesString.replaceAll("[{]", "");
        timeSeriesString = timeSeriesString.replaceAll("}", "");
        return timeSeriesString.split(",");
    }
}
