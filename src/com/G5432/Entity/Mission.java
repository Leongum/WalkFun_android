package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-14
 * Time: 下午8:45
 * To change this template use File | Settings | File Templates.
 */
public class Mission {
    @Expose
    @DatabaseField(id = true)
    private Integer missionId;
    @Expose
    @DatabaseField
    private Integer missionTypeId; //0 for steps times limit 1 for prop drop limit 2 for prop use for user
    @Expose
    @DatabaseField
    private String missionName;
    @Expose
    @DatabaseField
    private String missionRule;
    @Expose
    @DatabaseField
    private String missionDescription;
    @Expose
    @DatabaseField
    private Integer triggerSteps;
    @Expose
    @DatabaseField
    private Integer triggerTimes;
    @Expose
    @DatabaseField
    private Double triggerDistances;
    @Expose
    @DatabaseField
    private Integer triggerPropId;
    @Expose
    @DatabaseField
    private Integer triggerPropNumbers;
    @Expose
    @DatabaseField
    private Integer triggerUserNumbers;
    @Expose
    @DatabaseField
    private Double goldCoin;
    @Expose
    @DatabaseField
    private Double experience;
    @Expose
    @DatabaseField
    private Date updateTime;


    public Mission() {

    }

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public Integer getMissionTypeId() {
        return missionTypeId;
    }

    public void setMissionTypeId(Integer missionTypeId) {
        this.missionTypeId = missionTypeId;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }

    public String getMissionRule() {
        return missionRule;
    }

    public void setMissionRule(String missionRule) {
        this.missionRule = missionRule;
    }

    public String getMissionDescription() {
        return missionDescription;
    }

    public void setMissionDescription(String missionDescription) {
        this.missionDescription = missionDescription;
    }

    public Integer getTriggerSteps() {
        return triggerSteps;
    }

    public void setTriggerSteps(Integer triggerSteps) {
        this.triggerSteps = triggerSteps;
    }

    public Integer getTriggerTimes() {
        return triggerTimes;
    }

    public void setTriggerTimes(Integer triggerTimes) {
        this.triggerTimes = triggerTimes;
    }

    public Double getTriggerDistances() {
        return triggerDistances;
    }

    public void setTriggerDistances(Double triggerDistances) {
        this.triggerDistances = triggerDistances;
    }

    public Integer getTriggerPropId() {
        return triggerPropId;
    }

    public void setTriggerPropId(Integer triggerPropId) {
        this.triggerPropId = triggerPropId;
    }

    public Integer getTriggerPropNumbers() {
        return triggerPropNumbers;
    }

    public void setTriggerPropNumbers(Integer triggerPropNumbers) {
        this.triggerPropNumbers = triggerPropNumbers;
    }

    public Integer getTriggerUserNumbers() {
        return triggerUserNumbers;
    }

    public void setTriggerUserNumbers(Integer triggerUserNumbers) {
        this.triggerUserNumbers = triggerUserNumbers;
    }

    public Double getGoldCoin() {
        return goldCoin;
    }

    public void setGoldCoin(Double goldCoin) {
        this.goldCoin = goldCoin;
    }

    public Double getExperience() {
        return experience;
    }

    public void setExperience(Double experience) {
        this.experience = experience;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
