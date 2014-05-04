package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-2-28
 * Time: 下午2:24
 * To change this template use File | Settings | File Templates.
 */
public class FriendSort {

    @Expose
    @DatabaseField(id=true)
    private Integer friendId;
    @Expose
    @DatabaseField
    private String friendName;
    @Expose
    @DatabaseField
    private String sex;
    @Expose
    @DatabaseField
    private Double level;
    @Expose
    @DatabaseField
    private Double fatness;
    @Expose
    @DatabaseField
    private Double power;
    @Expose
    @DatabaseField
    private Double fight;
    @Expose
    @DatabaseField
    private Double fightPlus;
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

    public FriendSort(){

    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
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

    public Double getFightPlus() {
        return fightPlus;
    }

    public void setFightPlus(Double fightPlus) {
        this.fightPlus = fightPlus;
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
