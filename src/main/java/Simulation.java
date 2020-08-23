import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class Simulation {
    private Date startDate;
    private Date endDate;
    private Strategy strategy;
    private double amountOfCapital;
    private String dataBaseName;

    /**
     * Simulation is used to test strategies by backtesting old cryptocurrency data
     * @param startDate Date which backtesting begins
     * @param endDate Date which backtesting ends
     * @param strategy Strategy used during the simulation
     * @param amountOfCapital Amount of capital used by the strategy in the simulation
     * @param dataBaseName Name of database holding cryptocurrency data
     */
    public Simulation(Date startDate, Date endDate, Strategy strategy, double amountOfCapital, String dataBaseName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.strategy = strategy;
        this.amountOfCapital = amountOfCapital;
        this.strategy.AddCapital(amountOfCapital);
        this.dataBaseName = dataBaseName;
        this.strategy.setDataBaseName(dataBaseName);
    }

    /**
     * simulate runs the simulation
     * The way this is done is by taking 3 different prices for each day/week/month (open, (high+low)/2, close)
     * and allowing for the strategy to analyze these prices and then decide whether to buy/sell or wait
     * Note: On the final day of backtesting, all cryptocurrency is liquidated to capital
     * @return the amount of capital strategy ends with
     * @throws ParseException
     * @throws SQLException
     */
    public double simulate() throws ParseException, SQLException {

        ArrayList<CryptoDateData> cryptoDateDataArrayList = SQLCryptocurrenciesRepository.getDataFromDate1toDate2(DateUtil.DateToString(startDate), DateUtil.DateToString(endDate), this.dataBaseName);

        int index = 0;
        double finalPrice = 0;
        while (startDate.before(endDate)) {

            strategy.setCurrentDate(DateUtil.DateToString(startDate));
            double open = cryptoDateDataArrayList.get(index).getOpen();
            strategy.analyze(open);
            double middle = (cryptoDateDataArrayList.get(index).getLow() + cryptoDateDataArrayList.get(index).getHigh()) / 2;
            strategy.analyze(middle);
            double close = cryptoDateDataArrayList.get(index).getClose();
            strategy.analyze(close);
            finalPrice = close;

            index += 1;
            startDate = DateUtil.addDays(startDate,1);
        }

        if (strategy.getUnderlying() > 0) {
            strategy.AddCapital(finalPrice * strategy.getUnderlying());
        }
        strategy.setUnderlying(0);

        return strategy.getCapital();
    }
}
