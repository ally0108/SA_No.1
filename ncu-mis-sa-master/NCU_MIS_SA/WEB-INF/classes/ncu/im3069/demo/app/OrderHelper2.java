package ncu.im3069.demo.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import ncu.im3069.demo.util.DBMgr;

public class OrderHelper2 {

	private static OrderHelper2 oh;
	private Connection conn = null;
	private PreparedStatement pres = null;
	private OrderItemHelper oph = OrderItemHelper.getHelper();

	private OrderHelper2() {
	}

	public static OrderHelper2 getHelper() {
		if (oh == null)
			oh = new OrderHelper2();

		return oh;
	}

	public JSONObject create(Order2 order) {
		/** 記錄實際執行之SQL指令 */
		String exexcute_sql = "";
		long id = -1;
		JSONArray opa = new JSONArray();

		try {
			/** 取得資料庫之連線 */
			conn = DBMgr.getConnection();
			/** SQL指令 */
			String sql = "INSERT INTO `sa_database`.`order`(`order_id`, `member_id`, `credit_no`, `valid_time`, `certification`,`create_time`, `total_price`)"
					+ " VALUES(23, ?, ?, ?, ?, current_time(), ?)";

			/** 取得所需之參數 */
			int member_id = order.member_id();
			int credit_no = order.credit_no();
			String valid_time = order.valid_time();
			int certification = order.certification();
			int price = order.price();

			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pres.setInt(1, member_id);
			pres.setInt(2, credit_no);
			pres.setString(3, valid_time);
			pres.setInt(4, certification);
			pres.setInt(5, price);
			System.out.println(exexcute_sql);
			/** 執行新增之SQL指令並記錄影響之行數 */
			pres.executeUpdate();

			/** 紀錄真實執行的SQL指令，並印出 **/
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);

			ResultSet rs = pres.getGeneratedKeys();

			if (rs.next()) {
				id = rs.getLong(1);
				ArrayList<OrderItem> opd = order.getOrderProduct();
				opa = oph.createByList(id, opd);
			}
		} catch (SQLException e) {
			/** 印出JDBC SQL指令錯誤 **/
			System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			/** 若錯誤則印出錯誤訊息 */
			e.printStackTrace();
		} finally {
			/** 關閉連線並釋放所有資料庫相關之資源 **/
			DBMgr.close(pres, conn);
		}

		/** 將SQL指令、花費時間與影響行數，封裝成JSONObject回傳 */
		JSONObject response = new JSONObject();
		response.put("order_id", id);
		response.put("order_product_id", opa);

		return response;
	}

	public JSONObject getAll() {
		Order2 o = null;
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
			String sql = "SELECT * FROM `sa_database`.`order`";

			/** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
			pres = conn.prepareStatement(sql);
			/** 執行查詢之SQL指令並記錄其回傳之資料 */
			rs = pres.executeQuery();

			/** 紀錄真實執行的SQL指令，並印出 **/
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);

			/** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
			while (rs.next()) {
				/** 每執行一次迴圈表示有一筆資料 */
				row += 1;

				/** 將 ResultSet 之資料取出 */
				int id = rs.getInt("id");
				int member_id = rs.getInt("member_id");
				int credit_no = rs.getInt("credit_no");
				String valid_time = rs.getString("valid_time");
				int certification = rs.getInt("certification");
				int price = rs.getInt("price");
				Timestamp create = rs.getTimestamp("create");
				Timestamp modify = rs.getTimestamp("modify");

				/** 將每一筆商品資料產生一名新Product物件 */
				o = new Order2(member_id, credit_no, valid_time, certification, price);
				/** 取出該項商品之資料並封裝至 JSONsonArray 內 */
				jsa.put(o.getOrderAllInfo());
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

	public JSONObject getById(String order_id) {
		JSONObject data = new JSONObject();
		Order2 o = null;
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
			String sql = "SELECT * FROM `missa`.`orders` WHERE `orders`.`id` = ?";

			/** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
			pres = conn.prepareStatement(sql);
			pres.setString(1, order_id);
			/** 執行查詢之SQL指令並記錄其回傳之資料 */
			rs = pres.executeQuery();

			/** 紀錄真實執行的SQL指令，並印出 **/
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);

			/** 透過 while 迴圈移動pointer，取得每一筆回傳資料 */
			while (rs.next()) {
				/** 每執行一次迴圈表示有一筆資料 */
				row += 1;

				/** 將 ResultSet 之資料取出 */
				int id = rs.getInt("id");
				int member_id = rs.getInt("member_id");
				int credit_no = rs.getInt("credit_no");
				String valid_time = rs.getString("valid_time");
				int certification = rs.getInt("certification");
				int price = rs.getInt("price");
				Timestamp create = rs.getTimestamp("create");
				Timestamp modify = rs.getTimestamp("modify");

				/** 將每一筆商品資料產生一名新Product物件 */
				o = new Order2(id, member_id, credit_no, valid_time, certification, price);
				/** 取出該項商品之資料並封裝至 JSONsonArray 內 */
				data = o.getOrderAllInfo();
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
		response.put("data", data);

		return response;
	}
}
