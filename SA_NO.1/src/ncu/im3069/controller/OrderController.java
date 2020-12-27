package ncu.im3069.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import ncu.im3069.app.Order;
import ncu.im3069.app.OrderHelper;
import ncu.im3069.tools.JsonReader;

/**
 * Servlet implementation class OrderController
 */
@WebServlet("/OrderController")
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/** oh�AOrderHelper ������P order ��������Ʈw��k�]Sigleton�^ */
	private OrderHelper oh =  OrderHelper.getHelper();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * �B�zHttp Method�ШDGET��k�]���o��ơ^
     *
     * @param request Servlet�ШD��HttpServletRequest��Request����]�e�ݨ��ݡ^
     * @param response Servlet�^�Ǥ�HttpServletResponse��Response����]��ݨ�e�ݡ^
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** �z�LJsonReader���O�NRequest��JSON�榡��ƸѪR�è��^ */
        JsonReader jsr = new JsonReader(request);
        /** �Y�����z�L�e��AJAX��data�Hkey=value���r��覡�i��ǻ��ѼơA�i�H�����Ѧ���k���^��� */
        String id = jsr.getParameter("id");
        
        /** �P�_�Ӧr��O�_�s�b�A�Y�s�b�N��n���^�ӧO�q�椧��ơA�_�h�N��n���^������Ʈw���q�椧��� */
        if (id.isEmpty()) {
            /** �z�LOrderHelper����getAll()��k���^�Ҧ��q�椧��ơA�^�Ǥ���Ƭ�JSONObject���� */
            JSONObject query = oh.getAll();
            
            /** �s�ؤ@��JSONObject�Ω�N�^�Ǥ���ƶi��ʸ� */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "�Ҧ��q���ƨ��o���\");
            resp.put("response", query);
    
            /** �z�LJsonReader����^�Ǩ�e�ݡ]�HJSONObject�覡�^ */
            jsr.response(resp, response);
        }
        else {
            /** �z�LOrderHelper����getByID()��k�۸�Ʈw���^�ӦW�q�椧��ơA�^�Ǥ���Ƭ�JSONObject���� */
            JSONObject query = oh.getByID(id);
            
            /** �s�ؤ@��JSONObject�Ω�N�^�Ǥ���ƶi��ʸ� */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "�q���ƨ��o���\");
            resp.put("response", query);
    
            /** �z�LJsonReader����^�Ǩ�e�ݡ]�HJSONObject�覡�^ */
            jsr.response(resp, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * �B�zHttp Method�ШDDELETE��k�]�R���^
     *
     * @param request Servlet�ШD��HttpServletRequest��Request����]�e�ݨ��ݡ^
     * @param response Servlet�^�Ǥ�HttpServletResponse��Response����]��ݨ�e�ݡ^
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** �z�LJsonReader���O�NRequest��JSON�榡��ƸѪR�è��^ */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** ���X�g�ѪR��JSONObject��Request�Ѽ� */
        int id = jso.getInt("id");
        
        /** �z�LMemberHelper����deleteByID()��k�ܸ�Ʈw�R���ӦW�|���A�^�Ǥ���Ƭ�JSONObject���� */
        JSONObject query = oh.deleteByID(id);
        
        /** �s�ؤ@��JSONObject�Ω�N�^�Ǥ���ƶi��ʸ� */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "�q�沾�����\�I");
        resp.put("response", query);

        /** �z�LJsonReader����^�Ǩ�e�ݡ]�HJSONObject�覡�^ */
        jsr.response(resp, response);
	}

}
