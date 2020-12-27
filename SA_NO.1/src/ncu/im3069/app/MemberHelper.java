package ncu.im3069.app;

import java.sql.*;
import java.time.LocalDateTime;
import org.json.*;

import ncu.im3069.app.Member;

import ncu.im3069.util.DBMgr;

//TODO: Auto-generated Javadoc
/**
* <p>
* The Class MemberHelper<br>
* MemberHelper���O�]class�^�D�n�޲z�Ҧ��PMember�����P��Ʈw����k�]method�^
* </p>
* 
* @author IPLab
* @version 1.0.0
* @since 1.0.0
*/

public class MemberHelper {
 
 /**
  * ��Ҥơ]Instantiates�^�@�ӷs���]new�^MemberHelper����<br>
  * �ĥ�Singleton���ݭn�z�Lnew
  */
 private MemberHelper() {
     
 }
 
 /** �R�A�ܼơA�x�sMemberHelper���� */
 private static MemberHelper mh;
 
 /** �x�sJDBC��Ʈw�s�u */
 private Connection conn = null;
 
 /** �x�sJDBC�w�ǳƤ�SQL���O */
 private PreparedStatement pres = null;
 
 /**
  * �R�A��k<br>
  * ��@Singleton�]��ҼҦ��^�A�Ȥ��\�إߤ@��MemberHelper����
  *
  * @return the helper �^��MemberHelper����
  */
 public static MemberHelper getHelper() {
     /** Singleton�ˬd�O�_�w�g��MemberHelper����A�Y�L�hnew�@�ӡA�Y���h�����^�� */
     if(mh == null) mh = new MemberHelper();
     
     return mh;
 }
 
