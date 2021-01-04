package ncu.im3069.demo.app;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.*;

import ncu.im3069.demo.util.DBMgr;
import ncu.im3069.demo.app.Plane;

public class OrderHelper {
    private OrderHelper() {
        
    }
    
    private static OrderHelper oh;
    private Connection conn = null;
    private PreparedStatement pres = null;
    
    public static OrderHelper getHelper() {
        /** Singleton檢查是否已經有PlaneHelper物件，若無則new一個，若有則直接回傳 */
        if(oh == null) oh = new OrderHelper();
        
        return oh;
    }
    
    
    public JSONObject getByOrderID(int order_id) {
        /** 新建一個 Plane 物件之 m 變數，用於紀錄每一位查詢回之商品資料 */
    	Order o = null;
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
            String sql = "SELECT * FROM `sa_database`.`order` WHERE `order`.`order_id`=?" ;

            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1,order_id);
            
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
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss ");
                
                int order_ID=rs.getInt("order_id");
                String create_time = sdf.format(rs.getDate("create_time"));
                int price=rs.getInt("total_price");
                
                /** 將每一筆商品資料產生一名新Product物件 */
                o = new Order(order_ID,create_time,price);
                /** 取出該項商品之資料並封裝至 JSONsonArray 內 */
                jsa.put(o.getData());
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
  
}
