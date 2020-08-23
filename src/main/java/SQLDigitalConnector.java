import keysAndPasswords.java.SqlServerLoginInformation;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLDigitalConnector {

    static Connection con;
    static Statement stmt;
    static MysqlDataSource dataSource;

    /**
     * openConnectionToSQLServer opens a connection to a sql server using information that allows access to that said sql server
     * @return connection used to interact with sql database
     */
    public static Connection openConnectionToSQLServer() {
        try {
            dataSource = new MysqlDataSource();
            dataSource.setUser(SqlServerLoginInformation.getUser());
            dataSource.setPassword(SqlServerLoginInformation.getPassword());
            dataSource.setServerName(SqlServerLoginInformation.getServerName());
            dataSource.setPortNumber(SqlServerLoginInformation.getPortNumber());
            dataSource.setDatabaseName(SqlServerLoginInformation.getDatabaseName());
            dataSource.setServerTimezone(SqlServerLoginInformation.getServerTimezone());
            con = dataSource.getConnection();
            return con;
        } catch (Exception e) {
            System.out.println("ERROR : OpenConnectionToSQLServer");
            return null;
        }
    }

    /**
     * closeConnectionToSQLServer is used to end sql server connection
     */
    public static void closeConnectionToSQLServer() {
        try {
            con.close();
        } catch (Exception e) {
            System.out.println("ERROR : CloseConnectionToSQLServer");
        }
    }

    /**
     * executeInsertSQLCommand is used to execute a sql insert command outside of connect sql server
     * @param sql represents sql command
     * @throws SQLException
     */
    public static void executeInsertSQLCommand(String sql) throws SQLException {
        try {
            Connection con = SQLDigitalConnector.openConnectionToSQLServer();
            SQLDigitalConnector.stmt = con.createStatement();
            SQLDigitalConnector.stmt.executeUpdate(sql);
            SQLDigitalConnector.closeConnectionToSQLServer();
        } catch (Exception e) {
            System.out.println("ERROR : ExecuteInsertSQLCommand");
        }
    }

    /**
     * executeSelectSQLCommand is used to execute a sql select command outside of connect sql server
     * @param sql represents sql command
     * @return Arraylist of getDigitalCurrency data in the form of CryptoDateData
     * @throws SQLException
     */
    public static ArrayList<CryptoDateData> executeSelectSQLCommand(String sql) throws SQLException {
        try {
            Connection con = SQLDigitalConnector.openConnectionToSQLServer();
            SQLDigitalConnector.stmt = con.createStatement();
            ResultSet resultSet = SQLDigitalConnector.stmt.executeQuery(sql);

            ArrayList<CryptoDateData> cryptoDateDataArrayList = new ArrayList<CryptoDateData>();

            while (resultSet.next()) {
                CryptoDateData cryptoDateData = new CryptoDateData(resultSet.getString("CurrencyCode"), resultSet.getString("MarketCode"),
                        resultSet.getString("Day"), resultSet.getDouble("Open"), resultSet.getDouble("High"), resultSet.getDouble("Low"),
                        resultSet.getDouble("Close"), resultSet.getDouble("Volume"), resultSet.getDouble("MarketCap"));
                cryptoDateDataArrayList.add(cryptoDateData);
            }
            SQLDigitalConnector.closeConnectionToSQLServer();
            return cryptoDateDataArrayList;
        } catch (Exception e) {
            System.out.println("ERROR : ExecuteSelectSQLCommand");
            return null;
        }
    }
}