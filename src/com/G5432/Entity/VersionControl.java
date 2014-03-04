package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-14
 * Time: 下午8:25
 * To change this template use File | Settings | File Templates.
 */
public class VersionControl {

    private String platform;
    private Integer version;
    private Integer subVersion;
    private String description;
    private Date systemTime;
    private Date missionLastUpdateTime;
    private Date messageLastUpdateTime;
    private Date recommendLastUpdateTime;
    private Date productLastUpdateTime;
    private Date actionDefineUpdateTime;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getSubVersion() {
        return subVersion;
    }

    public void setSubVersion(Integer subVersion) {
        this.subVersion = subVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(Date systemTime) {
        this.systemTime = systemTime;
    }

    public Date getMissionLastUpdateTime() {
        return missionLastUpdateTime;
    }

    public void setMissionLastUpdateTime(Date missionLastUpdateTime) {
        this.missionLastUpdateTime = missionLastUpdateTime;
    }

    public Date getMessageLastUpdateTime() {
        return messageLastUpdateTime;
    }

    public void setMessageLastUpdateTime(Date messageLastUpdateTime) {
        this.messageLastUpdateTime = messageLastUpdateTime;
    }

    public Date getRecommendLastUpdateTime() {
        return recommendLastUpdateTime;
    }

    public void setRecommendLastUpdateTime(Date recommendLastUpdateTime) {
        this.recommendLastUpdateTime = recommendLastUpdateTime;
    }

    public Date getProductLastUpdateTime() {
        return productLastUpdateTime;
    }

    public void setProductLastUpdateTime(Date productLastUpdateTime) {
        this.productLastUpdateTime = productLastUpdateTime;
    }

    public Date getActionDefineUpdateTime() {
        return actionDefineUpdateTime;
    }

    public void setActionDefineUpdateTime(Date actionDefineUpdateTime) {
        this.actionDefineUpdateTime = actionDefineUpdateTime;
    }
}
