import java.sql.SQLException;

public class SemiContrarianStrategy extends Strategy {

    double boughtAt = Double.MAX_VALUE;
    double sellAt = Double.MAX_VALUE;
    double STOP_LOSS_PERCENT = 4;
    double stopLossLimit = 0;
    double LOW_AVERAGE_PERCENT_WIGGLE_ROOM = 3;
    double HIGH_AVERAGE_PERCENT_WIGGLE_ROOM = 3;

    @Override
    public void analyze(double currentPrice) throws SQLException {
            double highAverage = UsefulCryptoMethods.calculatePeakAverageFromPastDate(365, this.getDataBaseName(), this.getCurrentDate());
            double lowAverage = UsefulCryptoMethods.calculatePitAverageFromPastDate(365, this.getDataBaseName(), this.getCurrentDate());

            if (currentPrice < (lowAverage + lowAverage * LOW_AVERAGE_PERCENT_WIGGLE_ROOM / 100) && (this.getCapital() > (currentPrice + 0.99))) {
                boughtAt = currentPrice;
                buy(currentPrice);
                sellAt = highAverage - (highAverage * HIGH_AVERAGE_PERCENT_WIGGLE_ROOM / 100);
            }
            sell(currentPrice);
        }

    @Override
    public void buy(double currentPrice) {
        double count = 0;
        while ((count * currentPrice) + CoinbaseFees.determineCryptocurrencyFee((count * currentPrice)) < this.getCapital()) {
            count ++;
        }
        count--;
        this.AddCapital(-1*count*currentPrice);
        this.AddCapital(-1* CoinbaseFees.determineCryptocurrencyFee((count * currentPrice)));
        this.setUnderlying(this.getUnderlying() + count);
        stopLossLimit =  currentPrice - currentPrice * STOP_LOSS_PERCENT/100;
    }

    @Override
    public void sell(double currentPrice) {
        if (currentPrice > sellAt && this.getUnderlying() > 0) {
            this.AddCapital(this.getUnderlying() * currentPrice);
            this.setUnderlying(0);
        }
        if (currentPrice < stopLossLimit && this.getUnderlying() > 0) {
            this.AddCapital(this.getUnderlying() * currentPrice);
            this.setUnderlying(0);
        }
    }
}
