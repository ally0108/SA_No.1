package ncu.im3069.demo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import ncu.im3069.demo.app.Order2;
import ncu.im3069.demo.app.OrderHelper2;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/order2.do")
public class OrderController2 extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** ph，ProductHelper 之物件與 Product 相關之資料庫方法（Sigleton） */
	// private ProductHelper ph = ProductHelper.getHelper();

	/** oh，OrderHelper 之物件與 order 相關之資料庫方法（Sigleton） */
	private OrderHelper2 oh = OrderHelper2.getHelper();

	public OrderController2() {
		super();
	}

	/**
	 * 處理 Http Method 請求 GET 方法（新增資料）
	 *
	 * @param request  Servlet 請求之 HttpServletRequest 之 Request 物件（前端到後端）
	 * @param response Servlet 回傳之 HttpServletResponse 之 Response 物件（後端到前端）
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
		JsonReader jsr = new JsonReader(request);

		/** 取出經解析到 JsonReader 之 Request 參數 */
		String id = jsr.getParameter("id");

		/** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
		JSONObject resp = new JSONObject();

		/** 判斷該字串是否存在，若存在代表要取回個別訂單之資料，否則代表要取回全部資料庫內訂單之資料 */
		if (!id.isEmpty()) {
			/** 透過 orderHelper 物件的 getByID() 方法自資料庫取回該筆訂單之資料，回傳之資料為 JSONObject 物件 */
			JSONObject query = oh.getById(id);
			resp.put("status", "200");
			resp.put("message", "單筆訂單資料取得成功");
			resp.put("response", query);
		} else {
			/** 透過 orderHelper 物件之 getAll() 方法取回所有訂單之資料，回傳之資料為 JSONObject 物件 */
			JSONObject query = oh.getAll();
			resp.put("status", "200");
			resp.put("message", "所有訂單資料取得成功");
			resp.put("response", query);
		}

		/** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
		jsr.response(resp, response);
	}

	/**
	 * 處理 Http Method 請求 POST 方法（新增資料）
	 *
	 * @param request  Servlet 請求之 HttpServletRequest 之 Request 物件（前端到後端）
	 * @param response Servlet 回傳之 HttpServletResponse 之 Response 物件（後端到前端）
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
		JsonReader jsr = new JsonReader(request);
		JSONObject jso = jsr.getObject();

		/** 取出經解析到 JSONObject 之 Request 參數 */
		int member_id = jso.getInt("member_id");
		int credit_no = Integer.parseInt(jso.getString("credit_no"));
		String valid_time = jso.getString("valid_time");
		int certification = Integer.parseInt(jso.getString("certification"));
		int price = jso.getInt("price");
		System.out.println(valid_time);
		// JSONArray item = jso.getJSONArray("item");
		// JSONArray quantity = jso.getJSONArray("quantity");

		/** 建立一個新的訂單物件 */
		Order2 od = new Order2(member_id, credit_no, valid_time, certification, price);

		System.out.println(od.valid_time());
		/** 透過 orderHelper 物件的 create() 方法新建一筆訂單至資料庫 */
		JSONObject result = oh.create(od);

		/** 設定回傳回來的訂單編號與訂單細項編號 */
		od.setId((int) result.getLong("order_id"));
		// od.setOrderProductId(result.getJSONArray("order_product_id"));

		/** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
		JSONObject resp = new JSONObject();
		resp.put("status", "200");
		resp.put("message", "訂單新增成功！");
		resp.put("response", od.getOrderAllInfo());

		/** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
		jsr.response(resp, response);
	}

}
