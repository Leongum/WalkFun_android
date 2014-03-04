package com.G5432.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-2-28
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
public class RewardDetails {
    private Integer userId;

    private Integer actionId;

    private Integer rewardMoney;

    private Integer rewardPropId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public Integer getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(Integer rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    public Integer getRewardPropId() {
        return rewardPropId;
    }

    public void setRewardPropId(Integer rewardPropId) {
        this.rewardPropId = rewardPropId;
    }
}
