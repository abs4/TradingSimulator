import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Strategy {

    private double capital = 0;
    private double underlying = 0;
    private double stopLoss = 0;
    private ArrayList<Double> takeProfits = null;
    private ArrayList<Double> stopLosses = null;
    private String currentDate = "2000-01-01";
    private String dataBaseName = "";

    public double getStopLoss() {
        return stopLoss;
    }
    public void setStopLoss(double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public String getDataBaseName() {
        return dataBaseName;
    }
    public void setDataBaseName(String dataBaseName) {
        this.dataBaseName = dataBaseName;
    }

    public String getCurrentDate() {
        return currentDate;
    }
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public double getCapital() {
        return capital;
    }
    public void setCapital(double capital) {
        this.capital = capital;
    }
    public void AddCapital(double capital) {
        this.capital += capital;
    }

    public double getUnderlying() {
        return underlying;
    }
    public void setUnderlying(double underlying) {
        this.underlying = underlying;
    }

    public ArrayList<Double> getTakeProfits() {
        return takeProfits;
    }
    public void setTakeProfits(ArrayList<Double> takeProfits) {
        this.takeProfits = takeProfits;
    }

    public ArrayList<Double> getStopLosses() {
        return stopLosses;
    }
    public void setStopLosses(ArrayList<Double> stopLosses) {
        this.stopLosses = stopLosses;
    }

    /**
     * analyze is used to interpret current trading environment given a current price
     * @param currentPrice the price used to interpret
     * @throws SQLException
     */
    public abstract void analyze(double currentPrice) throws SQLException;

    /**
     * buy is where the amount of capital is exchanged for cryptocurrency
     * Typically called from analyze when opportunity presents itself
     * @param currentPrice the price at which party agrees to buy
     */
    public abstract void buy(double currentPrice);

    /**
     * sell is where the cryptocurrency is exchanged for amount of capital
     * @param currentPrice the price at which party agrees to sell off cryptocurrency
     */
    public abstract void sell(double currentPrice);
}
