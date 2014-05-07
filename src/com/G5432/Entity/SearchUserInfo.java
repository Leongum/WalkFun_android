package com.G5432.Entity;

import com.google.gson.annotations.Expose;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-2-28
 * Time: 下午2:26
 * To change this template use File | Settings | File Templates.
 */
public class SearchUserInfo {
    @Expose
    private Integer userId;
    @Expose
    private String nickName;
    @Expose
    private String sex;
    @Expose
    private Double level;
    @Expose
    private Double fatness;
    @Expose
    private Double power;
    @Expose
    private Double fight;
    @Expose
    private Integer totalFights;
    @Expose
    private Integer fightsWin;
    @Expose
    private Integer totalFriendFights;
    @Expose
    private Integer friendFightWin;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Double getLevel() {
        return level;
    }

    public void setLevel(Double level) {
        this.level = level;
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

    public Double getFight() {
        return fight;
    }

    public void setFight(Double fight) {
        this.fight = fight;
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
}
