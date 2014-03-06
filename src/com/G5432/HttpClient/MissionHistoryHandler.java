package com.G5432.HttpClient;

import android.os.Handler;
import android.util.Log;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.MissionHistory;
import com.G5432.Service.MissionHistoryService;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.Constant;
import com.G5432.Utils.GlobalSyncStatus;
import com.G5432.Utils.UserUtil;
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
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
public class MissionHistoryHandler {

    private HttpClientHelper httpClientHelper = new HttpClientHelper();

    private MissionHistoryService missionHistoryService = null;
    private UserHandler userHandler = null;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();

    public MissionHistoryHandler(DatabaseHelper helper) {
        missionHistoryService = new MissionHistoryService(helper);
        userHandler = new UserHandler(helper);
    }

    /**
     * 同步服务器mission history 信息
     *
     * @param handler
     */
    public void syncMissionHistories(final Handler handler) {
        GlobalSyncStatus.missionHistorySynced = false;
        String lastUpdateTime = UserUtil.getLastUpdateTime("MissionHistoryUpdateTime");
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.HISTORY_GET_MISSION_HISTORY_URL, UserUtil.getUserId(), lastUpdateTime));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.i(this.getClass().getName(), response);
                GlobalSyncStatus.missionHistorySynced = true;
                if (statusCode == 200 || statusCode == 204) {
                    List<MissionHistory> missionHistoryList = gson.fromJson(response, new TypeToken<List<MissionHistory>>() {
                    }.getType());
                    missionHistoryService.saveMissionHistoryToDB(missionHistoryList);
                    UserUtil.saveLastUpdateTime("MissionHistoryUpdateTime");
                    handler.sendEmptyMessage(1);
                } else {
                    Log.e(this.getClass().getName(), response);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                GlobalSyncStatus.missionHistorySynced = true;
                Log.e(this.getClass().getName(), error.getMessage());
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 同步本地未上传的mission info
     *
     * @param handler
     */
    public void uploadMissionHistories(final Handler handler) {
        final Integer userId = UserUtil.getUserId();
        List<MissionHistory> missionHistoryList = missionHistoryService.fetchMissionHistoryByUserId(userId);
        if (missionHistoryList != null && missionHistoryList.size() > 0) {
            String url = CommonUtil.getUrl(MessageFormat.format(Constant.HISTORY_POST_MISSION_HISTORY_URL, userId));
            httpClientHelper.post(url, null, gson.toJson(missionHistoryList), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, String response) {
                    if (statusCode == 200 || statusCode == 204) {
                        missionHistoryService.updateUnsyncedMissionHistories(UserUtil.getUserId());
                    } else {
                        Log.e(this.getClass().getName(), response);
                    }
                    userHandler.syncUserInfo(new Handler(), userId);
                    handler.sendEmptyMessage(1);
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
     * 根据mission uuid 获取一条mission history
     *
     * @param runUuid
     * @return
     */
    public MissionHistory fetchMissionHistoryByUuid(String runUuid) {
        return missionHistoryService.fetchMissionHistoryByUuid(runUuid);
    }

    /**
     * 根据user id 获取本用户的所有mission记录
     *
     * @param userId 用户id
     * @return 历史记录
     */
    public List<MissionHistory> fetchMissionHistoryByUserId(Integer userId) {
        return missionHistoryService.fetchMissionHistoryByUserId(userId);
    }

    /**
     * 本地新创建的 mission history 保存到本地
     *
     * @param missionHistory
     */
    public void saveMissionHistoryToDB(MissionHistory missionHistory) {
        missionHistoryService.saveMissionHistoryToDB(missionHistory);
    }
}
