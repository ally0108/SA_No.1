package ncu.im3069.demo.app;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Order2 {

	private int order_id;

	/** member_id，會員編號 */
	private int member_id;

	private int credit_no;

	private String valid_time;

	private int certification;

	private int price;

	/** list，訂單列表 */
	private ArrayList<OrderItem> list = new ArrayList<OrderItem>();

	/** create，訂單創建時間 */
	private Timestamp create;

	/** modify，訂單修改時間 */
	private Timestamp modify;

	/** oph，OrderItemHelper 之物件與 Order 相關之資料庫方法（Sigleton） */
	private OrderItemHelper oph = OrderItemHelper.getHelper();

	/**
	 * 實例化（Instantiates）一個新的（new）Order 物件<br>
	 * 採用多載（overload）方法進行，此建構子用於建立訂單資料時，產生一個新的訂單
	 *
	 * @param first_name 會員名
	 * @param last_name  會員姓
	 * @param email      會員電子信箱
	 * @param address    會員地址
	 * @param phone      會員姓名
	 */

	public Order2(int member_id, int credit_no, String valid_time, int certification, int price) {
		this.member_id = member_id;
		this.credit_no = credit_no;
		this.valid_time = valid_time;
		this.certification = certification;
		this.price = price;
		this.create = Timestamp.valueOf(LocalDateTime.now());
		this.modify = Timestamp.valueOf(LocalDateTime.now());
	}

	/**
	 * 實例化（Instantiates）一個新的（new）Order 物件<br>
	 * 採用多載（overload）方法進行，此建構子用於修改訂單資料時，新改資料庫已存在的訂單
	 *
	 * @param first_name 會員名
	 * @param last_name  會員姓
	 * @param email      會員電子信箱
	 * @param address    會員地址
	 * @param phone      會員姓名
	 * @param create     訂單創建時間
	 * @param modify     訂單修改時間
	 */
	public Order2(int order_id, int member_id, int credit_no, String valid_time, int certification, int price) {
		this.order_id = order_id;
		this.member_id = member_id;
		this.credit_no = credit_no;
		this.valid_time = valid_time;
		this.certification = certification;
		this.price = price;
		getOrderProductFromDB();
	}

	/**
	 * 新增一個訂單產品及其數量
	 */
	public void addOrderProduct(Product pd, int quantity) {
		this.list.add(new OrderItem(pd, quantity));
	}

	/**
	 * 新增一個訂單產品
	 */
	public void addOrderProduct(OrderItem op) {
		this.list.add(op);
	}

	/**
	 * 設定訂單編號
	 */
	public void setId(int id) {
		this.order_id = id;
	}

	/**
	 * 取得訂單編號
	 *
	 * @return int 回傳訂單編號
	 */
	public int getId() {
		return this.order_id;
	}

	/**
	 * 取得訂單會員的編號
	 *
	 * @return int 回傳訂單會員的編號
	 */
	public int member_id() {
		return this.member_id;
	}

	/**
	 * 取得信用卡號碼
	 *
	 * @return int 回傳信用卡號碼
	 */
	public int credit_no() {
		return this.credit_no;
	}

	/**
	 * 取得信用卡有效期限
	 *
	 * @return String 回傳信用卡有效期限
	 */
	public String valid_time() {
		return this.valid_time;
	}

	/**
	 * 取得訂單創建時間
	 *
	 * @return Timestamp 回傳訂單創建時間
	 */
	public Timestamp getCreateTime() {
		return this.create;
	}

	/**
	 * 取得訂單修改時間
	 *
	 * @return Timestamp 回傳訂單修改時間
	 */
	public Timestamp getModifyTime() {
		return this.modify;
	}

	/**
	 * 取得信用卡末三碼
	 *
	 * @return int 回傳信用卡末三碼
	 */
	public int certification() {
		return this.certification;
	}

	/**
	 * 取得訂單價錢
	 *
	 * @return int 回傳訂單價錢
	 */
	public int price() {
		return this.price;
	}

	/**
	 * 取得該名會員所有資料
	 *
	 * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
	 */
	public ArrayList<OrderItem> getOrderProduct() {
		return this.list;
	}

	/**
	 * 從 DB 中取得訂單產品
	 */
	private void getOrderProductFromDB() {
		ArrayList<OrderItem> data = oph.getOrderProductByOrderId(this.order_id);
		this.list = data;
	}

	/**
	 * 取得訂單基本資料
	 * 
	 * @return JSONObject 取得訂單基本資料
	 */
	public JSONObject getOrderData() {
		JSONObject jso = new JSONObject();
		jso.put("order_id", getId());
		jso.put("member_id", member_id());
		jso.put("credit_no", credit_no());
		jso.put("valid_time", valid_time());
		jso.put("certification", certification());
		jso.put("price", price());
		jso.put("create", getCreateTime());
		jso.put("modify", getModifyTime());

		return jso;
	}

	/**
	 * 取得訂單產品資料
	 *
	 * @return JSONArray 取得訂單產品資料
	 */
	public JSONArray getOrderProductData() {
		JSONArray result = new JSONArray();

		for (int i = 0; i < this.list.size(); i++) {
			result.put(this.list.get(i).getData());
		}

		return result;
	}

	/**
	 * 取得訂單所有資訊
	 *
	 * @return JSONObject 取得訂單所有資訊
	 */
	public JSONObject getOrderAllInfo() {
		JSONObject jso = new JSONObject();
		jso.put("order_info", getOrderData());
		jso.put("product_info", getOrderProductData());

		return jso;
	}

	/**
	 * 設定訂單產品編號
	 */
	public void setOrderProductId(JSONArray data) {
		for (int i = 0; i < this.list.size(); i++) {
			this.list.get(i).setId((int) data.getLong(i));
		}
	}

}
