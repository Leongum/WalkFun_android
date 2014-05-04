package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-10
 * Time: 下午3:23
 * To change this template use File | Settings | File Templates.
 */
public class UserInfo {
    @Expose
    @DatabaseField(id = true)
    private Integer userId;
    @Expose
    @DatabaseField
    private Integer picId;
    @Expose
    @DatabaseField
    private String userTitle;
    @Expose
    @DatabaseField
    private String userTitlePic;
    @Expose
    @DatabaseField
    private Double level;
    @Expose
    @DatabaseField
    private Double goldCoin;
    @Expose
    @DatabaseField
    private Double experience;
    @Expose
    @DatabaseField
    private Double fatness;
    @Expose
    @DatabaseField
    private Double power;
    @Expose
    @DatabaseField
    private Double powerPlus;
    @Expose
    @DatabaseField
    private Double fight;
    @Expose
    @DatabaseField
    private Double fightPlus;
    @Expose
    @DatabaseField
    private Double totalDistance;
    @Expose
    @DatabaseField
    private Double totalCarlorie;
    @Expose
    @DatabaseField
    private Integer totalSteps;
    @Expose
    @DatabaseField
    private Integer totalWalkingTimes;
    @Expose
    @DatabaseField
    private Integer totalActiveTimes;
    @Expose
    @DatabaseField
    private Integer totalFights;
    @Expose
    @DatabaseField
    private Integer fightsWin;
    @Expose
    @DatabaseField
    private Integer totalFriendFights;
    @Expose
    @DatabaseField
    private Integer friendFightWin;
    @Expose
    @DatabaseField
    private Integer missionCombo;
    @DatabaseField
    private String propHaving;
    @Expose
    @DatabaseField
    private Date updateTime;

    public UserInfo() {

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public String getUserTitlePic() {
        return userTitlePic;
    }

    public void setUserTitlePic(String userTitlePic) {
        this.userTitlePic = userTitlePic;
    }

    public Double getLevel() {
        return level;
    }

    public void setLevel(Double level) {
        this.level = level;
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

    public Double getFatness() {
        return fatness;
    }

    public void setFatness(Double fatness) {
        this.fatness = fatness;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Double getPowerPlus() {
        return powerPlus;
    }

    public void setPowerPlus(Double powerPlus) {
        this.powerPlus = powerPlus;
    }

    public Double getFight() {
        return fight;
    }

    public void setFight(Double fight) {
        this.fight = fight;
    }

    public Double getFightPlus() {
        return fightPlus;
    }

    public void setFightPlus(Double fightPlus) {
        this.fightPlus = fightPlus;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Double getTotalCarlorie() {
        return totalCarlorie;
    }

    public void setTotalCarlorie(Double totalCarlorie) {
        this.totalCarlorie = totalCarlorie;
    }

    public Integer getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(Integer totalSteps) {
        this.totalSteps = totalSteps;
    }

    public Integer getTotalWalkingTimes() {
        return totalWalkingTimes;
    }

    public void setTotalWalkingTimes(Integer totalWalkingTimes) {
        this.totalWalkingTimes = totalWalkingTimes;
    }

    public Integer getTotalActiveTimes() {
        return totalActiveTimes;
    }

    public void setTotalActiveTimes(Integer totalActiveTimes) {
        this.totalActiveTimes = totalActiveTimes;
    }

    public Integer getTotalFights() {
        return totalFights;
    }

    public void setTotalFights(Integer totalFights) {
        this.totalFights = totalFights;
    }

    public Integer getFightsWin() {
        return fightsWin;
    }

    public void setFightsWin(Integer fightsWin) {
        this.fightsWin = fightsWin;
    }

    public Integer getTotalFriendFights() {
        return totalFriendFights;
    }

    public void setTotalFriendFights(Integer totalFriendFights) {
        this.totalFriendFights = totalFriendFights;
    }

    public Integer getFriendFightWin() {
        return friendFightWin;
    }

    public void setFriendFightWin(Integer friendFightWin) {
        this.friendFightWin = friendFightWin;
    }

    public Integer getMissionCombo() {
        return missionCombo;
    }

    public void setMissionCombo(Integer missionCombo) {
        this.missionCombo = missionCombo;
    }

    public String getPropHaving() {
        return propHaving;
    }

    public void setPropHaving(String propHaving) {
        this.propHaving = propHaving;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
