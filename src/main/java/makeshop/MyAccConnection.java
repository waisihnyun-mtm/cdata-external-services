package makeshop;

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
            Class.forName("cdata.jdbc.gmomakeshop.GMOMakeShopDriver");

            Properties prop = new Properties();
            prop.setProperty("ShopId","value");
            prop.setProperty("OrdersAccessCode","value");
            prop.setProperty("ProductsAccessCode","value");
            prop.setProperty("MembersAccessCode","value");

            this.connection = DriverManager.getConnection("jdbc:gmomakeshop:", prop);
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
        return null;
    }
}
