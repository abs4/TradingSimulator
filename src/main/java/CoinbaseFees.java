import com.sun.tools.javac.util.Pair;

public class CoinbaseFees {

    private static Pair<Double, Double> flatLimitFeeOne = new Pair<>(10.0, 0.99);
    private static Pair<Double, Double> flatLimitFeeTwo = new Pair<>(25.0, 1.49);
    private static Pair<Double, Double> flatLimitFeeThree = new Pair<>(50.0, 1.99);
    private static Pair<Double, Double> flatLimitFeeFour = new Pair<>(200.0, 2.99);
    private static double variableFee = 0.0149;

    /**
     * determineFee is used to find the fee associated with a cryptocurrency transaction (Based of Coinbase)
     * @param transactionAmount the amount of capital a party is putting up to trade
     * @return amount in fees
     */
    public static double determineCryptocurrencyFee(double transactionAmount) {
        if (transactionAmount <= flatLimitFeeOne.fst) {
            return flatLimitFeeOne.snd;
        } else if (transactionAmount <= flatLimitFeeTwo.fst) {
            return flatLimitFeeTwo.snd;
        } else if (transactionAmount <= flatLimitFeeThree.fst) {
            return flatLimitFeeThree.snd;
        } else if (transactionAmount <= flatLimitFeeFour.fst) {
            return flatLimitFeeFour.snd;
        }
        return transactionAmount * variableFee;
    }
}
