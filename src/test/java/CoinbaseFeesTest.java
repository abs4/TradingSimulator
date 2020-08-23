import static org.junit.Assert.*;

import org.junit.Test;

import java.text.ParseException;

public class CoinbaseFeesTest {

    @Test
    public void coinbaseFeeFlatTest1() throws ParseException {
        double fee = CoinbaseFees.determineCryptocurrencyFee(9.50);
        assertEquals(.99, fee, 0.001);
    }

    @Test
    public void coinbaseFeeFlatTest2() throws ParseException {
        double fee = CoinbaseFees.determineCryptocurrencyFee(19.50);
        assertEquals(1.49, fee, 0.01);
    }

    @Test
    public void coinbaseFeeFlatTest3() throws ParseException {
        double fee = CoinbaseFees.determineCryptocurrencyFee(49.50);
        assertEquals(1.99, fee, 0.01);
    }

    @Test
    public void coinbaseFeeFlatTest4() throws ParseException {
        double fee = CoinbaseFees.determineCryptocurrencyFee(199.50);
        assertEquals(2.99, fee, 0.01);
    }

    @Test
    public void coinbaseFeeVariableTest() throws ParseException {
        double fee = CoinbaseFees.determineCryptocurrencyFee(1225.30);
        assertEquals(18.25, fee, 0.01);
    }
}