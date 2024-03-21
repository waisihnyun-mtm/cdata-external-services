package shopify;

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
            Class.forName("cdata.jdbc.shopify.ShopifyDriver");

            Properties prop = new Properties();
            prop.setProperty("InitiateOAuth","REFRESH");
            prop.setProperty("ShopUrl","https://yourshopname.myshopify.com");
            prop.setProperty("OauthClientId","myoauthclientid");
            prop.setProperty("OAuthClientSecret","myoauthclientsecret");

            // for logging
            prop.setProperty("Logfile","value");
            prop.setProperty("Verbosity","value");
            prop.setProperty("MaxLogFileSize","value");

            this.connection = DriverManager.getConnection("jdbc:shopify:",prop);
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
