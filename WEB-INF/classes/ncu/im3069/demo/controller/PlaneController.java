package ncu.im3069.demo.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.demo.app.PlaneHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/plane.do")
public class PlaneController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PlaneHelper ph =  PlaneHelper.getHelper();

    public PlaneController() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String departure_airport = jso.getString("departure_airport");
        String arrive_airport = jso.getString("arrive_airport");
        String departure_date=jso.getString("departure_date");
        int num_passenger=jso.getInt("num_passenger");
        
        
        System.out.println(departure_airport);
    	System.out.println(arrive_airport);
    	System.out.println(departure_date);
        JSONObject resp = new JSONObject();
        /** 判斷該字串是否存在，若存在代表要取回購物車內產品之資料，否則代表要取回全部資料庫內產品之資料 */
        
        JSONObject query = ph.getBySearch(departure_airport,arrive_airport,departure_date);
        resp.put("status", "200");
        resp.put("message", "所有班機取得成功");
        resp.put("response", query);
        
        jsr.response(resp, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
