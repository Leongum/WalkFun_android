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
    private String userTitle;
    @Expose
    private String userTitlePic;

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
}
