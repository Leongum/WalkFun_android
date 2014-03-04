package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-14
 * Time: 下午8:42
 * To change this template use File | Settings | File Templates.
 */
public class SystemMessage {

    @Expose
    @DatabaseField(id = true)
    private Integer messageId;
    @Expose
    @DatabaseField
    private String message;
    @Expose
    @DatabaseField
    private String rule;
    @Expose
    @DatabaseField
    private Date lastUpdateTime;

    public SystemMessage() {

    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
