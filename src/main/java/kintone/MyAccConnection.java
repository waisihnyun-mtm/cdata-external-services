package kintone;

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
            Class.forName("cdata.jdbc.kintone.KintoneDriver");

            Properties prop = new Properties();
            prop.setProperty("User","value");
            prop.setProperty("Password","value");
            prop.setProperty("Url","value");
//            prop.setProperty("GuestSpaceId","1");

            prop.setProperty("Logfile","value");
            prop.setProperty("Verbosity","value");
            prop.setProperty("MaxLogFileSize","value");

            this.connection = DriverManager.getConnection("jdbc:kintone:",prop);
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
