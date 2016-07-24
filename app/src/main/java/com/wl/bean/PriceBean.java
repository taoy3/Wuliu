package com.wl.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by taoy2 on 15-12-2.
 */
public class PriceBean {
    private String sc_name;
    private String sc_id;
    private String startPort = "起运港";//起运港
    private String line = "航线代码";//航线代码
    private List<PriceEntity> items;

    public PriceBean(FreightBean priceEntity) {
        this.sc_name = priceEntity.getSccode();
        this.startPort = priceEntity.getStart();
        this.line=priceEntity.getRoutecode();
        this.sc_id = priceEntity.getScid();
        this.items = new ArrayList<>();
    }

    public String getSc_id() {
        return sc_id;
    }

    public void setSc_id(String sc_id) {
        this.sc_id = sc_id;
    }

    public String getStartPort() {
        return startPort;
    }
    public String getLine() {
        return line;
    }

    public String getSc_name() {
        return sc_name;
    }
    public List<PriceEntity> getItems() {
        return items;
    }
}
