package sansan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemoApp {
    public static void main(String[] args) {
        try {
            Connection conn = MyAccConnection.getInstance().getConnection();
            System.out.println("Connected");

            printAllData(conn, "Bizcards");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> getAllTables(Connection conn) throws SQLException {
        DatabaseMetaData table_meta = conn.getMetaData();
        ResultSet rs = table_meta.getTables(null, null, "%", null);
        List<String> tables = new ArrayList<>();
        while(rs.next()){
            tables.add(rs.getString("TABLE_NAME"));
        }
        return tables;
    }

    private static void printAllData(Connection conn, String table) throws SQLException {
        int count = 0;
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + table);
        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData rsmd = pstmt.getMetaData();
        while (rs.next()) {
            for(int i=1;i<=rsmd.getColumnCount();i++) {
                System.out.println(rsmd.getColumnName(i) + " : " + rs.getString(i));
            }
            count++;
        }
        System.out.println("Total data at " + table + ": " + count);
        System.out.println("===========================");
    }

    private static void insert100BizcardsData(Connection conn) throws Exception {
        String ownerId = "value";
        String tagId = "value";

        for (int i = 1; i <= 100; i++) {
            String query = "INSERT INTO Bizcards (Email, OwnerId, CompanyName, TagId) VALUES(?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            String mail = "testuser" + i + "@gmail.com";
            String comname = "TestCompany" + i;
            pstmt.setString(1, mail);
            pstmt.setString(2, ownerId);
            pstmt.setString(3, comname);
            pstmt.setString(4, tagId);
            int count = pstmt.executeUpdate();
        }
    }
}


