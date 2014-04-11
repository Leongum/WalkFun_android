package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-2-28
 * Time: 下午2:19
 * To change this template use File | Settings | File Templates.
 */
public class ActionDefinition {

    @Expose
    @DatabaseField(id = true)
    private Integer actionId;
    @Expose
    @DatabaseField
    private Integer actionType; //0 for run 1 for use
    @Expose
    @DatabaseField
    private Integer inUsing;
    @Expose
    @DatabaseField
    private String actionName;
    @Expose
    @DatabaseField
    private String actionDescription;
    @Expose
    @DatabaseField
    private String actionAttribute;
    @Expose
    @DatabaseField
    private String actionRule;
    @Expose
    @DatabaseField
    private String effectiveRule;
    @Expose
    @DatabaseField
    private Double triggerProbability;
    @Expose
    @DatabaseField
    private String soundLink;
    @Expose
    @DatabaseField
    private String notificationMessage;
    @Expose
    @DatabaseField
    private Double minLevelLimit;
    @Expose
    @DatabaseField
    private Double maxLevelLimit;
    @Expose
    @DatabaseField
    private Date updateTime;

    public  ActionDefinition(){

    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Integer getInUsing() {
        return inUsing;
    }

    public void setInUsing(Integer inUsing) {
        this.inUsing = inUsing;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public String getActionAttribute() {
        return actionAttribute;
    }

    public void setActionAttribute(String actionAttribute) {
        this.actionAttribute = actionAttribute;
    }

    public String getActionRule() {
        return actionRule;
    }

    public void setActionRule(String actionRule) {
        this.actionRule = actionRule;
    }

    public String getEffectiveRule() {
        return effectiveRule;
    }

    public void setEffectiveRule(String effectiveRule) {
        this.effectiveRule = effectiveRule;
    }

    public Double getTriggerProbability() {
        return triggerProbability;
    }

    public void setTriggerProbability(Double triggerProbability) {
        this.triggerProbability = triggerProbability;
    }

    public String getSoundLink() {
        return soundLink;
    }

    public void setSoundLink(String soundLink) {
        this.soundLink = soundLink;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
