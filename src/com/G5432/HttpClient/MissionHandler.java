package com.G5432.HttpClient;

import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.Service.MissionService;
import com.G5432.Utils.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-3
 * Time: 上午11:24
 * To change this template use File | Settings | File Templates.
 */
public class MissionHandler {
    private HttpClientHelper httpClientHelper = new HttpClientHelper();

    private MissionService missionService = null;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();

    public MissionHandler(DatabaseHelper helper) {
        missionService = new MissionService(helper);
    }

    /**
     * 同步服务器missions 返回empty message handler
     *
     * @param handler
     */
    public void syncMissions(final Handler handler) {
        GlobalSyncStatus.missionSynced = false;
        String lastUpdateTime = UserUtil.getLastUpdateTime("MissionUpdateTime");
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.MISSION_GET_URL, lastUpdateTime));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                GlobalSyncStatus.missionSynced = true;
                if (statusCode == 200 || statusCode == 204) {
                    List<Mission> missionList = gson.fromJson(response, new TypeToken<List<Mission>>() {
                    }.getType());
                    missionService.saveMissionToDB(missionList);
                    UserUtil.saveLastUpdateTime("MissionUpdateTime");
                    handler.sendEmptyMessage(1);
                } else {
                    Log.e(this.getClass().getName(), response);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                GlobalSyncStatus.missionSynced = true;
                Log.e(this.getClass().getName(), error.getMessage());
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 获取当天的daily mission 返回包含mission的message handler
     *
     * @param handler
     */
    public void fetchDailyMission(final Handler handler) {
        Date fetchDay = CommonUtil.parseDate(UserUtil.getLastUpdateTime("DailyMissionGetTime"));
        Boolean b = DateUtils.isToday(fetchDay.getTime());
        if (b) {
            Integer missionId = UserUtil.getDailyMissionId();
            if (missionId > 0) {
                Message msg = Message.obtain();
                msg.obj = missionService.fetchMissionByMissionId(missionId);
                msg.what = 1;
                handler.sendMessage(msg);
            } else {
                b = false;
            }

        }
        if (!b) {
            String url = CommonUtil.getUrl(MessageFormat.format(Constant.MISSION_DAILY_GET_URL, UserUtil.getUserId()));
            httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, String response) {
                    Log.d(this.getClass().getName(), response);
                    Message msg = Message.obtain();
                    if (statusCode == 200 || statusCode == 204) {
                        Mission mission = gson.fromJson(response, Mission.class);
                        missionService.saveMissionToDB(mission);
                        msg.obj = mission;
                        msg.what = 1;
                    } else {
                        Log.e(this.getClass().getName(), response);
                        msg.what = 0;
                    }
                    handler.sendMessage(msg);
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    Log.e(this.getClass().getName(), error.getMessage());
                    handler.sendEmptyMessage(0);
                }
            });
        }
    }

}
