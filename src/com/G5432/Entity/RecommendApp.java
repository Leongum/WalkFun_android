package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-3
 * Time: 下午1:01
 * To change this template use File | Settings | File Templates.
 */
public class RecommendApp {
    @Expose
    @DatabaseField(id = true)
    private Integer appId;
    @Expose
    @DatabaseField
    private String appName;
    @Expose
    @DatabaseField
    private String appDescription;
    @Expose
    @DatabaseField
    private String appPicLink;
    @Expose
    @DatabaseField
    private String appAddress;
    @Expose
    @DatabaseField
    private Date updateTime;
    @Expose
    @DatabaseField
    private Integer recommendStatus;
    @Expose
    @DatabaseField
    private Integer sequence;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public String getAppPicLink() {
        return appPicLink;
    }

    public void setAppPicLink(String appPicLink) {
        this.appPicLink = appPicLink;
    }

    public String getAppAddress() {
        return appAddress;
    }

    public void setAppAddress(String appAddress) {
        this.appAddress = appAddress;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRecommendStatus() {
        return recommendStatus;
    }

    public void setRecommendStatus(Integer recommendStatus) {
        this.recommendStatus = recommendStatus;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
}
