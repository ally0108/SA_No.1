package ncu.im3069.demo.controller;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.demo.app.OrderHelper;
import ncu.im3069.demo.app.TicketHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/ticket.do")
public class TicketController extends HttpServlet {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    /** oh，OrderHelper 之物件與 order 相關之資料庫方法（Sigleton） */
	private TicketHelper th =  TicketHelper.getHelper();

    public TicketController() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        
        int order_id=jso.getInt("order_id");
        
        
        JSONObject resp = new JSONObject();
        /** 判斷該字串是否存在，若存在代表要取回購物車內產品之資料，否則代表要取回全部資料庫內產品之資料 */
        
        JSONObject query = th.getByOrderID(order_id);
        resp.put("status", "200");
        resp.put("message", "所有資訊取得成功");
        resp.put("response", query);
        
        jsr.response(resp, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
