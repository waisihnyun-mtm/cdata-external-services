package googlesheet;

import account.AccConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GssDemo {
    public static void main(String[] args) {
        AccConnection accConnection = MyAccConnection.getInstance();
        Connection conn = accConnection.getConnection();
        System.out.println("DB connected");

        try {
            List<String> tables = getTableNames(conn);
            tables.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getTableNames(Connection conn) throws SQLException {
        DatabaseMetaData table_meta = conn.getMetaData();
        ResultSet rs=table_meta.getTables(null, null, "%", null);
        List<String> tables = new ArrayList<>();
        while(rs.next()){
            System.out.println(rs.getString("TABLE_NAME"));
            tables.add(rs.getString("TABLE_NAME"));
        }
        return tables;
    }

    private static List<String> getColumnNames(Connection conn, String table) throws SQLException {
        DatabaseMetaData table_meta = conn.getMetaData();
        ResultSet rs = table_meta.getColumns(null,null,table, null);
        List<String> columns = new ArrayList<>();
        while(rs.next()){
            columns.add(rs.getString("COLUMN_NAME"));
        }
        return columns;
    }

    private static void printAllData(Connection conn, String table) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM `" + table + "`");
        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData rsmd = pstmt.getMetaData();
        while (rs.next()) {
            for(int i=1;i<=rsmd.getColumnCount();i++) {
                System.out.println(rsmd.getColumnName(i) + " : " + rs.getString(i));
            }
        }
    }
}
