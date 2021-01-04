package ncu.im3069.demo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import ncu.im3069.demo.app.Ticket;
import ncu.im3069.demo.app.TicketHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/ticket.do")
public class TicketController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private TicketHelper th = TicketHelper.getHelper();

	public TicketController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
		JsonReader jsr = new JsonReader(request);
		/** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
		String id_list = jsr.getParameter("id_list");

		JSONObject resp = new JSONObject();
		/** 判斷該字串是否存在，若存在代表要取回購物車內產品之資料，否則代表要取回全部資料庫內產品之資料 */
		if (!id_list.isEmpty()) {
			// JSONObject query = th.getByIdList(id_list);
			resp.put("status", "200");
			resp.put("message", "所有購物車之商品資料取得成功");
			// resp.put("response", query);
		} else {
			JSONObject query = th.getAll();

			resp.put("status", "200");
			resp.put("message", "所有商品資料取得成功");
			resp.put("response", query);
		}

		jsr.response(resp, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/** 透過 JsonReader 類別將 Request 之 JSON 格式資料解析並取回 */
		JsonReader jsr = new JsonReader(request);
		JSONObject jso = jsr.getObject();

		/** 取出經解析到 JSONObject 之 Request 參數 */
		int order_id = jso.getInt("order_id");
		int plane_seat_id = Integer.parseInt(jso.getString("plane_seat_id"));
		String plane_no = jso.getString("plane_no");
		String seat_no = jso.getString("seat_no");
		String first_name = jso.getString("first_name");
		String last_name = jso.getString("last_name");
		int passport = Integer.parseInt(jso.getString("passport"));
		int gender = jso.getInt("gender");
		String birthday = jso.getString("birthday");
		String salutation = jso.getString("salutation");

		/** 建立一個新的機票物件 */
		Ticket t = new Ticket(order_id, plane_seat_id, plane_no, seat_no, passport, first_name, last_name, gender,
				birthday, salutation);

		System.out.println(t.getPassport());
		/** 透過 orderHelper 物件的 create() 方法新建一筆訂單至資料庫 */
		JSONObject result = th.create(t);

		/** 設定回傳回來的訂單編號與訂單細項編號 */
		t.setId((int) result.getLong("ticket_id"));
		// od.setOrderProductId(result.getJSONArray("order_product_id"));

		/** 新建一個 JSONObject 用於將回傳之資料進行封裝 */
		JSONObject resp = new JSONObject();
		resp.put("status", "200");
		resp.put("message", "訂單新增成功！");
		// resp.put("response", t.getOrderAllInfo());

		/** 透過 JsonReader 物件回傳到前端（以 JSONObject 方式） */
		jsr.response(resp, response);
	}

}
