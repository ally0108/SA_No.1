package ncu.im3069.demo.app;

import java.util.Date;

import org.json.*;

public class Airport{

    private int airport_id;
    private String airport_name;
    private String city_name;
    private String city_name_in_chinese;
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
	public Airport(int airport_id,String airport_name,String city_name,String city_name_in_chinese) {
	
		this.airport_id=airport_id;
		this.airport_name=airport_name;
		this.city_name=city_name;
		this.city_name_in_chinese=city_name_in_chinese;
		
	}

	public Airport(String airport_name,String city_name,String city_name_in_chinese) {
		this.airport_name=airport_name;
		this.city_name=city_name;
		this.city_name_in_chinese=city_name_in_chinese;
		
	}
    /**
     * @return int 回傳機場代號
     */
	public int getAirportID() {
		return this.airport_id;
	}
 
    /**
     * @return String 回傳機場名稱
     */
	public String getAirportName() {
		return this.airport_name;
	}
    /**
     * @return String 回傳城市代號
     */
	public String getCityName() {
		return this.city_name;
	}
    /**
     * @return String 回傳城市中文名稱
     */
	public String getCityNameInChinese() {
		return this.city_name_in_chinese;
	}



    /**
     * 取得班機資訊
     *
     * @return JSONObject 回傳產品資訊
     */
	public JSONObject getData() {
        /** 透過JSONObject將該項班機所需之資料全部進行封裝*/
        JSONObject jso = new JSONObject();
        jso.put("airport_id", getAirportID());
        jso.put("airport_name", getAirportName());
        jso.put("city_name",getCityName() );
        jso.put("city_name_in_chinese",getCityNameInChinese() );
        return jso;
    }
	public JSONObject getAirportNameData() {
        /** 透過JSONObject將該項班機所需之資料全部進行封裝*/
        JSONObject jso = new JSONObject();
        jso.put("airport_name", getAirportName());
        jso.put("city_name",getCityName() );
        jso.put("city_name_in_chinese",getCityNameInChinese() );
        return jso;
    }
}
