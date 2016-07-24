package com.wl.bean;

import java.io.Serializable;

/**
 * Created by taoy2 on 15-10-15.
 */
public class SurchargesEntity implements Serializable,Comparable<SurchargesEntity> {
    /**
     * gp40 : 1080
     * hq40 : 1080
     * fee_code : THC
     * gp20 : 740
     * feename : 码头费
     * fee_id : a74b83cc-8ae4-4066-a1fd-00244cb28d25
     * pay_type : ​1
     * usingType : ​2
     * paycurr : CNY
     * type : ​1
     */
    private String gp40;
    private String hq40;
    private String fee_code;
    private String gp20;
    private String name;
    private String fee_id;
    private String pay_type;
    private String usingType;
    private String curr;
    private String type;
    private String tprice;


    public String getTprice() {
        return tprice;
    }

    public SurchargesEntity() {
    }

    public SurchargesEntity(String name, String curr, String gp20,String gp40, String hq40 ) {
        this.gp40 = gp40;
        this.hq40 = hq40;
        this.gp20 = gp20;
        this.curr = curr;
        this.name = name;
    }

    public SurchargesEntity(String name) {
        this.name = name;
    }

    public void setTprice(String tprice) {
        this.tprice = tprice;
    }

    public void setGp40(String gp40) {
        this.gp40 = gp40;
    }

    public void setHq40(String hq40) {
        this.hq40 = hq40;
    }

    public void setFee_code(String fee_code) {
        this.fee_code = fee_code;
    }

    public void setGp20(String gp20) {
        this.gp20 = gp20;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFee_id(String fee_id) {
        this.fee_id = fee_id;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public void setUsingType(String usingType) {
        this.usingType = usingType;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGp40() {
        return gp40;
    }

    public String getHq40() {
        return hq40;
    }

    public String getFee_code() {
        return fee_code;
    }

    public String getGp20() {
        return gp20;
    }

    public String getName() {
        return name;
    }

    public String getFee_id() {
        return fee_id;
    }

    public String getPay_type() {
        return pay_type;
    }

    public String getUsingType() {
        return usingType;
    }

    public String getCurr() {
        return curr;
    }

    public String getType() {
        return type;
    }

    @Override
    public int compareTo(SurchargesEntity another) {
        if(another.getTprice()!=null&&another.getTprice().trim().length()>0){
            return -1;
        }
        return 1;
    }
}
