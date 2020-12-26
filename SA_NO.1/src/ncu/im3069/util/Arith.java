package ncu.im3069.util;

import java.math.BigDecimal; 
/** 
* ?”±?–¼Java??„ç°¡?–®??‹åˆ¥ä¸èƒ½å¤ ç²¾ç¢ºç?„å?æµ®é»æ•¸?²è?Œé?‹ç?—ï?Œé?™å?‹å·¥?…·é¡æ?ä?›ç²¾ 
* ç¢ºç?„æµ®é»æ•¸??‹ç?—ï?Œå?…æ‹¬?? æ?›ä?˜é™¤??Œå?›æ¨äº”å…¥?? 
*/ 
public class Arith { 
    private static final int DEF_DIV_SCALE = 10; 
    
    private Arith() { 
    } 
    
    /** 
    * ??ä?›ç²¾ç¢ºç?„å? æ?•é?‹ç?—ã?? 
    * @param v1 è¢«å? æ•¸ 
    * @param v2 ?? æ•¸ 
    * @return ?…©?‹å?•æ•¸??„å?? 
    */ 
    public static double add(double v1,double v2){ 
        BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue(); 
    } 
    
    /** 
    * ??ä?›ç²¾ç¢ºç?„æ?›æ?•é?‹ç?—ã?? 
    * @param v1 è¢«æ?›æ•¸ 
    * @param v2 æ¸›æ•¸ 
    * @return ?…©?‹å?•æ•¸??„å·® 
    */ 
    public static double sub(double v1,double v2) { 
        BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
        BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
        return b1.subtract(b2).doubleValue(); 
    } 
    
    /** 
    * ??ä?›ç²¾ç¢ºç?„ä?˜æ?•é?‹ç?—ã?? 
    * @param v1 è¢«ä?˜æ•¸ 
    * @param v2 ä¹˜æ•¸ 
    * @return ?…©?‹å?•æ•¸??„ç?? 
    */ 
    public static double mul(double v1,double v2){ 
        BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
        BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
        return b1.multiply(b2).doubleValue(); 
    } 
    /** 
    * ??ä?›ï?ˆç›¸å°ï?‰ç²¾ç¢ºç?„é™¤æ³•é?‹ç?—ï?Œç•¶?™¼??Ÿé™¤ä¸ç›¡??„æ?…æ?æ?‚ï?Œç²¾ç¢ºåˆ° 
    * å°æ•¸é»ä»¥å¾?10ä½ï?Œä»¥å¾Œç?„æ•¸å­—å?›æ¨äº”å…¥?? 
    * @param v1 è¢«é™¤?•¸ 
    * @param v2 ?™¤?•¸ 
    * @return ?…©?‹å?•æ•¸??„å?? 
    */ 
    public static double div(double v1,double v2) { 
        return div(v1,v2,DEF_DIV_SCALE); 
    } 
    /** 
    * ??ä?›ï?ˆç›¸å°ï?‰ç²¾ç¢ºç?„é™¤æ³•é?‹ç?—ã?‚ç•¶?™¼??Ÿé™¤ä¸ç›¡??„æ?…æ?æ?‚ï?Œç”±scaleå¼•æ•¸??? 
    * å®šç²¾åº¦ï?Œä»¥å¾Œç?„æ•¸å­—å?›æ¨äº”å…¥?? 
    * @param v1 è¢«é™¤?•¸ 
    * @param v2 ?™¤?•¸ 
    * @param scale è¡¨ç¤ºè¡¨ç¤º??è¦ç²¾ç¢ºåˆ°å°æ•¸é»ä»¥å¾Œå¹¾ä½ã?? 
    * @return ?…©?‹å?•æ•¸??„å?? 
    */ 
    public static double div(double v1,double v2,int scale) { 
        if(scale<0){ 
            throw new IllegalArgumentException( 
            "The scale must be a positive integer or zero"); 
        } 
        
        BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
        BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
        
        return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
    } 
    /** 
    * ??ä?›ç²¾ç¢ºç?„å?æ•¸ä½å?›æ¨äº”å…¥??•ç?†ã?? 
    * @param v ??è¦å?›æ¨äº”å…¥??„æ•¸å­? 
    * @param scale å°æ•¸é»å?Œä?ç?™å¹¾ä½? 
    * @return ??›æ¨äº”å…¥å¾Œç?„ç?æ?? 
    */ 
    public static double round(double v,int scale){ 
    if(scale<0){ 
    throw new IllegalArgumentException( 
    "The scale must be a positive integer or zero"); 
    } 
    BigDecimal b = new BigDecimal(Double.toString(v)); 
    BigDecimal one = new BigDecimal("1"); 
    return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue(); 
    } 
}; 