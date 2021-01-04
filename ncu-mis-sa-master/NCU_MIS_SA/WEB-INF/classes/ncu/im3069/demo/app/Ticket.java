package ncu.im3069.demo.app;

import org.json.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class Ticket Ticket類別（class）具有機票所需要之屬性與方法，並且儲存與機票相關之商業判斷邏輯<br>
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */

public class Ticket {

	/** id，機票編號 */
	private int ticket_id;

	/** order_id，訂單編號 */
	private int order_id;

	/** plane_seat_id，班機座位 */
	private int plane_seat_id;

	/** plane_no，班機編號 */
	private String plane_no;

	/** seat_no，座位編號 */
	private String seat_no;

	/** passport，護照編號 */
	private int passport;

	/** name，乘客姓名 */
	private String first_name;

	private String last_name;

	/** gender，乘客性別 */
	private int gender;

	/** birthday，乘客生日 */
	private String birthday;

	/** salutation，乘客 */
	private String salutation;

	/** th，TicketHelper之物件與Ticket相關之資料庫方法（Sigleton） */
	private TicketHelper th = TicketHelper.getHelper();

	/**
	 * 實例化（Instantiates）一個新的（new）Ticket物件<br>
	 * 採用多載（overload）方法進行，此建構子用於建立機票資料時，產生一張新的機票
	 *
	 * @param passport 乘客護照
	 * @param name     乘客姓名
	 */
	public Ticket(int order_id, int plane_seat_id, String plane_no, String seat_no, int passport, String first_name,
			String last_name, int gender, String birthday, String salutation) {
		this.order_id = order_id;
		this.plane_seat_id = plane_seat_id;
		this.plane_no = plane_no;
		this.seat_no = seat_no;
		this.passport = passport;
		this.first_name = first_name;
		this.last_name = last_name;
		this.gender = gender;
		this.birthday = birthday;
		this.salutation = salutation;
		// update();
	}

	/**
	 * 實例化（Instantiates）一個新的（new）Ticket物件<br>
	 * 採用多載（overload）方法進行，此建構子用於更新會員資料時，產生一名會員同時需要去資料庫檢索原有更新時間分鐘數與會員組別
	 * 
	 * @param id       會員編號
	 * @param email    會員電子信箱
	 * @param password 會員密碼
	 * @param name     會員姓名
	 */
//    public Ticket(int id,String email, String password, String first_name,String last_name,String phone,String birthday,int auth) {
//        this.id = id;
//        this.email = email;
//        this.password = password;
//        this.first_name = first_name;
//        this.last_name = last_name;
//        this.phone = phone;
//        this.birthday = birthday;
//        this.auth=auth;
//        /** 取回原有資料庫內該名會員之更新時間分鐘數與組別 */
//
//    }

	/**
	 * 實例化（Instantiates）一個新的（new）Ticket物件<br>
	 * 採用多載（overload）方法進行，此建構子用於查詢機票資料時，將每一筆資料新增為一個機票物件
	 *
	 * @param id          會員編號
	 * @param email       會員電子信箱
	 * @param password    會員密碼
	 * @param name        會員姓名
	 * @param login_times 更新時間的分鐘數
	 * @param status      the 會員之組別
	 */

	/**
	 * 設定訂單編號
	 */
	public void setId(int id) {
		this.ticket_id = id;
	}

	/**
	 * 取得機票之編號
	 *
	 * @return the id 回傳會員編號
	 */
	public int getID() {
		return this.ticket_id;
	}

	/**
	 * 取得該機票所屬之訂單編號
	 *
	 * @return the ticket_id 回傳機票之訂單編號
	 */
	public int getOrderID() {
		return this.order_id;
	}

	/**
	 * 取得該機票所屬之訂單編號
	 *
	 * @return the plane_seat_id 回傳機票之訂單編號
	 */
	public int getPlaneSeatID() {
		return this.plane_seat_id;
	}

	/**
	 * 取得該機票所屬之訂單編號
	 *
	 * @return the plane_no 回傳機票之班機編號
	 */
	public String getPlaneNo() {
		return this.plane_no;
	}

	/**
	 * 取得該機票所屬之訂單編號
	 *
	 * @return the seat_no 回傳機票之座位編號
	 */
	public String getSeatNo() {
		return this.seat_no;
	}

	/**
	 * 取得乘客之護照編號
	 *
	 * @return the passport 回傳乘客護照編號
	 */
	public int getPassport() {
		return this.passport;
	}

	/**
	 * 取得乘客之姓名
	 *
	 * @return the name 回傳乘客姓名
	 */
	public String getFirstName() {
		return this.first_name;
	}

	public String getLastName() {
		return this.last_name;
	}

	/**
	 * 取得會員乘客之性別
	 *
	 * @return the gender 回傳乘客性別
	 */
	public int getGender() {
		return this.gender;
	}

	/**
	 * 取得會員乘客之生日
	 *
	 * @return the birthday 回傳乘客生日
	 */
	public String getBirthday() {
		return this.birthday;
	}

	public String getSalutation() {
		return this.salutation;
	}

	/**
	 * 取得該名會員所有資料
	 *
	 * @return the data 取得該名會員之所有資料並封裝於JSONObject物件內
	 */
	public JSONObject getData() {
		/** 透過JSONObject將該名會員所需之資料全部進行封裝 */
		JSONObject jso = new JSONObject();
		jso.put("ticket_id", getID());
		jso.put("order_id", getOrderID());
		jso.put("plane_seat_id", getPlaneSeatID());
		jso.put("plane_no", getPlaneNo());
		jso.put("seat_no", getSeatNo());
		jso.put("first_name", getFirstName());
		jso.put("last_name", getLastName());
		jso.put("passport", getPassport());
		jso.put("gender", getGender());
		jso.put("birthday", getBirthday());
		jso.put("salutation", getSalutation());

		return jso;
	}
}
/**
 * 取得資料庫內之更新資料時間分鐘數與會員組別
 *
 */
