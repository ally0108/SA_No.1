package ncu.im3069.demo.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

import ncu.im3069.demo.util.DBMgr;

public class TicketHelper {

	private static TicketHelper th;
	private Connection conn = null;
	private PreparedStatement pres = null;
	private OrderItemHelper oph = OrderItemHelper.getHelper();

	private TicketHelper() {
	}

	public static TicketHelper getHelper() {
		if (th == null)
			th = new TicketHelper();

		return th;
	}

	public JSONObject create(Ticket ticket) {
		/** 記錄實際執行之SQL指令 */
		String exexcute_sql = "";
		long id = -1;
		JSONArray opa = new JSONArray();

		try {
			/** 取得資料庫之連線 */
			conn = DBMgr.getConnection();

			/** SQL指令 */
			String sql = "INSERT INTO `sa_database`.`ticket`(`order_id`, `plane_seat_id`, `plane_no`, `seat_no`,`passport`, `birthday`,`gender`,`salutation`,`first_name`,`last_name`)"
					+ " VALUES( 1, ? , 'BR043', 'A2' ,?, ?, ?, ?, ?, ?)";

			/** 取得所需之參數 */
			int plane_seat_id = ticket.getPlaneSeatID();
			int passport = ticket.getPassport();
			String birthday = ticket.getBirthday();
			int gender = ticket.getGender();
			String salutation = ticket.getSalutation();
			String first_name = ticket.getFirstName();
			String last_name = ticket.getLastName();

			/** 將參數回填至SQL指令當中 */
			pres = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pres.setInt(1, plane_seat_id);
			pres.setInt(2, passport);
			pres.setString(3, birthday);
			pres.setInt(4, gender);
			pres.setString(5, salutation);
			pres.setString(6, first_name);
			pres.setString(7, last_name);
			System.out.println(exexcute_sql);

			/** 執行新增之SQL指令並記錄影響之行數 */
			pres.executeUpdate();

			/** 紀錄真實執行的SQL指令，並印出 **/
			exexcute_sql = pres.toString();
			System.out.println(exexcute_sql);

			// ResultSet rs = pres.getGeneratedKeys();

			// if (rs.next()) {
			// id = rs.getLong(1);
			// ArrayList<OrderItem> opd = ticket.getOrderProduct();
			// opa = oph.createByList(id, opd);
			// }
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
		// response.put("ticket_id", id);
		// response.put("order_product_id", opa);

		return response;
	}

	public JSONObject getAll() {
		Ticket t = null;
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
			String sql = "SELECT * FROM `sa_database`.`ticket`";

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
				int order_id = rs.getInt("order_id");
				int plane_seat_id = rs.getInt("plane_seat_id");
				String plane_no = rs.getString("plane_no");
				String seat_no = rs.getString("seat_no");
				int passport = rs.getInt("passport");
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				int gender = rs.getInt("gender");
				String birthday = rs.getString("birthday");
				String salutation = rs.getString("salutation");

				/** 將每一筆商品資料產生一名新Ticket物件 */
				t = new Ticket(order_id, plane_seat_id, plane_no, seat_no, passport, first_name, last_name, gender,
						birthday, salutation);
				/** 取出該項商品之資料並封裝至 JSONsonArray 內 */
				// jsa.put(t.getOrderAllInfo());
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

	public JSONObject getById(String ticket_id) {
		JSONObject data = new JSONObject();
		Ticket t = null;
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
			String sql = "SELECT * FROM `sa_database`.`ticket` WHERE `ticket`.`ticket_id` = ?";

			/** 將參數回填至SQL指令當中，若無則不用只需要執行 prepareStatement */
			pres = conn.prepareStatement(sql);
			pres.setString(1, ticket_id);
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
				int order_id = rs.getInt("order_id");
				int plane_seat_id = rs.getInt("plane_seat_id");
				String plane_no = rs.getString("plane_no");
				String seat_no = rs.getString("seat_no");
				int passport = rs.getInt("passport");
				String first_name = rs.getString("first_name");
				String last_name = rs.getString("last_name");
				int gender = rs.getInt("gender");
				String birthday = rs.getString("birthday");
				String salutation = rs.getString("salutation");

				/** 將每一筆商品資料產生一名新Product物件 */
				t = new Ticket(order_id, plane_seat_id, plane_no, seat_no, passport, first_name, last_name, gender,
						birthday, salutation);
				/** 取出該項商品之資料並封裝至 JSONsonArray 內 */
				// data = t.getOrderAllInfo();
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
