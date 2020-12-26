package ncu.im3069.util;

import java.math.BigDecimal; 
/** 
* ?��?��Java??�簡?��??�別不能夠精確�?��?�浮點數?��?��?��?��?��?��?�工?��類�?��?�精 
* 確�?�浮點數??��?��?��?�括??��?��?�除??��?�捨五入?? 
*/ 
public class Arith { 
    private static final int DEF_DIV_SCALE = 10; 
    
    private Arith() { 
    } 
    
    /** 
    * ??��?�精確�?��?��?��?��?��?? 
    * @param v1 被�?�數 
    * @param v2 ??�數 
    * @return ?��?��?�數??��?? 
    */ 
    public static double add(double v1,double v2){ 
        BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue(); 
    } 
    
    /** 
    * ??��?�精確�?��?��?��?��?��?? 
    * @param v1 被�?�數 
    * @param v2 減數 
    * @return ?��?��?�數??�差 
    */ 
    public static double sub(double v1,double v2) { 
        BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
        BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
        return b1.subtract(b2).doubleValue(); 
    } 
    
    /** 
    * ??��?�精確�?��?��?��?��?��?? 
    * @param v1 被�?�數 
    * @param v2 乘數 
    * @return ?��?��?�數??��?? 
    */ 
    public static double mul(double v1,double v2){ 
        BigDecimal b1 = new BigDecimal(Double.toString(v1)); 
        BigDecimal b2 = new BigDecimal(Double.toString(v2)); 
        return b1.multiply(b2).doubleValue(); 
    } 
    /** 
    * ??��?��?�相對�?�精確�?�除法�?��?��?�當?��??�除不盡??��?��?��?��?�精確到 
    * 小數點以�?10位�?�以後�?�數字�?�捨五入?? 
    * @param v1 被除?�� 
    * @param v2 ?��?�� 
    * @return ?��?��?�數??��?? 
    */ 
    public static double div(double v1,double v2) { 
        return div(v1,v2,DEF_DIV_SCALE); 
    } 
    /** 
    * ??��?��?�相對�?�精確�?�除法�?��?��?�當?��??�除不盡??��?��?��?��?�由scale引數??? 
    * 定精度�?�以後�?�數字�?�捨五入?? 
    * @param v1 被除?�� 
    * @param v2 ?��?�� 
    * @param scale 表示表示??要精確到小數點以後幾位�?? 
    * @return ?��?��?�數??��?? 
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
    * ??��?�精確�?��?�數位�?�捨五入??��?��?? 
    * @param v ??要�?�捨五入??�數�? 
    * @param scale 小數點�?��?��?�幾�? 
    * @return ??�捨五入後�?��?��?? 
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