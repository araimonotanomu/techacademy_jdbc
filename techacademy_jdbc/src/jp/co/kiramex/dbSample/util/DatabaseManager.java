package jp.co.kiramex.dbSample.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    // データベース接続と結果取得のための変数
    private static Connection con;
    
    // データベース接続を行うメソッド
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
    
    Class.forName("com.mysql.cj.jdbc.Driver");
    
    con = DriverManager.getConnection(
            "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true",
            "root",
            "Araimonotanomu"
            );
    
            return con;
    }
    
    // データベース接続を閉じるメソッド
    public static void close() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
            
}
