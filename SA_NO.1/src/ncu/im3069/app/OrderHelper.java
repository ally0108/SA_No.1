package ncu.im3069.app;

import java.sql.*;
import java.util.*;
import org.json.JSONObject;

import ncu.im3069.util.DBMgr;

public class OrderHelper {
	
	/** 靜態變數，儲存OrderHelper物件 */
	private static OrderHelper oh;
	
	/** 儲存JDBC資料庫連線 */
	private Connection conn = null;
	
	/** 儲存JDBC預準備之SQL指令 */
	private PreparedStatement pres = null;
	
    /**
     * 靜態方法<br>
     * 實作Singleton（單例模式），僅允許建立一個OrderHelper物件
     *
     * @return the helper 回傳OrderHelper物件
     */
	public static OrderHelper getHelper() {
		/** Singleton檢查是否已經有OrderHelper物件，若無則new一個，若有則直接回傳 */
        if(oh == null) oh = new OrderHelper();
        
        return oh;
	}
	
    /**
     * 透過會員編號（ID）刪除訂單
     *
     * @param order_id 訂單編號
     * @return the JSONObject 回傳SQL執行結果
     */
	public JSONObject deleteByID(int order_id) {
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
            String sql = "DELETE FROM `ncu-mis-sa-master`.`orders` WHERE `id` = ? LIMIT 1";
            
            /** 將參數回填至SQL指令當中 */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, order_id);
            /** 執行刪除之SQL指令並記錄影響之行數 */
            row = pres.executeUpdate();

            /** 紀錄真實執行的SQL指令，並印出 **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
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
        
        /** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);

        return response;
	}

	public JSONObject getByID(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONObject getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
