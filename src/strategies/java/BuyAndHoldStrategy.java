import java.sql.SQLException;

public class BuyAndHoldStrategy extends Strategy {

    double boughtAt = Double.MAX_VALUE;
    double sellAt = Double.MAX_VALUE;

    @Override
    public void analyze(double currentPrice) throws SQLException {

        if (this.getCapital() > (currentPrice + 0.99)) {
            boughtAt = currentPrice;
            int count = 0;
            while ((count * currentPrice) + CoinbaseFees.determineCryptocurrencyFee((count * currentPrice)) < this.getCapital()) {
                count ++;
            }
            count--;
            this.AddCapital(-1*count*currentPrice);
            this.AddCapital(-1* CoinbaseFees.determineCryptocurrencyFee((count * currentPrice)));
            this.setUnderlying(this.getUnderlying() + count);
            System.out.println("BOUGHT " + count + "x at " + boughtAt + " each");
        }

    }
    @Override
    public void buy(double currentPrice) {

    }
    @Override
    public void sell(double currentPrice) {
    }
}
