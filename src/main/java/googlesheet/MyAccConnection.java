package googlesheet;

import account.AccConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Semaphore;

public class MyAccConnection implements AccConnection {
    private static MyAccConnection instance = null;
    private static  final Semaphore semaphore = new Semaphore(1);
    private Connection connection;

    private MyAccConnection() {
        try {
            Class.forName("cdata.jdbc.googlesheets.GoogleSheetsDriver");

            Properties prop = new Properties();
            prop.setProperty("InitiateOAuth","REFRESH");
//            prop.setProperty("Spreadsheet","value");
            prop.setProperty("OAuthClientId","value");
            prop.setProperty("OAuthClientSecret","value");
            prop.setProperty("OAuthAccessToken","value");
            prop.setProperty("OAuthRefreshToken","value");

            // for logging
            prop.setProperty("Logfile","value");
            prop.setProperty("Verbosity","value");
            prop.setProperty("MaxLogFileSize","value");

            // retry and waiting time
//            prop.setProperty("Other", "RetryWaitTime=1;MaximumRequestRetries=1");

            this.connection = DriverManager.getConnection("jdbc:googlesheets:",prop);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static MyAccConnection getInstance() {
        try {
            semaphore.acquire();
            if (instance == null) {
                instance = new MyAccConnection();
            }
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return instance;
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }
}
