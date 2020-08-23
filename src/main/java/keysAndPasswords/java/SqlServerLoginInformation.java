package keysAndPasswords.java;

public class SqlServerLoginInformation {

    private static String user = "root";
    private static String password = "password";
    private static String serverName = "localhost";
    private static int portNumber = 3306;
    private static String databaseName = "CryptocurrencyData";
    private static String serverTimezone = "UTC";

    public static String getUser() {return user;}
    public static String getPassword() {return password;}
    public static String getServerName() {return serverName;}
    public static int getPortNumber() {return portNumber;}
    public static String getDatabaseName() {return databaseName;}
    public static String getServerTimezone() {return serverTimezone;}
}
