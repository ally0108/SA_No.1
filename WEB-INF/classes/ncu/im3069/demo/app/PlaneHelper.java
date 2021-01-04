package ncu.im3069.demo.app;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.*;

import ncu.im3069.demo.util.DBMgr;
import ncu.im3069.demo.app.Plane;

public class PlaneHelper {
    private PlaneHelper() {
        
    }
    
    private static PlaneHelper ph;
    private Connection conn = null;
    private PreparedStatement pres = null;
    
    public static PlaneHelper getHelper() {
        /** Singleton檢查是否已經有PlaneHelper物件，若無則new一個，若有則直接回傳 */
        if(ph == null) ph = new PlaneHelper();
        
        return ph;
    }
    
    public JSONObject getAll() {
        /** 新建一個 Plane 物件之 m 變數，用於紀錄每一位查詢回之商品資料 */
    	Plane p = null;
        /** 用於儲存所有檢索回之商品，以JSONArray方式儲存 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa_database`.`plane`" ;
            
            /** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
            pres = conn.prepareStatement(sql);
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                
                /** 將 ResultSet 之資料取出 */
                String plane_no = rs.getString("plane_no");
                String departure_airport = rs.getString("departure_airport");
                String departure_date = rs.getString("departure_date");
                String departure_time = sdf.format(rs.getTime("departure_time"));
                String arrive_airport = rs.getString("arrive_airport");
                String arrive_date = rs.getString("arrive_date");                
                String arrive_time = sdf.format(rs.getTime("arrive_time"));
                int price1=rs.getInt("price1");
                int price2=rs.getInt("price2");
                int price3=rs.getInt("price3");
                
                /** 將每一筆商品資料產生一名新Product物件 */
                p = new Plane(plane_no,departure_airport,departure_date,departure_time,arrive_airport,arrive_date,arrive_time,price1,price2,price3);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(p.getData());
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);

        return response;
    } 
    
    public JSONObject getBySearch(String d_airport,String a_airport,String d_date) {
    	System.out.println(d_airport);
    	System.out.println(a_airport);
    	System.out.println(d_date);
        /** 新建一個 Plane 物件之 m 變數，用於紀錄每一位查詢回之商品資料 */
    	Plane p = null;
        /** 用於儲存所有檢索回之商品，以JSONArray方式儲存 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 紀錄SQL總行數 */
        int row = 0;
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        JSONArray d_data=null;
        JSONArray a_data =null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa_database`.`plane` WHERE `plane`.`departure_airport`=? AND ( `plane`.`arrive_airport`= ? AND `plane`.`departure_date` = ?)" ;

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1,d_airport);
            pres.setString(2,a_airport);
            pres.setString(3,d_date);
            
            System.out.printf(sql);
            
            
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                row += 1;
                
                /** 將 ResultSet 之資料取出 */
                
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                
                String plane_no = rs.getString("plane_no");
                String departure_airport = rs.getString("departure_airport");
                String departure_date = rs.getString("departure_date");
                String departure_time = sdf.format(rs.getTime("departure_time"));
                String arrive_airport = rs.getString("arrive_airport");
                String arrive_date = rs.getString("arrive_date");                
                String arrive_time = sdf.format(rs.getTime("arrive_time"));
                int price1=rs.getInt("price1");
                int price2=rs.getInt("price2");
                int price3=rs.getInt("price3");
                
                calculateFlight(rs.getTime("departure_time"),rs.getTime("arrive_time"));
                
                /** 將每一筆商品資料產生一名新Product物件 */
                p = new Plane(plane_no,departure_airport,departure_date,departure_time,arrive_airport,arrive_date,arrive_time,price1,price2,price3);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(p.getData());
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);

        d_data = getByAirportName(d_airport);
        a_data = getByAirportName(a_airport);
        
        /** 將SQL指令、花費時間、影響行數與所有會員資料之JSONArray，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);
        response.put("data", jsa);
        response.put("d_airport",d_data);
        response.put("a_airport",a_data);
        

        return response;
    }
    public JSONArray getByAirportName(String airport) {
        /** 新建一個 Plane 物件之 m 變數，用於紀錄每一位查詢回之商品資料 */
    	Airport a = null;
        /** 用於儲存所有檢索回之商品，以JSONArray方式儲存 */
        JSONArray jsa = new JSONArray();
        /** 記錄實際執行之SQL指令 */
        String exexcute_sql = "";
        /** 紀錄程式開始執行時間 */
        long start_time = System.nanoTime();
        /** 儲存JDBC檢索資料庫後回傳之結果，以 pointer 方式移動到下一筆資料 */
        ResultSet rs = null;
        
        try {
            /** 取得資料庫之連線 */
            conn = DBMgr.getConnection();
            /** SQL指令 */
            String sql = "SELECT * FROM `sa_database`.`airport` WHERE `airport`.`airport_name`=?" ;

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setString(1,airport);
            
            System.out.printf(sql);
            
            
            /** 執行查詢之SQL指令並記錄其回傳之資料 */
            rs = pres.executeQuery();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
            /** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
            while(rs.next()) {
                /** 每執行一次迴圈表示有一筆資料 */
                
                /** 將 ResultSet 之資料取出 */
                
                
                String airport_name = rs.getString("airport_name");
                String city_id = rs.getString("city_name");
                String city = rs.getString("city_name_in_chinese");
                
                /** 將每一筆商品資料產生一名新Product物件 */
                a = new Airport(airport_name,city_id,city);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(a.getAirportNameData());
            }

        } catch (SQLException e) {
            /** 印出JDBC SQL指令錯誤 **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** 若錯誤則印出錯誤訊息 */
            e.printStackTrace();
        } finally {
            /** 關閉連線並釋放所有資料庫相關之資源 **/
            DBMgr.close(rs, pres, conn);
        }
        
        /** 紀錄程式結束執行時間 */
        long end_time = System.nanoTime();
        /** 紀錄程式執行時間 */
        long duration = (end_time - start_time);
        
        

        return jsa;
    }
    
    public void calculateFlight(Date d_time,Date a_time) {
    	
    }
}
