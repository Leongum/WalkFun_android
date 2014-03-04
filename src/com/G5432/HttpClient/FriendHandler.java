package com.G5432.HttpClient;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.Entity.Enum.FollowStatusEnum;
import com.G5432.Service.FriendService;
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
 * Date: 14-3-4
 * Time: 下午2:38
 * To change this template use File | Settings | File Templates.
 */
public class FriendHandler {
    private HttpClientHelper httpClientHelper = new HttpClientHelper();

    private FriendService friendService = null;
    private UserHandler userHandler = null;
    private UserPropHandler userPropHandler = null;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();

    public FriendHandler(DatabaseHelper helper) {
        friendService = new FriendService(helper);
        userHandler = new UserHandler(helper);
        userPropHandler = new UserPropHandler(helper);
    }

    /**
     * 同步用户好友信息
     *
     * @param handler
     */
    public void syncFriends(final Handler handler) {
        GlobalSyncStatus.userFriendSynced = false;
        String lastUpdateTime = UserUtil.getLastUpdateTime("FriendsUpdateTime");
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.FRIEND_GET_URL, UserUtil.getUserId(), lastUpdateTime));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                GlobalSyncStatus.userFriendSynced = true;
                if (statusCode == 200 || statusCode == 204) {
                    List<UserFriend> userFriendList = gson.fromJson(response, new TypeToken<List<UserFriend>>() {
                    }.getType());
                    friendService.saveFriendsToDB(userFriendList);
                    UserUtil.saveLastUpdateTime("FriendsUpdateTime");
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = userFriendList.size();
                    handler.sendMessage(msg);
                } else {
                    Log.e(this.getClass().getName(), response);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                GlobalSyncStatus.userFriendSynced = true;
                Log.e(this.getClass().getName(), error.getMessage());
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 同步好友sort 信息
     *
     * @param handler
     */
    public void syncFriendSort(final Handler handler) {
        GlobalSyncStatus.userFriendSortSynced = false;
        String lastUpdateTime = UserUtil.getLastUpdateTime("FriendSortUpdateTime");
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.FRIEND_SORT_UPDATE_URL, UserUtil.getUserId(), lastUpdateTime));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                GlobalSyncStatus.userFriendSortSynced = true;
                if (statusCode == 200 || statusCode == 204) {
                    List<FriendSort> friendSortList = gson.fromJson(response, new TypeToken<List<FriendSort>>() {
                    }.getType());
                    friendService.saveFriendSortsToDB(friendSortList);
                    UserUtil.saveLastUpdateTime("FriendSortUpdateTime");
                    handler.sendEmptyMessage(1);
                } else {
                    Log.e(this.getClass().getName(), response);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                GlobalSyncStatus.userFriendSortSynced = true;
                Log.e(this.getClass().getName(), error.getMessage());
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 获得我的粉丝按照修改时间修改顺序排序
     *
     * @param userId
     * @return
     */
    public List<UserFriend> fetchFriendFansList(Integer userId) {
        return friendService.fetchFriendFansList(userId);
    }

    /**
     * 获得我关注的列表按照时间修改顺序
     *
     * @param userId
     * @return
     */
    public List<UserFriend> fetchFriendFollowsList(Integer userId) {
        return friendService.fetchFriendFollowsList(userId);
    }

    /**
     * 获得与我互相关注的好友
     *
     * @param userId
     * @return
     */
    public List<UserFriend> fetchFriendEachFansList(Integer userId) {
        return friendService.fetchFriendEachFansList(userId);
    }

    /**
     * 同步好友 action 信息
     *
     * @param handler
     */
    public void syncActions(final Handler handler) {
        GlobalSyncStatus.userActionSynced = false;
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.ACTION_GET_ACTION_URL, UserUtil.getUserId()));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                GlobalSyncStatus.userActionSynced = true;
                if (statusCode == 200 || statusCode == 204) {
                    List<Action> actionList = gson.fromJson(response, new TypeToken<List<Action>>() {
                    }.getType());
                    friendService.saveActionsToDB(actionList);
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = actionList.size();
                    handler.sendMessage(msg);
                } else {
                    Log.e(this.getClass().getName(), response);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                GlobalSyncStatus.userActionSynced = true;
                Log.e(this.getClass().getName(), error.getMessage());
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 创建新的动作，必须同步完成，如果服务器同步失败，则失败
     *
     * @param actionId         action define id
     * @param actionToId       action to user id
     * @param actionToUserName action to user name
     * @param handler
     */
    public void createAction(Integer actionId, Integer actionToId, String actionToUserName, final Handler handler) {
        final Integer userId = UserUtil.getUserId();
        Action action = new Action();
        action.setActionFromId(userId);
        action.setActionFromName(UserUtil.getUserName());
        action.setActionId(actionId);
        action.setActionToId(actionToId);
        action.setActionToName(actionToUserName);

        String requestBody = gson.toJson(action, Action.class);
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.ACTION_CREATE_ACTION_URL, userId));
        httpClientHelper.post(url, null, requestBody, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                if (statusCode == 200 || statusCode == 204) {
                    syncActions(new Handler());
                    userHandler.syncUserInfo(new Handler(), userId);
                    userPropHandler.syncUserProps(new Handler());
                    handler.sendEmptyMessageDelayed(1, 1000);
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
     * 获取某个用户最新的3条 action 记录
     *
     * @param userId
     * @param handler
     */
    public void fetchUserLatestActions(Integer userId, final Handler handler) {
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.ACTION_GET_ACTION_BY_USER_ID_URL, userId));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                if (statusCode == 200 || statusCode == 204) {
                    List<Action> actionList = gson.fromJson(response, new TypeToken<List<Action>>() {
                    }.getType());
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = actionList;
                    handler.sendMessage(msg);
                } else {
                    Log.e(this.getClass().getName(), response);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Log.e(this.getClass().getName(), error.getMessage());
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 获得当前用户的本地action 信息
     *
     * @param userId
     * @return
     */
    public List<Action> fetchUserActions(Integer userId) {
        return friendService.fetchUserAction(userId);
    }

    /**
     * 根据page no获取当前推荐用户列表
     *
     * @param pageNo
     * @param handler
     */
    public void fetchRecommendFriends(Integer pageNo, final Handler handler) {
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.FRIEND_RECOMMEND_URL, pageNo));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                if (statusCode == 200 || statusCode == 204) {
                    List<SearchUserInfo> searchUserInfoList = gson.fromJson(response, new TypeToken<List<SearchUserInfo>>() {
                    }.getType());
                    Integer me = UserUtil.getUserId();
                    for (SearchUserInfo searchUserInfo : searchUserInfoList) {
                        if (me == searchUserInfo.getUserId()) {
                            searchUserInfoList.remove(searchUserInfo);
                        }
                    }
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = searchUserInfoList;
                    handler.sendMessage(msg);
                } else {
                    Log.e(this.getClass().getName(), response);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Log.e(this.getClass().getName(), error.getMessage());
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 根据好友的id判定与好友的关注关系
     *
     * @param friendId
     * @return
     */
    public FollowStatusEnum getFollowStatus(Integer friendId) {
        UserFriend userFriend = friendService.fetchFriendByIds(UserUtil.getUserId(), friendId);

        if (userFriend != null || userFriend.getFriendStatus() == FollowStatusEnum.DELETED.ordinal()) {
            return FollowStatusEnum.DELETED;
        }
        return FollowStatusEnum.FOLLOWED;
    }

    /**
     * 创建关注
     *
     * @param friendId
     * @param handler
     */
    public void followFriend(Integer friendId, final Handler handler) {
        final Integer userId = UserUtil.getUserId();
        if (userId == friendId) handler.sendEmptyMessage(0);
        UserFriend userFriend = friendService.fetchFriendByIds(UserUtil.getUserId(), friendId);
        if (userFriend != null) {
            userFriend.setFriendStatus(FollowStatusEnum.FOLLOWED.ordinal());
        } else {
            userFriend = new UserFriend();
            userFriend.setUserId(userId);
            userFriend.setFriendId(friendId);
            userFriend.setFriendStatus(FollowStatusEnum.FOLLOWED.ordinal());
        }
        doUpdateFriendStatus(userFriend, handler);
    }

    /**
     * 取消关注
     *
     * @param friendId
     * @param handler
     */
    public void deFollowFriend(Integer friendId, final Handler handler) {
        final Integer userId = UserUtil.getUserId();
        if (userId == friendId) handler.sendEmptyMessage(0);
        UserFriend userFriend = friendService.fetchFriendByIds(UserUtil.getUserId(), friendId);
        if (userFriend != null) {
            userFriend.setFriendStatus(FollowStatusEnum.DELETED.ordinal());
        }
        doUpdateFriendStatus(userFriend, handler);
    }

    /**
     * 关注更新
     *
     * @param userFriend
     * @param handler
     */
    public void doUpdateFriendStatus(UserFriend userFriend, final Handler handler) {
        final Integer userId = userFriend.getUserId();
        String requestBody = gson.toJson(userFriend, UserFriend.class);
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.FRIEND_CREATE_OR_UPDATE_URL, userId));
        httpClientHelper.post(url, null, requestBody, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                if (statusCode == 200 || statusCode == 204) {
                    syncActions(new Handler());
                    userHandler.syncUserInfo(new Handler(), userId);
                    syncFriendSort(new Handler());
                    handler.sendEmptyMessageDelayed(1, 1000);
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
}
