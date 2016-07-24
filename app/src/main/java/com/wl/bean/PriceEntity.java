package com.wl.bean;

import com.wl.BaseApp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by taoy2 on 15-12-2.
 */
public class PriceEntity implements Serializable{
    private String sc_name;
    private List<BoxBean> boxBeans = new ArrayList<>();
    private String id;
    private Integer cls;//截关日期
    private Integer etd;//开船日期
    private String startPort = "起运港";//起运港
    private String destPort = "目的港";//起运港
    private String startId = "起运港";//起运港
    private String destId = "目的港";//起运港
    private String line = "航线代码";//航线代码
    private int tt;
    private String remarks;
    private int via;
    private String svalid;
    private String evalid;
    private List<SurchargesEntity> surcharges;
    private String sc_id;

    public int getTt() {
        return tt;
    }

    public void setTt(int tt) {
        this.tt = tt;
    }

    public PriceEntity(FreightBean item) {
        this.sc_name = item.getSccode();
        for (int i = 0; i < item.getPrices().size(); i++) {
            boxBeans.add(new BoxBean(item.getPrices().get(i).getName(),getPrice(item.getPrices().get(i).getValue())));
        }
        this.id = item.getId();
        this.cls = item.getCls();
        this.etd = item.getEtd();
        this.startPort = item.getStart();
        this.destPort= item.getDest();
        this.startId = item.getStartid();
        this.destId = item.getDestid();
        this.line = item.getRoutecode();
        this.tt = item.getTt();
        this.remarks = item.getRemarks();
        this.via = item.getValid();
        this.svalid = item.getSvalid();
        this.evalid=item.getEvalid();
        this.surcharges = item.getSurcharges();
        this.sc_id = item.getScid();
    }

    public String getDestPort() {
        return destPort;
    }

    public void setDestPort(String destPort) {
        this.destPort = destPort;
    }

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getDestId() {
        return destId;
    }

    public void setDestId(String destId) {
        this.destId = destId;
    }

    private Float getPrice(Float price){
        if(price==null){
            return null;
        }
        return Float.valueOf(price)-(BaseApp.loginStatus==BaseApp.LOGINOK ?50:0);
    }

    public List<BoxBean> getBoxBeans() {
        return boxBeans;
    }

    public void setBoxBeans(List<BoxBean> boxBeans) {
        this.boxBeans = boxBeans;
    }

    public String getSc_name() {
        return sc_name;
    }

    public void setSc_name(String sc_name) {
        this.sc_name = sc_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCls() {
        return cls;
    }

    public void setCls(Integer cls) {
        this.cls = cls;
    }

    public Integer getEtd() {
        return etd;
    }

    public void setEtd(Integer etd) {
        this.etd = etd;
    }

    public String getStartPort() {
        return startPort;
    }

    public void setStartPort(String startPort) {
        this.startPort = startPort;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getRemarks() {
        return remarks;
    }

    public int getVia() {
        return via;
    }

    public String getSvalid() {
        return svalid;
    }

    public String getEvalid() {
        return evalid;
    }

    public List<SurchargesEntity> getSurcharges() {
        return surcharges;
    }

    public String getSc_id() {
        return sc_id;
    }
}
