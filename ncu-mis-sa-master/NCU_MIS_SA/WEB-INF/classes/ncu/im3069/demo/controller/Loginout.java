package ncu.im3069.demo.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import ncu.im3069.demo.app.Member;
import ncu.im3069.demo.app.MemberHelper;
import ncu.im3069.tools.JsonReader;

/**
 * Servlet implementation class Loginout
 */
@WebServlet("/api/Loginout.do")
public class Loginout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberHelper mh =  MemberHelper.getHelper();
    /**
     * @see HttpServlet#HttpServlet()
     */
 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
       
        String email = jso.getString("email");
        String password = jso.getString("password");
        
       
        
	  /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
	  if (email.isEmpty() || password.isEmpty()) {
	   /** 以字串組出JSON格式之資料 */
	   String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
	   /** 透過JsonReader物件回傳到前端（以字串方式） */
	   jsr.response(resp, response);
	  } else {
	   /** 驗證使用者 */
		  System.out.print("start validate...\n");
		 

		  Member member = mh. valPassword(email,password);
		 
		 

		 int success;
		 if(member!=null) {
			success=1;
			 
		
					 }else { success=0;}
	   /** 登入成功 */
	   if (success==1) {
	    Cookie cookie = new Cookie("memberName", member.getFirstName());
	    cookie.setMaxAge(60 * 60 * 24);
	    cookie.setPath("/");
	    response.addCookie(cookie);
	    
	    System.out.println(member.getAuth());
	    Cookie cookie1 = new Cookie("memberIsAdmin",String.valueOf(member.getAuth()));
	    cookie1.setMaxAge(60 * 60 * 24);
	    cookie1.setPath("/");
	    response.addCookie(cookie1);
	    
	    Cookie cookie2 = new Cookie("memberEmail",String.valueOf(member.getEmail()));
	    cookie2.setMaxAge(60 * 60 * 24);
	    cookie2.setPath("/");
	    response.addCookie(cookie2);
	    
	    String resp = "{status: '200', " + "message: '登入成功', " + "data: {email:" + member.getEmail() + "}}";
	    jsr.response(resp, response);
	   }
	   /** 登入失敗 */
	   else {
	    String resp = "{status: '401', " + "message: '登入失敗', }";
	    
	    /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	    jsr.response(resp, response);
	   }
	}

}}
