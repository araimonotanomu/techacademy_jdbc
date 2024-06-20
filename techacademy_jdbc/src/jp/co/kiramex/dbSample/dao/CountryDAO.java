package jp.co.kiramex.dbSample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.kiramex.dbSample.entity.Country;
import jp.co.kiramex.dbSample.util.DatabaseManager;

public class CountryDAO {
    
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    // 検索結果に合致するCountryオブジェクトリストを取得するメソッド
    
    public List<Country> getCountryFromName(String name){
        // メソッドの結果として返すリスト
        List<Country> results = new ArrayList<Country>();
        
        try {
            // ドライバを呼び込み、データベースに接続
            Connection con = DatabaseManager.getConnection();
            
            // データベースとやりとりする窓口
            String sql = "select * from country where Name = ?";
            pstmt = con.prepareStatement(sql);
            
            // select文の実行と結果を格納/代入
            pstmt.setString(1,  name);
            rs = pstmt.executeQuery();
            
            // 結果を表示する
            while (rs.next()) {
                // 1件ずつCountryオブジェクトを生成して結果を詰める
                Country country = new Country();
                country.setName(rs.getString("Name"));
                country.setPopulation(rs.getInt("Population"));
                
                // リストに追加
                results.add(country);
            }
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if( pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DatabaseManager.close();
        }
        return results;
    }

}
