import org.json.JSONObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLCryptocurrenciesRepository {

    static String RIPPLE_DATABASE_NAME = "RippleTimeSeries";
    static String INSERT_RIPPLE_TIME_SERIES_KEYS = "INSERT INTO RippleTimeSeries (CurrencyCode, MarketCode, Day, Open, High, Low, Close, Volume, MarketCap)";
    static String BITCOIN_DATABASE_NAME = "BitcoinTimeSeries";
    static String INSERT_BITCOIN_TIME_SERIES_KEYS = "INSERT INTO BitcoinTimeSeries (CurrencyCode, MarketCode, Day, Open, High, Low, Close, Volume, MarketCap)";
    static String ETHEREUM_DATABASE_NAME = "EthereumTimeSeries";
    static String INSERT_ETHEREUM_TIME_SERIES_KEYS = "INSERT INTO EthereumTimeSeries (CurrencyCode, MarketCode, Day, Open, High, Low, Close, Volume, MarketCap)";
    static String LITECOIN_DATABASE_NAME = "LitecoinTimeSeries";
    static String INSERT_LITECOIN_TIME_SERIES_KEYS = "INSERT INTO LitecoinTimeSeries (CurrencyCode, MarketCode, Day, Open, High, Low, Close, Volume, MarketCap)";
    static String BITCOIN_CASH_DATABASE_NAME = "BitcoinCashTimeSeries";
    static String INSERT_BITCOIN_CASH_TIME_SERIES_KEYS = "INSERT INTO BitcoinCashTimeSeries (CurrencyCode, MarketCode, Day, Open, High, Low, Close, Volume, MarketCap)";


    /**
     * fillSQLServer populates database with n data values from getDigitalCurrency Methods
     * @param json return of getDigitalCurrency Methods
     * @param n the amount of days to fill (recent first)
     * @param databaseTimeSeriesKeys represents insert statement excluding values
     * @throws SQLException
     */
    public static void fillSQLServer(JSONObject json, int n, String databaseTimeSeriesKeys) throws SQLException {
        CryptoDateData cryptoDateData = new CryptoDateData();
        int numberOfDays = n;

        String[] metaData = CryptocurrenciesDataOrganizer.organizeMetaData(json);
        for (String md : metaData) {
            if (md.contains("Digital Currency Code")) {
                cryptoDateData.setCurrencyCode(md.substring(md.indexOf(':') + 1));
            }
            if (md.contains("Market Code")) {
                cryptoDateData.setMarketCode(md.substring(md.indexOf(':') + 1));
                break;
            }
        }
        JSONObject timeSeries = Cryptocurrencies.getDigitalCurrencyTimeSeries(json);
        ArrayList<String> days = CryptocurrenciesDataOrganizer.organizeTimeSeriesData(timeSeries);
        for (String day : days) {

            cryptoDateData.setDate(day);
            String[] splitTimeSeries = CryptocurrenciesDataOrganizer.organizeTimeSeriesLineData(timeSeries, day);

            for (String splitTimeSeriesLine : splitTimeSeries) {
                double colonIndex = Double.parseDouble(splitTimeSeriesLine.substring(splitTimeSeriesLine.indexOf(':') + 1));
                if (splitTimeSeriesLine.contains("1a. open")) {
                    cryptoDateData.setOpen(colonIndex);
                } else if (splitTimeSeriesLine.contains("2a. high")) {
                    cryptoDateData.setHigh(colonIndex);
                } else if (splitTimeSeriesLine.contains("3a. low")) {
                    cryptoDateData.setLow(colonIndex);
                } else if (splitTimeSeriesLine.contains("4a. close")) {
                    cryptoDateData.setClose(colonIndex);
                } else if (splitTimeSeriesLine.contains("5. volume")) {
                    cryptoDateData.setVolume(colonIndex);
                } else if (splitTimeSeriesLine.contains("6. market cap")) {
                    cryptoDateData.setMarketCap(colonIndex);
                }
            }
            insertDateData(cryptoDateData, databaseTimeSeriesKeys);
            n -= 1;
            if (n == 0) {
                break;
            }
        }
        numberOfDays = (n == 0) ? numberOfDays : n * -1;
        System.out.println("Filled " + numberOfDays + " database entries.");
    }

    /**
     * fillSQLServer populates entire database with data from getDigitalCurrency Methods
     * @param json return of getDigitalCurrency Methods
     * @param databaseTimeSeriesKeys representation of insert statement excluding values
     * @throws SQLException
     */
    public static void fillSQLServer(JSONObject json, String databaseTimeSeriesKeys) throws SQLException {
        fillSQLServer(json, -1, databaseTimeSeriesKeys);
    }

    /**
     * getMostRecentDateData is used to retrieve most recent getDigitalCurrency data from database in the form of CrptoDateData
     * @param n Amount of recent dates to retrieve
     * @param databaseName name of database containing getDigitalCurrency data
     * @return ArrayList of most recent getDigitalCurrency data in the form of CrptoDateData
     * @throws SQLException
     */
    public static ArrayList<CryptoDateData> getMostRecentDateData(int n, String databaseName) throws SQLException {
        String sqlQuery = String.format("select * from %s\n" +
                "order by Day desc\n" +
                "limit %d;", databaseName, n);
        return SQLDigitalConnector.executeSelectSQLCommand(sqlQuery);
    }

    /**
     * getMostRecentDateDataFromPastDate is used to retrieve most recent getDigitalCurrency data from database in the form of CrptoDateData
     * from a past date
     * Useful for strategies that look at past data
     * @param n Amount of recent dates to retrieve
     * @param databaseName name of database containing getDigitalCurrency data
     * @param date represents past date
     * @return ArrayList of most recent getDigitalCurrency data given a day in the past in the form of CrptoDateData
     * @throws SQLException
     */
    public static ArrayList<CryptoDateData> getMostRecentDateDataFromPastDate(int n, String databaseName, String date) throws SQLException {
        String sqlQuery = String.format(
                "select * from \n" +
                "(select * \n" +
                "from %s as r\n" +
                "where CAST(r.Day AS Date) < '%s'\n" +
                "order by r.Day desc\n" +
                "limit %d) as s\n" +
                "order by Day asc;", databaseName, date, n);
        return SQLDigitalConnector.executeSelectSQLCommand(sqlQuery);
    }

    /**
     * insertDateData is used to insert getDigitalCurrency data in the form of CrptoDateData to a database
     * @param cryptoDateData getDigitalCurrency data to be inserted to sql table
     * @param databaseTimeSeriesKeys represents insert statement excluding values
     * @throws SQLException
     */
    public static void insertDateData(CryptoDateData cryptoDateData, String databaseTimeSeriesKeys) throws SQLException {
        String insertSQLCommand = String.format(databaseTimeSeriesKeys + "VALUES('%s','%s','%s',%f,%f,%f,%f,%f,%f);",
                cryptoDateData.getCurrencyCode(), cryptoDateData.getMarketCode(), cryptoDateData.getDate(), cryptoDateData.getOpen(),
                cryptoDateData.getHigh(), cryptoDateData.getLow(), cryptoDateData.getClose(), cryptoDateData.getVolume(), cryptoDateData.getMarketCap());
        SQLDigitalConnector.executeInsertSQLCommand(insertSQLCommand);
    }

    /**
     * getDateFromNDaysBack is used to retrieve getDigitalCurrency data from n days back
     * @param n the amount of days back
     * @param databaseName name of database containing getDigitalCurrency data
     * @return date from n days back in the form of a string
     * @throws SQLException
     */
    public static String getDateFromNDaysBack(int n, String databaseName) throws SQLException {
        ArrayList<CryptoDateData> cryptoDateDataArrayList = SQLCryptocurrenciesRepository.getMostRecentDateData(n-1, databaseName);
        return cryptoDateDataArrayList.get(cryptoDateDataArrayList.size() - 1).getDate();
    }

    /**
     * getDateFromNDaysBack is used to retrieve the getDigitalCurrency data from n days back starting from a past date
     * @param n the amount of days back
     * @param databaseName name of database containing getDigitalCurrency data
     * @param date represents past date
     * @return date from n days back starting from a past date in the form of a string
     * @throws SQLException
     */
    public static String getDateFromNDaysBackStartingFrom(int n, String databaseName, String date) throws SQLException {
        ArrayList<CryptoDateData> cryptoDateDataArrayList = SQLCryptocurrenciesRepository.getMostRecentDateDataFromPastDate(n-1, databaseName, date);
        return cryptoDateDataArrayList.get(0).getDate();
    }

    /**
     * getDataFromDate is used to retrieve a certain row from the getDigitalCurrency data found in a database
     * @param date date which getDigitalCurrency data is returns
     * @param databaseName name of database containing getDigitalCurrency data
     * @return getDigitalCurrency data given a date
     * @throws SQLException
     */
    public static CryptoDateData getDataFromDate(String date, String databaseName) throws SQLException {
        String sqlQuery = String.format("select * \n" +
                "from %s as r\n" +
                "where CAST(r.Day AS Date) = '%s';", databaseName, date);
        return SQLDigitalConnector.executeSelectSQLCommand(sqlQuery).get(0);
    }

    /**
     * getDataFromDate1toDate2 is used to retrieve a collection of getDigitalCurrency data found in a database
     * starting at date1 and ending date2
     * @param date1 first date of collection (Inclusive)
     * @param date2 final date of collection (Exclusive)
     * @param databaseName name of database containing getDigitalCurrency data
     * @return Arraylist of getDigitalCurrency data from date1 to date2 in the form of CryptoDateData
     * @throws SQLException
     */
    public static ArrayList<CryptoDateData> getDataFromDate1toDate2(String date1, String date2, String databaseName) throws SQLException {
        String sqlQuery = String.format("select * \n" +
                "from %s as r\n" +
                "where CAST(r.Day AS Date) >= '%s' and CAST(r.Day AS Date) < '%s'\n" +
                "order by Day asc;", databaseName, date1, date2);
        return SQLDigitalConnector.executeSelectSQLCommand(sqlQuery);
    }
}