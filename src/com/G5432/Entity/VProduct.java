package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-2-28
 * Time: 下午2:47
 * To change this template use File | Settings | File Templates.
 */
public class VProduct {

    @Expose
    @DatabaseField(id = true)
    private Integer productId;
    @Expose
    @DatabaseField
    private String productName;
    @Expose
    @DatabaseField
    private String productDescription;
    @Expose
    @DatabaseField
    private Integer virtualPrice;
    @Expose
    @DatabaseField
    private Integer dropFlag;
    @Expose
    @DatabaseField
    private String picLink;
    @Expose
    @DatabaseField
    private String effectiveRule;
    @Expose
    @DatabaseField
    private String dropPicList;
    @Expose
    @DatabaseField
    private Double minLevelLimit;
    @Expose
    @DatabaseField
    private Double maxLevelLimit;
    @Expose
    @DatabaseField
    private Integer maxDropNum;
    @Expose
    @DatabaseField
    private Date updateTime;

    public VProduct(){

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

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public Integer getVirtualPrice() {
        return virtualPrice;
    }

    public void setVirtualPrice(Integer virtualPrice) {
        this.virtualPrice = virtualPrice;
    }

    public Integer getDropFlag() {
        return dropFlag;
    }

    public void setDropFlag(Integer dropFlag) {
        this.dropFlag = dropFlag;
    }

    public String getPicLink() {
        return picLink;
    }

    public void setPicLink(String picLink) {
        this.picLink = picLink;
    }

    public String getEffectiveRule() {
        return effectiveRule;
    }

    public void setEffectiveRule(String effectiveRule) {
        this.effectiveRule = effectiveRule;
    }

    public String getDropPicList() {
        return dropPicList;
    }

    public void setDropPicList(String dropPicList) {
        this.dropPicList = dropPicList;
    }

    public Double getMinLevelLimit() {
        return minLevelLimit;
    }

    public void setMinLevelLimit(Double minLevelLimit) {
        this.minLevelLimit = minLevelLimit;
    }

    public Double getMaxLevelLimit() {
        return maxLevelLimit;
    }

    public void setMaxLevelLimit(Double maxLevelLimit) {
        this.maxLevelLimit = maxLevelLimit;
    }

    public Integer getMaxDropNum() {
        return maxDropNum;
    }

    public void setMaxDropNum(Integer maxDropNum) {
        this.maxDropNum = maxDropNum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
