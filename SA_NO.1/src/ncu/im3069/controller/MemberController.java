package ncu.im3069.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import ncu.im3069.tools.JsonReader;
import ncu.im3069.app.Member;
import ncu.im3069.app.MemberHelper;

//TODO: Auto-generated Javadoc
/**
* <p>
* The Class MemberController<br>
* MemberController���O�]class�^�D�n�Ω�B�zMember������Http�ШD�]Request�^�A�~��HttpServlet
* </p>
* 
* @author IPLab
* @version 1.0.0
* @since 1.0.0
*/

public class MemberController extends HttpServlet {
 
 /** The Constant serialVersionUID. */
 private static final long serialVersionUID = 1L;
 
 /** mh�AMemberHelper������PMember��������Ʈw��k�]Sigleton�^ */
 private MemberHelper mh =  MemberHelper.getHelper();
 
 /**
  * �B�zHttp Method�ШDPOST��k�]�s�W��ơ^
  *
  * @param request Servlet�ШD��HttpServletRequest��Request����]�e�ݨ��ݡ^
  * @param response Servlet�^�Ǥ�HttpServletResponse��Response����]��ݨ�e�ݡ^
  * @throws ServletException the servlet exception
  * @throws IOException Signals that an I/O exception has occurred.
  */
 public void doPost(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
     /** �z�LJsonReader���O�NRequest��JSON�榡��ƸѪR�è��^ */
     JsonReader jsr = new JsonReader(request);
     JSONObject jso = jsr.getObject();
     
     /** ���X�g�ѪR��JSONObject��Request�Ѽ� */
     String email = jso.getString("email");
     String password = jso.getString("password");
     String first_name = jso.getString("first_name");
     String last_name = jso.getString("last_name");
     String phone = jso.getString("phone");
     String birthday = jso.getString("birthday");
     int auth = jso.getInt("auth");
     
     /** �إߤ@�ӷs���|������ */
     Member m = new Member(email, password, first_name ,last_name, phone, birthday, auth);
     
     /** ����ˬd�O�_����쬰�ŭȡA�Y���h�^�ǿ��~�T�� */
     if(email.isEmpty() || password.isEmpty() || first_name.isEmpty()) {
         /** �H�r��եXJSON�榡����� */
         String resp = "{\"status\": \'400\', \"message\": \'��줣�঳�ŭ�\', \'response\': \'\'}";
         /** �z�LJsonReader����^�Ǩ�e�ݡ]�H�r��覡�^ */
         jsr.response(resp, response);
     }
     /** �z�LMemberHelper����checkDuplicate()�ˬd�ӷ|���q�l�l��H�c�O�_������ */
     else if (!mh.checkDuplicate(m)) {
         /** �z�LMemberHelper����create()��k�s�ؤ@�ӷ|���ܸ�Ʈw */
         JSONObject data = mh.create(m);
         
         /** �s�ؤ@��JSONObject�Ω�N�^�Ǥ���ƶi��ʸ� */
         JSONObject resp = new JSONObject();
         resp.put("status", "200");
         resp.put("message", "���\! ���U�|�����...");
         resp.put("response", data);
         
         /** �z�LJsonReader����^�Ǩ�e�ݡ]�HJSONObject�覡�^ */
         jsr.response(resp, response);
     }
     else {
         /** �H�r��եXJSON�榡����� */
         String resp = "{\"status\": \'400\', \"message\": \'�s�W�b�����ѡA��E-Mail�b�����ơI\', \'response\': \'\'}";
         /** �z�LJsonReader����^�Ǩ�e�ݡ]�H�r��覡�^ */
         jsr.response(resp, response);
     }
 }

 /**
  * �B�zHttp Method�ШDGET��k�]���o��ơ^
  *
  * @param request Servlet�ШD��HttpServletRequest��Request����]�e�ݨ��ݡ^
  * @param response Servlet�^�Ǥ�HttpServletResponse��Response����]��ݨ�e�ݡ^
  * @throws ServletException the servlet exception
  * @throws IOException Signals that an I/O exception has occurred.
  */
 public void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
     /** �z�LJsonReader���O�NRequest��JSON�榡��ƸѪR�è��^ */
     JsonReader jsr = new JsonReader(request);
     /** �Y�����z�L�e��AJAX��data�Hkey=value���r��覡�i��ǻ��ѼơA�i�H�����Ѧ���k���^��� */
     String id = jsr.getParameter("id");
     