 /**
  * �z�L�|���s���]ID�^�R���|��
  *
  * @param id �|���s��
  * @return the JSONObject �^��SQL���浲�G
  */
 public JSONObject deleteByID(int id) {
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
         String sql = "DELETE FROM `sa_database`.`member` WHERE `member_id` = ? LIMIT 1";
         
         /** �N�ѼƦ^���SQL���O�� */
         pres = conn.prepareStatement(sql);
         pres.setInt(1, id);
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
 
 /**
  * ���^�Ҧ��|�����
  *
  * @return the JSONObject �^��SQL���浲�G�P�۸�Ʈw���^���Ҧ����
  */
 public JSONObject getAll() {
     /** �s�ؤ@�� Member ���� m �ܼơA�Ω�����C�@��d�ߦ^���|����� */
     Member m = null;
     /** �Ω��x�s�Ҧ��˯��^���|���A�HJSONArray�覡�x�s */
     JSONArray jsa = new JSONArray();
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
         String sql = "SELECT * FROM `sa_database`.`member`";
         
         /** �N�ѼƦ^���SQL���O���A�Y�L�h���Υu�ݭn���� prepareStatement */
         pres = conn.prepareStatement(sql);
         /** ����d�ߤ�SQL���O�ðO����^�Ǥ���� */
         rs = pres.executeQuery();

         /** �����u����檺SQL���O�A�æL�X **/
         exexcute_sql = pres.toString();
         System.out.println(exexcute_sql);
         
         /** �z�L while �j�鲾��pointer�A���o�C�@���^�Ǹ�� */
         while(rs.next()) {
             /** �C����@���j���ܦ��@����� */
             row += 1;
             
             /** �N ResultSet ����ƨ��X */
             int id = rs.getInt("member_id");
             String first_name = rs.getString("first_name");
             String last_name = rs.getString("last_name");
             String email = rs.getString("email");
             String password = rs.getString("password");
             String phone = rs.getString("phone");
             String birthday = rs.getString("birthday");
             int auth = rs.getInt("auth");

             
             /** �N�C�@���|����Ʋ��ͤ@�W�sMember���� */
             m = new Member( email, password, first_name,last_name,phone, birthday, auth);
             /** ���X�ӦW�|������ƨëʸ˦� JSONsonArray �� */
             jsa.put(m.getData());
         }

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
     
     /** �NSQL���O�B��O�ɶ��B�v�T��ƻP�Ҧ��|����Ƥ�JSONArray�A�ʸ˦�JSONObject�^�� */
     JSONObject response = new JSONObject();
     response.put("sql", exexcute_sql);
     response.put("row", row);
     response.put("time", duration);
     response.put("data", jsa);

     return response;
 }
 
 /**
  * �z�L�|���s���]ID�^���o�|�����
  *
  * @param member_id �|���s��
  * @return the JSON object �^��SQL���浲�G�P�ӷ|���s�����|�����
  */
 public JSONObject getByID(String member_id) {
     /** �s�ؤ@�� Member ���� m �ܼơA�Ω�����C�@��d�ߦ^���|����� */
     Member m = null;
     /** �Ω��x�s�Ҧ��˯��^���|���A�HJSONArray�覡�x�s */
     JSONArray jsa = new JSONArray();
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
         String sql = "SELECT * FROM `sa_database`.`member` WHERE `member_id` = ? LIMIT 1";
         
         /** �N�ѼƦ^���SQL���O�� */
         pres = conn.prepareStatement(sql);
         pres.setString(1, member_id);
         /** ����d�ߤ�SQL���O�ðO����^�Ǥ���� */
         rs = pres.executeQuery();

         /** �����u����檺SQL���O�A�æL�X **/
         exexcute_sql = pres.toString();
         System.out.println(exexcute_sql);
         
         /** �z�L while �j�鲾��pointer�A���o�C�@���^�Ǹ�� */
         /** ���T�ӻ���Ʈw�u�|���@���ӷ|���s������ơA�]�����i�H���Ψϥ� while �j�� */
         while(rs.next()) {
             /** �C����@���j���ܦ��@����� */
             row += 1;
             
             /** �N ResultSet ����ƨ��X */
             int id = rs.getInt("member_id");
             String first_name = rs.getString("first_name");
             String last_name = rs.getString("last_name");
             String email = rs.getString("email");
             String password = rs.getString("password");
             String phone = rs.getString("phone");
             String birthday = rs.getString("birthday");
             int auth = rs.getInt("auth");
             
             /** �N�C�@���|����Ʋ��ͤ@�W�sMember���� */
             m = new Member(id, email, password,first_name,last_name,phone, birthday, auth);
             /** ���X�ӦW�|������ƨëʸ˦� JSONsonArray �� */
             jsa.put(m.getData());
         }
         
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
     
     /** �NSQL���O�B��O�ɶ��B�v�T��ƻP�Ҧ��|����Ƥ�JSONArray�A�ʸ˦�JSONObject�^�� */
     JSONObject response = new JSONObject();
     response.put("sql", exexcute_sql);
     response.put("row", row);
     response.put("time", duration);
     response.put("data", jsa);

     return response;
 }
 
 /**
  * ���o�ӦW�|������s�ɶ��P���ݤ��|���էO
  *
  * @param m �@�W�|����Member����
  * @return the JSON object �^�ǸӦW�|������s�ɶ��P���ݲէO�]�HJSONObject�i��ʸˡ^
  */
 public JSONObject getLoginTimesStatus(Member m) {
     /** �Ω��x�s�ӦW�|�����˯�����s�ɶ������ƻP�|���էO����� */
     JSONObject jso = new JSONObject();
     /** �x�sJDBC�˯���Ʈw��^�Ǥ����G�A�H pointer �覡���ʨ�U�@����� */
     ResultSet rs = null;

     try {
         /** ���o��Ʈw���s�u */
         conn = DBMgr.getConnection();
         /** SQL���O */
         String sql = "SELECT * FROM `sa_database`.`member` WHERE `member_id` = ? LIMIT 1";
         
         /** �N�ѼƦ^���SQL���O�� */
         pres = conn.prepareStatement(sql);
         pres.setInt(1, m.getID());
         /** ����d�ߤ�SQL���O�ðO����^�Ǥ���� */
         rs = pres.executeQuery();
         
         /** �z�L while �j�鲾��pointer�A���o�C�@���^�Ǹ�� */
         /** ���T�ӻ���Ʈw�u�|���@���ӹq�l�l�󤧸�ơA�]�����i�H���Ψϥ� while�j�� */
         while(rs.next()) {
             /** �N ResultSet ����ƨ��X */
             int login_times = rs.getInt("login_times");
             String status = rs.getString("status");
             /** �N��ʸ˦�JSONObject��� */
             jso.put("login_times", login_times);
             jso.put("status", status);
         }
         
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

     return jso;
 }
 
 /**
  * �ˬd�ӦW�|�����q�l�l��H�c�O�_���Ƶ��U
  *
  * @param m �@�W�|����Member����
  * @return boolean �Y���Ƶ��U�^��False�A�Y�ӫH�c���s�b�h�^��True
  */
 public boolean checkDuplicate(Member m){
     /** ����SQL�`��ơA�Y���u-1�v�N���Ʈw�˯��|������ */
     int row = -1;
     /** �x�sJDBC�˯���Ʈw��^�Ǥ����G�A�H pointer �覡���ʨ�U�@����� */
     ResultSet rs = null;
     
     try {
         /** ���o��Ʈw���s�u */
         conn = DBMgr.getConnection();
         /** SQL���O */
         String sql = "SELECT count(*) FROM `sa_database`.`member` WHERE `email` = ?";
         
         /** ���o�һݤ��Ѽ� */
         String email = m.getEmail();
         
         /** �N�ѼƦ^���SQL���O�� */
         pres = conn.prepareStatement(sql);
         pres.setString(1, email);
         /** ����d�ߤ�SQL���O�ðO����^�Ǥ���� */
         rs = pres.executeQuery();

         /** �����в����̫�@�C�A���o�ثe���X��b��Ʈw�� */
         rs.next();
         row = rs.getInt("count(*)");
         System.out.print(row);

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
     
     /** 
      * �P�_�O�_�w�g���@���ӹq�l�l��H�c�����
      * �Y�L�@���h�^��False�A�_�h�^��True 
      */
     return (row == 0) ? false : true;
 }
 
 /**
  * �إ߸ӦW�|���ܸ�Ʈw
  *
  * @param m �@�W�|����Member����
  * @return the JSON object �^��SQL���O���椧���G
  */
 public JSONObject create(Member m) {
     /** �O����ڰ��椧SQL���O */
     String exexcute_sql = "";
     /** �����{���}�l����ɶ� */
     long start_time = System.nanoTime();
     /** ����SQL�`��� */
     int row = 0;
     
     try {
         /** ���o��Ʈw���s�u */
         conn = DBMgr.getConnection();
         /** SQL���O */
         String sql = "INSERT INTO `sa_database`.`member`(`first_name`, `last_name`,`email`, `password`,`birthday`,`phone`,`auth`, `modify_time`, `create_time`)"
                 + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
         
         /** ���o�һݤ��Ѽ� */
         String first_name = m.getFirstName();
         String last_name = m.getLastName();
         String email = m.getEmail();
         String password = m.getPassword();
         String birthday= m.getBirthday();
         String phone = m.getPhone();
         int auth = m.getAuth();

         
         /** �N�ѼƦ^���SQL���O�� */
         pres = conn.prepareStatement(sql);
         pres.setString(1, first_name);
         pres.setString(2, last_name);
         pres.setString(3, email);
         pres.setString(4, password);
         pres.setString(5, birthday);
         pres.setString(6, phone);
         pres.setInt(7, auth);
         pres.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
         pres.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));

         
         /** ����s�W��SQL���O�ðO���v�T����� */
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
         DBMgr.close(pres, conn);
     }

     /** �����{����������ɶ� */
     long end_time = System.nanoTime();
     /** �����{������ɶ� */
     long duration = (end_time - start_time);

     /** �NSQL���O�B��O�ɶ��P�v�T��ơA�ʸ˦�JSONObject�^�� */
     JSONObject response = new JSONObject();
     response.put("sql", exexcute_sql);
     response.put("time", duration);
     response.put("row", row);

     return response;
 }
 
 /**
  * ��s�@�W�|�����|�����
  *
  * @param m �@�W�|����Member����
  * @return the JSONObject �^��SQL���O���浲�G�P���椧���
  */
 public JSONObject update(Member m) {
     /** �����^�Ǥ���� */
     JSONArray jsa = new JSONArray();
     /** �O����ڰ��椧SQL���O */
     String exexcute_sql = "";
     /** �����{���}�l����ɶ� */
     long start_time = System.nanoTime();
     /** ����SQL�`��� */
     int row = 0;
     
     try {
         /** ���o��Ʈw���s�u */
         conn = DBMgr.getConnection();
         /** SQL���O */
         String sql = "Update `sa_database`.`member` SET `first_name` = ? ,last_name` = ? ,`password` = ? , `birthday` = ? ,`phone` = ? ,`modify_time` = ? WHERE `email` = ?";
         /** ���o�һݤ��Ѽ� */
         String first_name = m.getFirstName();
         String last_name = m.getLastName();
         String email = m.getEmail();
         String password = m.getPassword();
         String birthday= m.getBirthday();
         String phone = m.getPhone();

         
         /** �N�ѼƦ^���SQL���O�� */
         pres = conn.prepareStatement(sql);
         pres.setString(1, first_name);
         pres.setString(2, last_name);
         pres.setString(3, password);
         pres.setString(4, birthday);
         pres.setString(5, phone);
         pres.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
         pres.setString(7, email);
         /** �����s��SQL���O�ðO���v�T����� */
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
         DBMgr.close(pres, conn);
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
     response.put("data", jsa);

     return response;
 }
 
 /**
  * ��s�|����s��Ƥ�������
  *
  * @param m �@�W�|����Member����
  */


}
