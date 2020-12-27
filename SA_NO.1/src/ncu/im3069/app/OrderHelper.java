package ncu.im3069.app;

import java.sql.*;
import java.util.*;
import org.json.JSONObject;

import ncu.im3069.util.DBMgr;

public class OrderHelper {
	
	/** �R�A�ܼơA�x�sOrderHelper���� */
	private static OrderHelper oh;
	
	/** �x�sJDBC��Ʈw�s�u */
	private Connection conn = null;
	
	/** �x�sJDBC�w�ǳƤ�SQL���O */
	private PreparedStatement pres = null;
	
    /**
     * �R�A��k<br>
     * ��@Singleton�]��ҼҦ��^�A�Ȥ��\�إߤ@��OrderHelper����
     *
     * @return the helper �^��OrderHelper����
     */
	public static OrderHelper getHelper() {
		/** Singleton�ˬd�O�_�w�g��OrderHelper����A�Y�L�hnew�@�ӡA�Y���h�����^�� */
        if(oh == null) oh = new OrderHelper();
        
        return oh;
	}
	
    /**
     * �z�L�|���s���]ID�^�R���q��
     *
     * @param order_id �q��s��
     * @return the JSONObject �^��SQL���浲�G
     */
	public JSONObject deleteByID(int order_id) {
		 /** �O����ڰ��椧SQL���O */
        String exexcute_sql = "";
        /** �����{���}�l����ɶ� */
        long start_time = System.nanoTime();
        /** ����SQL�`��� */
        int row = 0;
        /** �x�sJDBC�˯���Ʈw��^�Ǥ����G�A�H pointer �覡���ʨ�U�@����� */
        ResultSet rs = null;
        
        try {
            /** ���o��Ʈw���s�u */
            conn = DBMgr.getConnection();
            
            /** SQL���O */
            String sql = "DELETE FROM `ncu-mis-sa-master`.`orders` WHERE `id` = ? LIMIT 1";
            
            /** �N�ѼƦ^���SQL���O�� */
            pres = conn.prepareStatement(sql);
            pres.setInt(1, order_id);
            /** ����R����SQL���O�ðO���v�T����� */
            row = pres.executeUpdate();

            /** �����u����檺SQL���O�A�æL�X **/
            exexcute_sql = pres.toString();
            System.out.println(exexcute_sql);
            
        } catch (SQLException e) {
            /** �L�XJDBC SQL���O���~ **/
            System.err.format("SQL State: %s\n%s\n%s", e.getErrorCode(), e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            /** �Y���~�h�L�X���~�T�� */
            e.printStackTrace();
        } finally {
            /** �����s�u������Ҧ���Ʈw�������귽 **/
            DBMgr.close(rs, pres, conn);
        }

        /** �����{����������ɶ� */
        long end_time = System.nanoTime();
        /** �����{������ɶ� */
        long duration = (end_time - start_time);
        
        /** �NSQL���O�B��O�ɶ��P�v�T��ơA�ʸ˦�JSONObject�^�� */
        JSONObject response = new JSONObject();
        response.put("sql", exexcute_sql);
        response.put("row", row);
        response.put("time", duration);

        return response;
	}

	public JSONObject getByID(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public JSONObject getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
