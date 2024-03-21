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
}
