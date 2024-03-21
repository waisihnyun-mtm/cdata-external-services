package bigquery;

import account.AccConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Properties;
import java.util.concurrent.Semaphore;

public class MyAccConnection implements AccConnection {
    private static MyAccConnection instance = null;
    private static  final Semaphore semaphore = new Semaphore(1);
    private Connection connection;

    private MyAccConnection() {
        try {
            Class.forName("cdata.jdbc.googlebigquery.GoogleBigQueryDriver");

            Properties prop = new Properties();
            prop.setProperty("InitiateOAuth","REFRESH");
            prop.setProperty("ProjectId","value");
            prop.setProperty("DatasetId","value");
            prop.setProperty("OAuthClientId","value");
            prop.setProperty("OAuthClientSecret","value");
            prop.setProperty("OAuthAccessToken","value");
            prop.setProperty("OAuthRefreshToken","value");

            // for logging
//            prop.setProperty("Logfile","value");
//            prop.setProperty("Verbosity","3");
//            prop.setProperty("MaxLogFileSize","512MB");

//            prop.setProperty("Other", "RetryWaitTime=1;MaximumRequestRetries=1");
//            prop.setProperty("ConnectOnOpen", "true");

            this.connection = DriverManager.getConnection("jdbc:googlebigquery:",prop);
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
        return connection;
    }
}
