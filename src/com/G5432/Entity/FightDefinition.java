package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-23
 * Time: 下午2:32
 * To change this template use File | Settings | File Templates.
 */
public class FightDefinition {

    @Expose
    @DatabaseField(id = true)
    private Integer id;
    @Expose
    @DatabaseField
    private Integer inUsing;
    @Expose
    @DatabaseField
    private String fName;
    @Expose
    @DatabaseField
    private Double minLimit;
    @Expose
    @DatabaseField
    private Double maxLimit;
    @Expose
    @DatabaseField
    private String mName;
    @Expose
    @DatabaseField
    private Integer mLevel;
    @Expose
    @DatabaseField
    private Double mMaxFight;
    @Expose
    @DatabaseField
    private Double mMinFight;
    @Expose
    @DatabaseField
    private Double bPower;
    @Expose
    @DatabaseField
    private Double bGold;
    @Expose
    @DatabaseField
    private Double bExperience;
    @Expose
    @DatabaseField
    private String fWin;
    @Expose
    @DatabaseField
    private String winGot;
    @Expose
    @DatabaseField
    private String winRule;
    @Expose
    @DatabaseField
    private String fLoose;
    @Expose
    @DatabaseField
    private String tProb;
    @Expose
    @DatabaseField
    private Date updateTime;

    public  FightDefinition(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInUsing() {
        return inUsing;
    }

    public void setInUsing(Integer inUsing) {
        this.inUsing = inUsing;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public Double getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(Double minLimit) {
        this.minLimit = minLimit;
    }

    public Double getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(Double maxLimit) {
        this.maxLimit = maxLimit;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Integer getmLevel() {
        return mLevel;
    }

    public void setmLevel(Integer mLevel) {
        this.mLevel = mLevel;
    }

    public Double getmMaxFight() {
        return mMaxFight;
    }

    public void setmMaxFight(Double mMaxFight) {
        this.mMaxFight = mMaxFight;
    }

    public Double getmMinFight() {
        return mMinFight;
    }

    public void setmMinFight(Double mMinFight) {
        this.mMinFight = mMinFight;
    }

    public Double getbPower() {
        return bPower;
    }

    public void setbPower(Double bPower) {
        this.bPower = bPower;
    }

    public Double getbGold() {
        return bGold;
    }

    public void setbGold(Double bGold) {
        this.bGold = bGold;
    }

    public Double getbExperience() {
        return bExperience;
    }

    public void setbExperience(Double bExperience) {
        this.bExperience = bExperience;
    }

    public String getfWin() {
        return fWin;
    }

    public void setfWin(String fWin) {
        this.fWin = fWin;
    }

    public String getWinGot() {
        return winGot;
    }

    public void setWinGot(String winGot) {
        this.winGot = winGot;
    }

    public String getWinRule() {
        return winRule;
    }

    public void setWinRule(String winRule) {
        this.winRule = winRule;
    }

    public String getfLoose() {
        return fLoose;
    }

    public void setfLoose(String fLoose) {
        this.fLoose = fLoose;
    }

    public String gettProb() {
        return tProb;
    }

    public void settProb(String tProb) {
        this.tProb = tProb;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
