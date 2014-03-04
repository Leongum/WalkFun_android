package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-14
 * Time: 下午9:00
 * To change this template use File | Settings | File Templates.
 */
public class RunningHistory {

    @Expose
    @DatabaseField
    private Integer userId;
    @Expose
    @DatabaseField(id = true)
    private String runUuid;
    @Expose
    @DatabaseField
    private Integer missionId;
    @Expose
    @DatabaseField
    private Integer missionTypeId;
    @Expose
    @DatabaseField
    private String missionRoute;
    @Expose
    @DatabaseField
    private Date missionStartTime;
    @Expose
    @DatabaseField
    private Date missionEndTime;
    @Expose
    @DatabaseField
    private Date missionDate;
    @Expose
    @DatabaseField
    private double spendCarlorie;
    @Expose
    @DatabaseField
    private Integer duration;
    @Expose
    @DatabaseField
    private double avgSpeed;
    @Expose
    @DatabaseField
    private Integer steps;
    @Expose
    @DatabaseField
    private double distance;
    @Expose
    @DatabaseField
    private Integer missionGrade;
    @Expose
    @DatabaseField
    private double goldCoin;
    @Expose
    @DatabaseField
    private double extraGoldCoin;
    @Expose
    @DatabaseField
    private double experience;
    @Expose
    @DatabaseField
    private double extraExperience;
    @Expose
    @DatabaseField
    private double fatness;
    @Expose
    @DatabaseField
    private double health;
    @Expose
    @DatabaseField
    private String comment;
    @Expose
    @DatabaseField
    private Integer valid;
    @Expose
    @DatabaseField
    private String missionUuid;
    @Expose
    @DatabaseField
    private Integer sequence;
    @Expose
    @DatabaseField
    private String propGet;
    @Expose
    @DatabaseField
    private String actionIds;
    @Expose
    @DatabaseField
    private Date commitTime;

    public RunningHistory() {

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRunUuid() {
        return runUuid;
    }

    public void setRunUuid(String runUuid) {
        this.runUuid = runUuid;
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

    public String getMissionRoute() {
        return missionRoute;
    }

    public void setMissionRoute(String missionRoute) {
        this.missionRoute = missionRoute;
    }

    public Date getMissionStartTime() {
        return missionStartTime;
    }

    public void setMissionStartTime(Date missionStartTime) {
        this.missionStartTime = missionStartTime;
    }

    public Date getMissionEndTime() {
        return missionEndTime;
    }

    public void setMissionEndTime(Date missionEndTime) {
        this.missionEndTime = missionEndTime;
    }

    public Date getMissionDate() {
        return missionDate;
    }

    public void setMissionDate(Date missionDate) {
        this.missionDate = missionDate;
    }

    public double getSpendCarlorie() {
        return spendCarlorie;
    }

    public void setSpendCarlorie(double spendCarlorie) {
        this.spendCarlorie = spendCarlorie;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Integer getMissionGrade() {
        return missionGrade;
    }

    public void setMissionGrade(Integer missionGrade) {
        this.missionGrade = missionGrade;
    }

    public double getGoldCoin() {
        return goldCoin;
    }

    public void setGoldCoin(double goldCoin) {
        this.goldCoin = goldCoin;
    }

    public double getExtraGoldCoin() {
        return extraGoldCoin;
    }

    public void setExtraGoldCoin(double extraGoldCoin) {
        this.extraGoldCoin = extraGoldCoin;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

    public double getExtraExperience() {
        return extraExperience;
    }

    public void setExtraExperience(double extraExperience) {
        this.extraExperience = extraExperience;
    }

    public double getFatness() {
        return fatness;
    }

    public void setFatness(double fatness) {
        this.fatness = fatness;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getMissionUuid() {
        return missionUuid;
    }

    public void setMissionUuid(String missionUuid) {
        this.missionUuid = missionUuid;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getPropGet() {
        return propGet;
    }

    public void setPropGet(String propGet) {
        this.propGet = propGet;
    }

    public String getActionIds() {
        return actionIds;
    }

    public void setActionIds(String actionIds) {
        this.actionIds = actionIds;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }
}
