package com.G5432.HttpClient;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.Service.RunningHistoryService;
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
 * Time: 上午11:21
 * To change this template use File | Settings | File Templates.
 */
public class RunningHistoryHandler {
    private HttpClientHelper httpClientHelper = new HttpClientHelper();

    private RunningHistoryService runningHistoryService = null;
    private UserHandler userHandler = null;
    private UserPropHandler userPropHandler = null;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();

    public RunningHistoryHandler(DatabaseHelper helper) {
        runningHistoryService = new RunningHistoryService(helper);
        userHandler = new UserHandler(helper);
        userPropHandler = new UserPropHandler(helper);
    }

    /**
     * 同步服务器running history 信息
     *
     * @param handler
     */
    public void syncRunningHistories(final Handler handler) {
        GlobalSyncStatus.historySynced = false;
        String lastUpdateTime = UserUtil.getLastUpdateTime("RunningHistoryUpdateTime");
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.HISTORY_GET_RUNNING_HISTORY_URL, UserUtil.getUserId(), lastUpdateTime));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                GlobalSyncStatus.historySynced = true;
                if (statusCode == 200 || statusCode == 204) {
                    List<RunningHistory> runningHistoryList = gson.fromJson(response, new TypeToken<List<RunningHistory>>() {
                    }.getType());
                    runningHistoryService.saveRunListToDB(runningHistoryList);
                    UserUtil.saveLastUpdateTime("RunningHistoryUpdateTime");
                    handler.sendEmptyMessage(1);
                } else {
                    Log.e(this.getClass().getName(), response);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                GlobalSyncStatus.historySynced = true;
                Log.e(this.getClass().getName(), error.getMessage());
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 同步本地未上传的history info
     *
     * @param handler
     */
    public void uploadRunningHistories(final Handler handler) {
        final Integer userId = UserUtil.getUserId();
        List<RunningHistory> runningHistoryList = runningHistoryService.fetchUnsyncedRunHistory(userId);
        if (runningHistoryList != null && runningHistoryList.size() > 0) {
            String url = CommonUtil.getUrl(MessageFormat.format(Constant.HISTORY_POST_RUNNING_HISTORY_URL, userId));
            httpClientHelper.post(url, null, gson.toJson(runningHistoryList), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, String response) {
                    Log.d(this.getClass().getName(), response);
                    if (statusCode == 200 || statusCode == 204) {
                        runningHistoryService.updateUnsyncedRunHistories(UserUtil.getUserId());
                    } else {
                        Log.e(this.getClass().getName(), response);
                    }
                    userHandler.syncUserInfo(new Handler(), userId);
                    userPropHandler.syncUserProps(new Handler());
                    handler.sendEmptyMessageDelayed(1, 1000);
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    Log.e(this.getClass().getName(), error.getMessage());
                    handler.sendEmptyMessage(0);
                }
            });
        }
        handler.sendEmptyMessage(1);
    }

    /**
     * 根据run uuid 获取一条run history
     *
     * @param runUuid
     * @return
     */
    public RunningHistory fetchRunHistoryByRunId(String runUuid) {
        return runningHistoryService.fetchRunHistoryByRunId(runUuid);
    }

    /**
     * 根据user id 获取本用户的所有跑步记录
     *
     * @param userId 用户id
     * @return 历史记录
     */
    public List<RunningHistory> fetchRunHistoryByUserId(Integer userId) {
        return runningHistoryService.fetchRunHistoryByUserId(userId);
    }

    /**
     * 本地新创建的run history 保存到本地
     *
     * @param runningHistory
     */
    public void saveRunHistoryToDB(RunningHistory runningHistory) {
        runningHistoryService.saveRunListToDB(runningHistory);
    }

    /**
     * 根据missionUuid 返回对应的run history
     *
     * @param missionUuid
     * @return run history
     */
    public RunningHistory fetchRunHistoryByMissionUuid(String missionUuid) {
        return runningHistoryService.fetchRunHistoryByMissionUuid(missionUuid);
    }

    /**
     * 根据user id 获取用户最新3条跑步记录
     *
     * @param userId
     * @param handler
     */
    public void getSimpleRunningHistories(Integer userId, final Handler handler) {
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.HISTORY_GET_SIMPLE_RUNNING_HISTORY_URL, userId));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                GlobalSyncStatus.historySynced = true;
                Message msg = Message.obtain();
                if (statusCode == 200 || statusCode == 204) {
                    List<SimpleRunningHistory> runningHistoryList = gson.fromJson(response, new TypeToken<List<SimpleRunningHistory>>() {
                    }.getType());
                    msg.obj = runningHistoryList;
                    msg.what = 1;
                    handler.sendMessage(msg);
                } else {
                    Log.e(this.getClass().getName(), response);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                GlobalSyncStatus.historySynced = true;
                Log.e(this.getClass().getName(), error.getMessage());
                handler.sendEmptyMessage(0);
            }
        });
    }


}
