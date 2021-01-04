package ncu.im3069.demo.app;

import java.util.Date;

import org.json.*;

public class Plane{

    /** id，班機編號 */
    private String plane_no;
    private String departure_airport;
    private String arrive_airport;
    private String departure_time;
    private String arrive_time;
    private String departure_date;
    private String arrive_date;
    private int price1;
    private int price2;
    private int price3;
    /** id，會員編號 */

    /**
     * 實例化（Instantiates）一個新的（new）Plane 物件<br>
     * 採用多載（overload）方法進行，此建構子用於查詢來回程
     *
     * @param plane_no 班機編號
     * @param a_airport 目的地機場
     * @param d_airport 出發機場
     * @param a_date 抵達日期
     * @param d_date 出發日期     *
     * @param a_time 抵達時間
     * @param d_time 出發時間
     * @param price1 頭等艙價錢
     * @param price2 商務艙價錢
     * @param price3 經濟艙價錢
     */
	public Plane(String plane_no,String d_airport,String d_date,String d_time,String a_airport,String a_date,String a_time,int price1,int price2,int price3) {
		this.plane_no = plane_no;
		this.arrive_airport=a_airport;
		this.departure_airport=d_airport;
		this.arrive_time=a_time;
		this.departure_time=d_time;
		this.arrive_date=a_date;
		this.departure_date=d_date;
		this.price1=price1;
		this.price2=price2;
		this.price3=price3;
		
	}

	public Plane(String plane_no,String d_airport,String d_date,String d_time,String a_airport,String a_date,String a_time) {
		this.plane_no = plane_no;
		this.arrive_airport=a_airport;
		this.departure_airport=d_airport;
		this.arrive_time=a_time;
		this.departure_time=d_time;
		this.arrive_date=a_date;
		this.departure_date=d_date;
		
	}
	public Plane(String d_airport,String d_date,String a_airport) {
		this.arrive_airport=a_airport;
		this.departure_airport=d_airport;
		this.departure_time=d_date;
		
	}

 
    /**
     * @return String 回傳班機編號
     */
	public String getPlaneNo() {
		return this.plane_no;
	}

    /**
     * @return Date 回傳抵達時間
     */
	public String getArriveTime() {
		return this.arrive_time;
	}

    /**
     * @return String 回傳抵達機場
     */
	public String getArriveAirport() {
		return this.arrive_airport;
	}

    /**
     * @return Date 回傳出發時間
     */
	public String getDepartureTime() {
		return this.departure_time;
	}
    /**
     * @return Date 回傳出發日期
     */
	public String getDepartureDate() {
		return this.departure_date;
	}
	
    /**
     * @return Date 回傳抵達日期
     */
	public String getArriveDate() {
		return this.arrive_date;
	}

    /**
     * @return String 回傳出發機場
     */
	public String getDepartureAirport() {
		return this.departure_airport;
	}
	
    /**
     * @return int 回傳頭等艙價錢
     */
	public int getPrice1() {
		return this.price1;
	}
    /**
     * @return int 回傳商務艙價錢
     */
	public int getPrice2() {
		return this.price2;
	}
    /**
     * @return int 回傳經濟艙價錢
     */
	public int getPrice3() {
		return this.price3;
	}


    /**
     * 取得班機資訊
     *
     * @return JSONObject 回傳產品資訊
     */
	public JSONObject getData() {
        /** 透過JSONObject將該項班機所需之資料全部進行封裝*/
        JSONObject jso = new JSONObject();
        jso.put("plane_no", getPlaneNo());
        jso.put("departure_airport", getDepartureAirport());
        jso.put("departure_time",getDepartureTime() );
        jso.put("departure_date",getDepartureDate() );
        jso.put("arrive_airport", getArriveAirport());
        jso.put("arrive_time", getArriveTime());
        jso.put("arrive_date", getArriveDate());
        jso.put("price1", getPrice1());
        jso.put("price2", getPrice2());
        jso.put("price3", getPrice3());
        return jso;
    }
}
