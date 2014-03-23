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
    private String userTitle;
    @Expose
    @DatabaseField
    private String userTitlePic;
    @Expose
    @DatabaseField
    private Double fatness;
    @Expose
    @DatabaseField
    private Double power;
    @Expose
    @DatabaseField
    private Double fight;

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
}
