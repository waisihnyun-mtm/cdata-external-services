package salesforce;

import account.AccConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SalesforceDemo {
    public static void main(String[] args) {
        try {
            AccConnection accConnection = MyAccConnection.getInstance();
            Connection conn = accConnection.getConnection();
            System.out.println("DB connected");

            printAllData(conn);
//            printAllDataIn10Threads(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printAllDataIn10Threads(Connection conn) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                try {
                    printAllData(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();
    }

    private static void printAllData(Connection conn) throws SQLException {
        List<String> tables = getTables(conn);
        tables.forEach(table -> {
            try {
                System.out.println("Table Name: " + table);
                getData(conn, table);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static List<String> getTables(Connection conn) throws SQLException {
        DatabaseMetaData table_meta = conn.getMetaData();
        ResultSet rs=table_meta.getTables(null, null, "%", null);
        List<String> tables = new ArrayList<>();
        while(rs.next()){
            tables.add(rs.getString("TABLE_NAME"));
        }
        return tables;
    }

    public static List<String> getColumns(Connection conn, String table) throws SQLException {
        DatabaseMetaData table_meta = conn.getMetaData();
        ResultSet rs = table_meta.getColumns(null,null, table, null);
        List<String> columns = new ArrayList<>();
        while(rs.next()){
            columns.add(rs.getString("COLUMN_NAME"));
        }
        return columns;
    }

    public static void getData(Connection conn, String table) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + table);
        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData rsmd = pstmt.getMetaData();
        while (rs.next()) {
            for(int i=1;i<=rsmd.getColumnCount();i++) {
                System.out.println(rsmd.getColumnName(i) + " : " + rs.getString(i));
            }
        }
    }
}
