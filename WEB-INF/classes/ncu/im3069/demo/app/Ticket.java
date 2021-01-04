package ncu.im3069.demo.app;

import java.util.Date;

import org.json.*;

public class Ticket{

    private int ticket_id;
    private int order_id;
    private String plane_no;
    private String seat_id;
    private String salutation;
	
	public Ticket(int ticket_id,int order_id,String plane_no,String seat_id,String salutation) {
		this.ticket_id=ticket_id;
		this.order_id= order_id;
		this.plane_no=plane_no;
		this.seat_id=seat_id;
		this.salutation=salutation;
		
	}
	public Ticket(String plane_no,String seat_id,String salutation) {
		this.plane_no=plane_no;
		this.seat_id=seat_id;
		this.salutation=salutation;
		
	}
    /**
     * @return String 回傳城市中文名稱
     */
	public int getTicketID() {
		return this.ticket_id;
	}
    /**
     * @return String 回傳城市中文名稱
     */
	public int getOrderID() {
		return this.order_id;
	}
	   /**
     * @return String 回傳城市中文名稱
     */
	public String getPlaneNO() {
		return this.plane_no;
	}
	 /**
     * @return String 回傳城市中文名稱
     */
	public String getSeatID() {
		return this.seat_id;
	}
	   /**
     * @return String 回傳城市中文名稱
     */
	public String getSalutation() {
		return this.salutation;
	}
    /**
     * 取得班機資訊
     *
     * @return JSONObject 回傳產品資訊
     */
	public JSONObject getData() {
        /** 透過JSONObject將該項班機所需之資料全部進行封裝*/
        JSONObject jso = new JSONObject();
        jso.put("plane_no", getPlaneNO());
        jso.put("seat_id", getSeatID());
        jso.put("salutation", getSalutation());
        return jso;
    }
	
}