     /** �P�_�Ӧr��O�_�s�b�A�Y�s�b�N��n���^�ӧO�|������ơA�_�h�N��n���^������Ʈw���|������� */
     if (id.isEmpty()) {
         /** �z�LMemberHelper����getAll()��k���^�Ҧ��|������ơA�^�Ǥ���Ƭ�JSONObject���� */
         JSONObject query = mh.getAll();
         
         /** �s�ؤ@��JSONObject�Ω�N�^�Ǥ���ƶi��ʸ� */
         JSONObject resp = new JSONObject();
         resp.put("status", "200");
         resp.put("message", "�Ҧ��|����ƨ��o���\");
         resp.put("response", query);
 
         /** �z�LJsonReader����^�Ǩ�e�ݡ]�HJSONObject�覡�^ */
         jsr.response(resp, response);
     }
     else {
         /** �z�LMemberHelper����getByID()��k�۸�Ʈw���^�ӦW�|������ơA�^�Ǥ���Ƭ�JSONObject���� */
         JSONObject query = mh.getByID(id);
         
         /** �s�ؤ@��JSONObject�Ω�N�^�Ǥ���ƶi��ʸ� */
         JSONObject resp = new JSONObject();
         resp.put("status", "200");
         resp.put("message", "�|����ƨ��o���\");
         resp.put("response", query);
 
         /** �z�LJsonReader����^�Ǩ�e�ݡ]�HJSONObject�覡�^ */
         jsr.response(resp, response);
     }
 }

 /**
  * �B�zHttp Method�ШDDELETE��k�]�R���^
  *
  * @param request Servlet�ШD��HttpServletRequest��Request����]�e�ݨ��ݡ^
  * @param response Servlet�^�Ǥ�HttpServletResponse��Response����]��ݨ�e�ݡ^
  * @throws ServletException the servlet exception
  * @throws IOException Signals that an I/O exception has occurred.
  */
 public void doDelete(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
     /** �z�LJsonReader���O�NRequest��JSON�榡��ƸѪR�è��^ */
     JsonReader jsr = new JsonReader(request);
     JSONObject jso = jsr.getObject();
     
     /** ���X�g�ѪR��JSONObject��Request�Ѽ� */
     int id = jso.getInt("id");
     
     /** �z�LMemberHelper����deleteByID()��k�ܸ�Ʈw�R���ӦW�|���A�^�Ǥ���Ƭ�JSONObject���� */
     JSONObject query = mh.deleteByID(id);
     
     /** �s�ؤ@��JSONObject�Ω�N�^�Ǥ���ƶi��ʸ� */
     JSONObject resp = new JSONObject();
     resp.put("status", "200");
     resp.put("message", "�|���������\�I");
     resp.put("response", query);

     /** �z�LJsonReader����^�Ǩ�e�ݡ]�HJSONObject�覡�^ */
     jsr.response(resp, response);
 }

 /**
  * �B�zHttp Method�ШDPUT��k�]��s�^
  *
  * @param request Servlet�ШD��HttpServletRequest��Request����]�e�ݨ��ݡ^
  * @param response Servlet�^�Ǥ�HttpServletResponse��Response����]��ݨ�e�ݡ^
  * @throws ServletException the servlet exception
  * @throws IOException Signals that an I/O exception has occurred.
  */
 public void doPut(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
     /** �z�LJsonReader���O�NRequest��JSON�榡��ƸѪR�è��^ */
     JsonReader jsr = new JsonReader(request);
     JSONObject jso = jsr.getObject();
     
     /** ���X�g�ѪR��JSONObject��Request�Ѽ� */
     int id = jso.getInt("id");
     String email = jso.getString("email");
     String password = jso.getString("password");
     String first_name = jso.getString("first_name");
     String last_name = jso.getString("last_name");
     String phone = jso.getString("phone");
     String birthday = jso.getString("birthday");
     int auth = jso.getInt("auth");

     /** �z�L�ǤJ���ѼơA�s�ؤ@�ӥH�o�ǰѼƤ��|��Member���� */
     Member m = new Member(email, password, first_name ,last_name, phone, birthday, auth);
     
     /** �z�LMember����update()��k�ܸ�Ʈw��s�ӦW�|����ơA�^�Ǥ���Ƭ�JSONObject���� */
     JSONObject data = m.update();
     
     /** �s�ؤ@��JSONObject�Ω�N�^�Ǥ���ƶi��ʸ� */
     JSONObject resp = new JSONObject();
     resp.put("status", "200");
     resp.put("message", "���\! ��s�|�����...");
     resp.put("response", data);
     
     /** �z�LJsonReader����^�Ǩ�e�ݡ]�HJSONObject�覡�^ */
     jsr.response(resp, response);
 }
}