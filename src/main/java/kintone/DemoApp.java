package kintone;

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
//            getAllTables(conn).forEach(System.out::println);

            printAllData(conn, "New App");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> getAllTables(Connection conn) throws SQLException {
        DatabaseMetaData table_meta = conn.getMetaData();
        ResultSet rs=table_meta.getTables(null, null, "%", null);
        List<String> tables = new ArrayList<>();
        while(rs.next()){
            tables.add(rs.getString("TABLE_NAME"));
        }
        return tables;
    }

    private static void printAllData(Connection conn, String table) throws SQLException {
        System.out.println("Table: " + table);
        PreparedStatement pstmt = conn.prepareStatement("SELECT   FROM `" + table + "`");
        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData rsmd = pstmt.getMetaData();
        while (rs.next()) {
            for(int i=1;i<=rsmd.getColumnCount();i++) {
                System.out.println(rsmd.getColumnName(i) + " : " + rs.getString(i));
            }
        }
    }
}
