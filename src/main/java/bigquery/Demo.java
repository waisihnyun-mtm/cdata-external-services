package bigquery;

import account.AccConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        try {
            AccConnection accConnection = MyAccConnection.getInstance();
            Connection conn = accConnection.getConnection();
            System.out.println("Connected");

//            getTables(conn).forEach(System.out::println);
//            getColumns(conn).forEach(System.out::println);
            printData(conn, "test1");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static List<String> getColumns(Connection conn) throws Exception {
        DatabaseMetaData table_meta = conn.getMetaData();
        ResultSet rs = table_meta.getColumns("publicdata","samples","github_nested", null);
        List<String> columns = new ArrayList<>();
        while(rs.next()){
                columns.add(rs.getString("COLUMN_NAME"));
        }
        return columns;
    }

    public static void printData(Connection conn, String table) throws SQLException {
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