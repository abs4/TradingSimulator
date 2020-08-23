import java.sql.SQLException;
import java.util.ArrayList;


public class UsefulCryptoMethods {

    /**
     * PrintCryptoDateData is used to print out CryptoDateData from an ArrayList in order
     * @param cryptoDateDataArrayList ArrayList in which CryptoDateData will be printed
     */
    public static void printCryptoDateData(ArrayList<CryptoDateData> cryptoDateDataArrayList){
        for (CryptoDateData cryptoDateData : cryptoDateDataArrayList) {
            System.out.println(cryptoDateData.toString());
        }
    }

    /**
     * calculateSma is used to run a simple moving average given a time horizon n
     * @param n time horizon of simple moving average
     * @param dataBaseName name of database containing getDigitalCurrency data
     * @return the resulting average from n data points (Using close data)
     * @throws SQLException
     */
    public static double calculateSma(int n, String dataBaseName) throws SQLException {
        double sum = 0;
        ArrayList<CryptoDateData> cryptoDateDataArrayList = SQLCryptocurrenciesRepository.getMostRecentDateData(n-1, dataBaseName);
        for (CryptoDateData cryptoDateData : cryptoDateDataArrayList) {
            sum += cryptoDateData.getClose();
        }
        return sum / cryptoDateDataArrayList.size();
    }

    /**
     * calculateSmaFromPastDate is used to run a simple moving average given a time horizon n from a past date
     * @param n time horizon of simple moving average
     * @param dataBaseName name of database containing getDigitalCurrency data
     * @param date past date
     * @return the resulting average from n data points (Using close data) from a past date
     * @throws SQLException
     */
    public static double calculateSmaFromPastDate(int n, String dataBaseName, String date) throws SQLException {
        double sum = 0;
        ArrayList<CryptoDateData> cryptoDateDataArrayList = SQLCryptocurrenciesRepository.getMostRecentDateDataFromPastDate(n, dataBaseName, date);
        for (CryptoDateData cryptoDateData : cryptoDateDataArrayList) {
            sum += cryptoDateData.getClose();
        }
        return sum / cryptoDateDataArrayList.size();
    }

    /**
     * calculatePeakAverageFromPastDate is used to determine the average ceiling price given a time horizon n from a past date
     * A point is considered a ceiling point if it is a peak (day before and day after closing prices are lower)
     * @param n time horizon of simple moving average
     * @param dataBaseName name of database containing getDigitalCurrency data
     * @param date past date
     * @return the resulting ceiling price average from n data points (Using close data) from a past date
     * @throws SQLException
     */
    public static double calculatePeakAverageFromPastDate(int n, String dataBaseName, String date) throws SQLException {
        double sum = 0;
        int count = 0;
        ArrayList<CryptoDateData> cryptoDateDataArrayList = SQLCryptocurrenciesRepository.getMostRecentDateDataFromPastDate(n, dataBaseName, date);
        int len = cryptoDateDataArrayList.size();
        for (int i = 1; i < len - 1; i++) {
            if (cryptoDateDataArrayList.get(i+1).getHigh() < cryptoDateDataArrayList.get(i).getHigh()
                    && cryptoDateDataArrayList.get(i-1).getHigh() < cryptoDateDataArrayList.get(i).getHigh()) {
                sum += cryptoDateDataArrayList.get(i).getHigh();
                count += 1;
            }
        }
        return sum / count;
    }

    /**
     * calculatePitAverageFromPastDate is used to determine the average floor price given a time horizon n from a past date
     * A point is considered a floor point if it is a pit (day before and day after closing prices are higher)
     * @param n time horizon of simple moving average
     * @param dataBaseName name of database containing getDigitalCurrency data
     * @param date past date
     * @return the resulting floor price average from n data points (Using close data) from a past date
     * @throws SQLException
     */
    public static double calculatePitAverageFromPastDate(int n, String dataBaseName, String date) throws SQLException {
        double sum = 0;
        int count = 0;
        ArrayList<CryptoDateData> cryptoDateDataArrayList = SQLCryptocurrenciesRepository.getMostRecentDateDataFromPastDate(n, dataBaseName, date);
        int len = cryptoDateDataArrayList.size();
        for (int i = 1; i < len - 1; i++) {

            if (cryptoDateDataArrayList.get(i - 1).getLow() > cryptoDateDataArrayList.get(i).getLow()
                    && cryptoDateDataArrayList.get(i + 1).getLow() > cryptoDateDataArrayList.get(i).getLow()) {
                sum += cryptoDateDataArrayList.get(i).getLow();
                count += 1;
            }
        }
        return sum / count;
    }
}
