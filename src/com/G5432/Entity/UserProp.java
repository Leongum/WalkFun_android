package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-2-28
 * Time: 下午2:31
 * To change this template use File | Settings | File Templates.
 */
public class UserProp {

    @DatabaseField(id = true, useGetSet = true)
    private String genId;
    @Expose
    @DatabaseField
    private Integer userId;
    @Expose
    @DatabaseField
    private Integer productId;
    @Expose
    @DatabaseField
    private String productName;
    @Expose
    @DatabaseField
    private Integer ownNumber;
    @Expose
    @DatabaseField
    private Date updateTime;

    public UserProp() {

    }

    public String getGenId() {
        return userId + "-" + productId;
    }

    public void setGenId(String genId) {
        this.genId = genId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getOwnNumber() {
        return ownNumber;
    }

    public void setOwnNumber(Integer ownNumber) {
        this.ownNumber = ownNumber;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
