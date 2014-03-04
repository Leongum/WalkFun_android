package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-8
 * Time: 下午3:15
 * To change this template use File | Settings | File Templates.
 */
public class UserBase {
    @Expose
    @DatabaseField(id = true)
    private Integer userId;
    @Expose
    @DatabaseField
    private String deviceId;
    @Expose
    @DatabaseField
    private String userEmail;
    @Expose
    @DatabaseField
    private String password;
    @Expose
    @DatabaseField
    private String userName;
    @Expose
    @DatabaseField
    private Integer age;
    @Expose
    @DatabaseField
    private String sex;
    @Expose
    @DatabaseField
    private String uuid;
    @Expose
    @DatabaseField
    private Double weight;
    @Expose
    @DatabaseField
    private Double height;
    @Expose
    @DatabaseField
    private String platformInfo;
    @DatabaseField
    private Date updateTime;

    private UserInfo userInfo;


    public UserBase() {

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getPlatformInfo() {
        return platformInfo;
    }

    public void setPlatformInfo(String platformInfo) {
        this.platformInfo = platformInfo;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
