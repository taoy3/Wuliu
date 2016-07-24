package com.wl.bean;

import java.io.Serializable;

/**
 * Created by taoy2 on 15-10-14.
 */
public class PortsInfo implements Serializable {

    /**
     * port_name : YANTIAN
     * tt : 0
     * no : 1
     * eta : 2015-08-30
     * etd : 2015-08-31
     * csi : 2015-08-28
     * port_id : cd368ae5-8e13-4317-8dab-92de449c72ba
     * cls : 2015-08-29
     * via : 0
     */
    private String port_name;
    private int tt;
    private int no;
    private String eta;
    private String etd;
    private String csi;
    private String port_id;
    private String cls;
    private int via;



    public void setPort_name(String port_name) {
        this.port_name = port_name;
    }

    public void setTt(int tt) {
        this.tt = tt;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public void setEtd(String etd) {
        this.etd = etd;
    }

    public void setCsi(String csi) {
        this.csi = csi;
    }

    public void setPort_id(String port_id) {
        this.port_id = port_id;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public void setVia(int via) {
        this.via = via;
    }

    public String getPort_name() {
        return port_name;
    }

    public int getTt() {
        return tt;
    }

    public int getNo() {
        return no;
    }

    public String getEta() {
        return eta;
    }

    public String getEtd() {
        return etd;
    }

    public String getCsi() {
        return csi;
    }

    public String getPort_id() {
        return port_id;
    }

    public String getCls() {
        return cls;
    }

    public int getVia() {
        return via;
    }
}
