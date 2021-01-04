package ncu.im3069.demo.app;

import java.util.Date;

import org.json.*;

public class Seat{

    private String seat_no;
    private int seat_class;
	
	public Seat(String seat_no,int seat_class) {
		this.seat_no=seat_no;
		this.seat_class=seat_class;
		
	}
   
	public String getSeatNO() {
		return this.seat_no;
	}
	   /**
     * @return String 回傳城市中文名稱
     */
	public int getSeatClass() {
		return this.seat_class;
	}
    /**
     * 取得班機資訊
     *
     * @return JSONObject 回傳產品資訊
     */
	public JSONObject getData() {
        /** 透過JSONObject將該項班機所需之資料全部進行封裝*/
        JSONObject jso = new JSONObject();
        jso.put("seat_no", getSeatNO());
        jso.put("seat_class", getSeatClass());
        return jso;
    }
	
}
