package ncu.im3069.app;

import org.json.*;
import java.util.Calendar;

/**
 * <p>
 * The Class Member
 * Member���O�]class�^�㦳�|���һݭn���ݩʻP��k�A�åB�x�s�P�|���������ӷ~�P�_�޿�<br>
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class Member {
	 
    /** id�A�|���s�� */
    private int id;
    
    /** email�A�|���q�l�l��H�c */
    private String email;
    
    /** name�A�|���m�W */
    private String first_name;
    
    private String last_name;
    
    /** password�A�|���K�X */
    private String password;
    
    private String phone;
    
    private String birthday;
    /** login_times�A��s�ɶ��������� */
    
    /** status�A�|�������O */
    private int auth;
    
    
    /** mh�AMemberHelper������PMember��������Ʈw��k�]Sigleton�^ */
    private MemberHelper mh =  MemberHelper.getHelper();
    
    /**
     * ��Ҥơ]Instantiates�^�@�ӷs���]new�^Member����<br>
     * �ĥΦh���]overload�^��k�i��A���غc�l�Ω�إ߷|����ƮɡA���ͤ@�W�s���|��
     *
     * @param email �|���q�l�H�c
     * @param password �|���K�X
     * @param name �|���m�W
     */
    public Member(String email, String password, String first_name,String last_name,String phone,String birthday,int auth) {
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.birthday = birthday;
        this.auth=auth;
        update();
    }

    /**
     * ��Ҥơ]Instantiates�^�@�ӷs���]new�^Member����<br>
     * �ĥΦh���]overload�^��k�i��A���غc�l�Ω��s�|����ƮɡA���ͤ@�W�|���P�ɻݭn�h��Ʈw�˯��즳��s�ɶ������ƻP�|���էO
     * 
     * @param id �|���s��
     * @param email �|���q�l�H�c
     * @param password �|���K�X
     * @param name �|���m�W
     */
    public Member(int id,String email, String password, String first_name,String last_name,String phone,String birthday,int auth) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.birthday = birthday;
        this.auth=auth;
        /** ���^�즳��Ʈw���ӦW�|������s�ɶ������ƻP�էO */

    }
    
    /**
     * ��Ҥơ]Instantiates�^�@�ӷs���]new�^Member����<br>
     * �ĥΦh���]overload�^��k�i��A���غc�l�Ω�d�߷|����ƮɡA�N�C�@����Ʒs�W���@�ӷ|������
     *
     * @param id �|���s��
     * @param email �|���q�l�H�c
     * @param password �|���K�X
     * @param name �|���m�W
     * @param login_times ��s�ɶ���������
     * @param status the �|�����էO
     */

    
    /**
     * ���o�|�����s��
     *
     * @return the id �^�Ƿ|���s��
     */
    public int getID() {
        return this.id;
    }

    /**
     * ���o�|�����q�l�l��H�c
     *
     * @return the email �^�Ƿ|���q�l�l��H�c
     */
    public String getEmail() {
        return this.email;
    }
    
    /**
     * ���o�|�����m�W
     *
     * @return the name �^�Ƿ|���m�W
     */
    public String getFirstName() {
        return this.first_name;
    }
    
    public String getLastName() {
        return this.last_name;
    }

    /**
     * ���o�|�����K�X
     *
     * @return the password �^�Ƿ|���K�X
     */
    public String getPassword() {
        return this.password;
    }
    
    /**
     * ���o��s��Ʈɶ���������
     *
     * @return the login times �^�ǧ�s��Ʈɶ���������
     */
    public String getBirthday() {
        return this.birthday;
    }
    public String getPhone() {
        return this.phone;
    }
    /**
     * ���o�|����Ƥ��|���էO
     *
     * @return the status �^�Ƿ|�����O
     */
    public int getAuth() {
        return this.auth;
    }
    
    /**
     * ��s�|�����
     *
     * @return the JSON object �^��SQL��s�����G�P�����ʸˤ����
     */
    public JSONObject update() {
        /** �s�ؤ@��JSONObject�ΥH�x�s��s�ᤧ��� */
        JSONObject data = new JSONObject();
        
        /** �ˬd�ӦW�|���O�_�w�g�b��Ʈw */
        if(this.id != 0) {
            /** �Y���h�N�ثe��s�ᤧ��Ƨ�s�ܸ�Ʈw�� */
 
            /** �z�LMemberHelper����A��s�ثe���|����Ƹm��Ʈw�� */
            data = mh.update(this);
        }
        
        return data;
    }
    
    /**
     * ���o�ӦW�|���Ҧ����
     *
     * @return the data ���o�ӦW�|�����Ҧ���ƨëʸ˩�JSONObject����
     */
    public JSONObject getData() {
        /** �z�LJSONObject�N�ӦW�|���һݤ���ƥ����i��ʸ�*/ 
        JSONObject jso = new JSONObject();
        jso.put("id", getID());
        jso.put("first_name", getFirstName());
        jso.put("last_name", getLastName());
        jso.put("email", getEmail());
        jso.put("password", getPassword());
        jso.put("phone", getPhone());
        jso.put("birthday", getBirthday());
        jso.put("auth", getAuth());
        
        return jso;
    }
}
