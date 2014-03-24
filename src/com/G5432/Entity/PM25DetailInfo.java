package com.G5432.Entity;

import com.google.gson.annotations.Expose;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-3
 * Time: 下午1:49
 * To change this template use File | Settings | File Templates.
 */
public class PM25DetailInfo {

    @Expose
    private Integer aqi;
    @Expose
    private String area;
    @Expose
    private Integer pm2_5;
    @Expose
    private Integer pm2_5_24h;
    @Expose
    private String quality;
    @Expose
    private String level;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getAqi() {
        return aqi;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    public Integer getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(Integer pm2_5) {
        this.pm2_5 = pm2_5;
    }

    public Integer getPm2_5_24h() {
        return pm2_5_24h;
    }

    public void setPm2_5_24h(Integer pm2_5_24h) {
        this.pm2_5_24h = pm2_5_24h;
    }
}
