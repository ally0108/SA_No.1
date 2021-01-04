package ncu.im3069.demo.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Date;

import org.json.*;

public class Order {

    /** id，訂單編號 */
    private int order_id;
    private String create_time;
    private int total_price;

    public Order(int order_id,String create_time,int total_price) {
        this.order_id=order_id;
        this.create_time=create_time;
        this.total_price=total_price;
    }

    
    public int getOrderID() {
        return this.order_id;
    }
    public String getCreateTime() {
        return this.create_time;
    }
    public int getTotalPrice() {
        return this.total_price;
    }
    /**
     * 取得訂單基本資料
     *
     * @return JSONObject 取得訂單基本資料
     */
    public JSONObject getData() {
        JSONObject jso = new JSONObject();
        jso.put("order_id",getOrderID());
        jso.put("create_time",getCreateTime());
        jso.put("total_price",getTotalPrice());
        return jso;
    }


}
