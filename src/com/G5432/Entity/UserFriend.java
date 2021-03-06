package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-2-28
 * Time: 下午2:21
 * To change this template use File | Settings | File Templates.
 */
public class UserFriend {

    @DatabaseField(id = true, useGetSet = true)
    private String genId;
    @Expose
    @DatabaseField
    private Integer userId;
    @Expose
    @DatabaseField
    private Integer friendId;
    @Expose
    @DatabaseField
    private Integer friendStatus; //'0 - 关注 1 - 删除',
    @Expose
    @DatabaseField
    private Integer friendEach;   //'0 - 仅关注 1 - 相互关注',
    @Expose
    @DatabaseField
    private Date addTime;
    @Expose
    @DatabaseField
    private Date updateTime;
    @Expose
    @DatabaseField
    private Date lastWalkTime;

    private String sex;

    private String userName;

    private Double level;

    private String userTitle;

    private Double fight;
    private Double fightPlus;
    private Double power;
    private Integer totalFights;
    private Integer fightsWin;
    private Integer totalFriendFights;
    private Integer friendFightWin;

    public UserFriend() {

    }

    public String getGenId() {
        return userId + "-" + friendId;
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

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Integer getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(Integer friendStatus) {
        this.friendStatus = friendStatus;
    }

    public Integer getFriendEach() {
        return friendEach;
    }

    public void setFriendEach(Integer friendEach) {
        this.friendEach = friendEach;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getLevel() {
        return level;
    }

    public void setLevel(Double level) {
        this.level = level;
    }

    public String getUserTitle() {
        return userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
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

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
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

    public Date getLastWalkTime() {
        return lastWalkTime;
    }

    public void setLastWalkTime(Date lastWalkTime) {
        this.lastWalkTime = lastWalkTime;
    }
}
