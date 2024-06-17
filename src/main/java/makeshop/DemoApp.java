package makeshop;

import java.sql.Connection;

public class DemoApp {
    public static void main(String[] args) {
        try {
            Connection conn = MyAccConnection.getInstance().getConnection();
            System.out.println("Connected to Makeshop");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
