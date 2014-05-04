package com.G5432.Entity;

import com.google.gson.annotations.Expose;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-24
 * Time: 上午11:36
 * To change this template use File | Settings | File Templates.
 */
public class WalkEvent {

    @Expose
    private String  eType;
    @Expose
    private Integer eId;
    @Expose
    private Integer fId;
    @Expose
    private Integer eWin;
    @Expose
    private Integer times;
    @Expose
    private Double lati;
    @Expose
    private Double longi;
    @Expose
    private Double power;

    public String geteType() {
        return eType;
    }

    public void seteType(String eType) {
        this.eType = eType;
    }

    public Integer geteId() {
        return eId;
    }

    public void seteId(Integer eId) {
        this.eId = eId;
    }

    public Integer geteWin() {
        return eWin;
    }

    public void seteWin(Integer eWin) {
        this.eWin = eWin;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Double getLati() {
        return lati;
    }

    public void setLati(Double lati) {
        this.lati = lati;
    }

    public Double getLongi() {
        return longi;
    }

    public void setLongi(Double longi) {
        this.longi = longi;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Integer getfId() {
        return fId;
    }

    public void setfId(Integer fId) {
        this.fId = fId;
    }
}
