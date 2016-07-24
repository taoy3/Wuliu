package com.wl.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by taoy2 on 15-9-21.
 */
@Table(name = "TB_PORT")
public class PortsEntity implements Serializable, Comparable<PortsEntity> {
    /**
     * name_zh : ç‘žèŽ«ä¸¹
     * code : EGRAM
     * porttype : 0
     * countryname : EGYPT
     * id : 437508f2-d3d7-43e0-ba77-b0e94361aa6f
     * state : 1
     * countryid : 9e9eedaf-30ed-4922-a4e4-cf2fb88553ff
     * name_en : 10TH OF RAMADAN CITY
     */
    @Column(column = "name_zh")
    private String name_zh;
    @Column(column = "code")
    private String code;
    @Column(column = "porttype")
    private int porttype;
    @Column(column = "countryname")
    private String countryname;
    @Column(column = "id")
    private String id;
    @Column(column = "state")
    private int state;
    @Column(column = "countryid")
    private String countryid;
    @Column(column = "name_en")
    private String name_en;
    @Column(column = "locationtype")
    private String locationtype;

    public String getLocationtype() {
        return locationtype;
    }

    public void setLocationtype(String locationtype) {
        this.locationtype = locationtype;
    }

    public void setName_zh(String name_zh) {
        this.name_zh = name_zh;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPorttype(int porttype) {
        this.porttype = porttype;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setCountryid(String countryid) {
        this.countryid = countryid;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getName_zh() {
        return name_zh;
    }

    public String getCode() {
        return code;
    }

    public int getPorttype() {
        return porttype;
    }

    public String getCountryname() {
        return countryname;
    }

    public String getId() {
        return id;
    }

    public int getState() {
        return state;
    }

    public String getCountryid() {
        return countryid;
    }

    public String getName_en() {
        return name_en;
    }

//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }

    @Override
    public int compareTo(PortsEntity portsEntity) {
        return this.getName_en().charAt(0) - portsEntity.getName_en().charAt(0);
    }

    public PortsEntity(String name_zh, String code, String name_en) {
        this.name_zh = name_zh;
        this.code = code;
        this.name_en = name_en;
    }

    public PortsEntity() {
    }
}
