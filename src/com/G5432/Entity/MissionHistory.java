package com.G5432.Entity;

import com.google.gson.annotations.Expose;
import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-2-28
 * Time: 下午2:35
 * To change this template use File | Settings | File Templates.
 */
public class MissionHistory {

    @Expose
    @DatabaseField(id = true)
    private String missionUuid;
    @Expose
    @DatabaseField
    private Integer userId;
    @Expose
    @DatabaseField
    private String userName;
    @Expose
    @DatabaseField
    private Integer missionId;
    @Expose
    @DatabaseField
    private Integer missionTypeId;
    @Expose
    @DatabaseField
    private String missionName;
    @Expose
    @DatabaseField
    private Date startTime;
    @Expose
    @DatabaseField
    private Date endTime;
    @Expose
    @DatabaseField
    private Integer missionStatus; //0 success 1 failed
    @Expose
    @DatabaseField
    private String missionStatusComment;
    @Expose
    @DatabaseField
    private Date updateTime;

    public MissionHistory(){

    }

    public String getMissionUuid() {
        return missionUuid;
    }

    public void setMissionUuid(String missionUuid) {
        this.missionUuid = missionUuid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getMissionStatus() {
        return missionStatus;
    }

    public void setMissionStatus(Integer missionStatus) {
        this.missionStatus = missionStatus;
    }

    public String getMissionStatusComment() {
        return missionStatusComment;
    }

    public void setMissionStatusComment(String missionStatusComment) {
        this.missionStatusComment = missionStatusComment;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
