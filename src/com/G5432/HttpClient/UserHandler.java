package com.G5432.HttpClient;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.Service.UserService;
import com.G5432.Utils.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-3
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
public class UserHandler {
    private HttpClientHelper httpClientHelper = new HttpClientHelper();

    private UserService userService = null;
    private UserPropHandler propHandler = null;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();

    public UserHandler(DatabaseHelper helper) {
        userService = new UserService(helper);
        propHandler = new UserPropHandler(helper);
    }

    /**
     * 根据用户id同步用户信息
     *
     * @param handler
     * @param userId
     */
    public void syncUserInfo(final Handler handler, Integer userId) {
        if (userId > 0) {
            GlobalSyncStatus.userInfoSynced = false;
            UserBase userBase = userService.fetchUserById(userId);
            String lastUpdateTime = CommonUtil.parseDateToString(userBase.getUserInfo().getUpdateTime());
            String url = CommonUtil.getUrl(MessageFormat.format(Constant.USER_GETINFO_BY_ID_URL, userId, lastUpdateTime));
            httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, String response) {
                    Log.d(this.getClass().getName(), response);
                    GlobalSyncStatus.userInfoSynced = true;
                    if (statusCode == 200 || statusCode == 204) {
                        UserBase userBase = gson.fromJson(response, UserBase.class);
                        UserInfo userInfo = gson.fromJson(response, UserInfo.class);
                        userBase.setUserInfo(userInfo);
                        userService.saveUserInfoToDB(userBase);
                        handler.sendEmptyMessage(1);
                    } else {
                        Log.e(this.getClass().getName(), response);
                        handler.sendEmptyMessage(0);
                    }
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    Log.e(this.getClass().getName(), error.getMessage());
                    GlobalSyncStatus.userInfoSynced = true;
                    handler.sendEmptyMessage(0);
                }
            });
        }
        handler.sendEmptyMessage(0);
    }

    /**
     * 根据用户id获取本地的用户信息，不与服务器同步
     *
     * @param userId
     * @return
     */
    public UserBase fetchUser(Integer userId) {
        return userService.fetchUserById(userId);
    }

    /**
     * 注册用户，根据userBase信息 handler 返回userBase
     *
     * @param userBase
     * @param handler
     */
    public void registerUser(UserBase userBase, final Handler handler) {
        String requestBody = gson.toJson(userBase, UserBase.class);
        String url = CommonUtil.getUrl(Constant.USER_REGISTER_URL);
        httpClientHelper.post(url, null, requestBody, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                Message msg = Message.obtain();
                if (statusCode == 200 || statusCode == 204) {
                    UserBase userBase = gson.fromJson(response, UserBase.class);
                    UserInfo userInfo = gson.fromJson(response, UserInfo.class);
                    userBase.setUserInfo(userInfo);
                    userService.saveUserInfoToDB(userBase);
                    UserUtil.saveUserInfoToList(userBase);
                    msg.obj = userBase;
                    msg.what = 1;
                } else {
                    msg.what = 0;
                    Log.e(this.getClass().getName(), response);
                }
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Log.e(this.getClass().getName(), error.getMessage() + content);
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 用户登录，handler 返回userBase
     *
     * @param userName 用户登录名
     * @param password 用户密码
     * @param handler
     */
    public void syncUserInfoByLogin(String userName, String password, final Handler handler) {
        String url = MessageFormat.format(Constant.USER_LOGIN_URL, userName, CommonUtil.getMD5(password));
        httpClientHelper.get(CommonUtil.getUrl(url), null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                Message msg = Message.obtain();
                if (statusCode == 200 || statusCode == 204) {
                    UserBase userBase = gson.fromJson(response, UserBase.class);
                    UserInfo userInfo = gson.fromJson(response, UserInfo.class);
                    userBase.setUserInfo(userInfo);
                    userService.saveUserInfoToDB(userBase);
                    UserUtil.saveUserInfoToList(userBase);
                    msg.obj = userBase;
                    msg.what = 1;
                } else {
                    msg.what = 0;
                    Log.e(this.getClass().getName(), response);
                }
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Log.e(this.getClass().getName(), error.getMessage() + content);
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 更新用户base信息
     *
     * @param userBase 需要更新的用户信息
     * @param handler
     */
    public void updateUserBase(UserBase userBase, final Handler handler) {
        String requestBody = gson.toJson(userBase, UserBase.class);
        final Integer userId = userBase.getUserId();
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.USER_BASE_UPDATE_URL, userId));
        httpClientHelper.post(url, null, requestBody, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                if (statusCode == 200 || statusCode == 204) {
                    syncUserInfo(handler, userId);
                } else {
                    Log.e(this.getClass().getName(), response);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Log.e(this.getClass().getName(), error.getMessage() + content);
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 获取随机奖励
     *
     * @param userId  用户id
     * @param handler
     */
    public void getRandomReward(final Integer userId, final Handler handler) {
        String url = MessageFormat.format(Constant.REWARD_GET_RANDOM_URL, userId);
        httpClientHelper.get(CommonUtil.getUrl(url), null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                Message msg = Message.obtain();
                if (statusCode == 200 || statusCode == 204) {
                    RewardDetails rewardDetails = gson.fromJson(response, RewardDetails.class);
                    syncUserInfo(new Handler(), userId);
                    propHandler.syncUserProps(new Handler());
                    msg.obj = rewardDetails;
                    msg.what = 1;
                } else {
                    msg.what = 0;
                    Log.e(this.getClass().getName(), response);
                }
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Log.e(this.getClass().getName(), error.getMessage() + content);
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 根据用户昵称查询用户基本信息
     * @param userName 用户昵称
     * @param handler
     */
    public void searchFriend(String userName, final Handler handler) {
        String url = MessageFormat.format(Constant.FRIEND_SEARCH_URL, userName);
        httpClientHelper.get(CommonUtil.getUrl(url), null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                Message msg = Message.obtain();
                if (statusCode == 200 || statusCode == 204) {
                    List<SearchUserInfo> searchUserInfoList = gson.fromJson(response, new TypeToken<List<SearchUserInfo>>() {
                    }.getType());
                    msg.obj = searchUserInfoList;
                    msg.what = 1;
                } else {
                    msg.what = 0;
                    Log.e(this.getClass().getName(), response);
                }
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Log.e(this.getClass().getName(), error.getMessage() + content);
                handler.sendEmptyMessage(0);
            }
        });
    }

}
