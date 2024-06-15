package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnectSample05 {

    public static void main(String[] args) {
        // 3. データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement spstmt = null; // 検索用
        PreparedStatement ipstmt = null; // 更新処理用
        ResultSet rs = null;

        try {
            // 1. ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "Araimonotanomu"
            );

            // 4. DBとやりとりする窓口（Statementオブジェクト）の作成
            String selectsql = "SELECT * FROM city WHERE CountryCode = ?";
            spstmt = con.prepareStatement(selectsql);

            // 5, 6. Select文の実行と結果を格納／代入
            System.out.print("CountryCodeを入力してください＞");
            String str1 = keyIn();
            
            spstmt.setString(1, str1);
            
            rs = spstmt.executeQuery();

            // 7. 結果を表示する
            System.out.println("更新前===============");// 更新前
            while( rs.next() ) {
                String name = rs.getString("Name");// Name列の値を取得
                String countryCode = rs.getString("CountryCode");
                String district = rs.getString("District");
                int population = rs.getInt("Population");
                System.out.println(name + "\t" + countryCode + "\t" + district + "\t" + population);
            }
         
            System.out.println("更新処理実行============");
            
            String insertsql = "INSERT INTO city (Name,CountryCode,District,Population) VALUES ('Rafah',?,'Rafah',?)";
            ipstmt = con.prepareStatement(insertsql);
            
            // 更新するPopulationを入力
            System.out.print("Populationを数字で入力してください＞");
            int num1 = keyInNum();
            
            // 入力されたPopulationとCountryCodeをPreparedStatementオブジェクトにセット
            ipstmt.setString(1, str1);
            ipstmt.setInt(2, num1);
            
            // update処理の実行および更新された行数を取得
            int count = ipstmt.executeUpdate();
            System.out.println("更新行数：" + count);
            
            // 更新後の結果を表示する
            rs.close();// 更新後の検索のため、一旦閉じる
            System.out.println("更新後=============");
            rs = spstmt.executeQuery();//検索の再実行と結果を格納・代入↓
            while (rs.next()) {
                String name = rs.getString("Name");
                String countryCode = rs.getString("CountryCode");
                String district = rs.getString("District");
                int population = rs.getInt("Population");
                System.out.println(name + "\t" + countryCode + "\t" + district + "\t" + population);
            }
            
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        } finally {
            // 8. 接続を閉じる
            if( rs != null ) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                }
            }
            if( ipstmt != null) {
                try {
                    ipstmt.close();
                } catch (SQLException e) {
                    System.err.println("PreparedStetementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if( spstmt != null) {
                try {
                    spstmt.close();
                } catch (SQLException e) {
                    System.err.println("PreparedStetementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if( con != null ) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
    }
    }
    }
    
    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        } catch (IOException e) {
            
        }
        return line;
    }
    
    private static int keyInNum() {
        int result = 0;
        try {
            result = Integer.parseInt(keyIn());
        } catch (NumberFormatException e) {
        }
        return result;
    }
}
