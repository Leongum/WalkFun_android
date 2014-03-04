package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-2-28
 * Time: 下午2:15
 * To change this template use File | Settings | File Templates.
 */
public class Action {

    @Expose
    @DatabaseField
    private Integer actionFromId;
    @Expose
    @DatabaseField
    private String actionFromName;
    @Expose
    @DatabaseField
    private Integer actionToId;
    @Expose
    @DatabaseField
    private String actionToName;
    @Expose
    @DatabaseField
    private Integer actionId;
    @Expose
    @DatabaseField
    private String actionName;
    @Expose
    @DatabaseField
    private Date updateTime;

    public Action() {

    }

    public Integer getActionFromId() {
        return actionFromId;
    }

    public void setActionFromId(Integer actionFromId) {
        this.actionFromId = actionFromId;
    }

    public String getActionFromName() {
        return actionFromName;
    }

    public void setActionFromName(String actionFromName) {
        this.actionFromName = actionFromName;
    }

    public Integer getActionToId() {
        return actionToId;
    }

    public void setActionToId(Integer actionToId) {
        this.actionToId = actionToId;
    }

    public String getActionToName() {
        return actionToName;
    }

    public void setActionToName(String actionToName) {
        this.actionToName = actionToName;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
