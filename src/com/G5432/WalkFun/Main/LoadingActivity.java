package com.G5432.WalkFun.Main;

import android.content.Intent;
import android.os.*;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.HttpClient.*;
import com.G5432.Utils.*;
import com.G5432.WalkFun.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.io.File;
import java.util.Date;


/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-1
 * Time: 下午3:42
 * To change this template use File | Settings | File Templates.
 */
public class LoadingActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private SystemHandler systemHandler = null;
    private UserHandler userHandler = null;

    private MissionHandler missionHandler = null;

    private VirtualProductHandler virtualProductHandler = null;

    //flag to make sure only jump once
    private Boolean needJump = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        UserUtil.sharedPreferences = getSharedPreferences("UserBaseInfo", MODE_APPEND);
        UserUtil.weatherPreferences = getSharedPreferences("WeatherInfo",MODE_APPEND);
        systemHandler = new SystemHandler(getHelper());
        missionHandler = new MissionHandler(getHelper());
        virtualProductHandler = new VirtualProductHandler(getHelper());
        userHandler = new UserHandler(getHelper());
        //do sync data
        systemHandler.syncVersion(versionHandler);

    }

    Handler versionHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                VersionControl version = (VersionControl) msg.obj;
                UserUtil.saveSystemTime(version.getSystemTime());
                //time from service
                Date missionLastUpdateTime = version.getMissionLastUpdateTime();
                Date fightDefineUpdateTime = version.getFightDefineUpdateTime();
                Date recommendLastUpdateTime = version.getRecommendLastUpdateTime();
                Date actionDefineUpdateTime = version.getActionDefineUpdateTime();
                Date productLastUpdateTime = version.getProductLastUpdateTime();
                //time from local
                Date localMissionUpdateTime = CommonUtil.parseDate(UserUtil.getLastUpdateTime("MissionUpdateTime"));
                Date localFightDefineUpdateTime = CommonUtil.parseDate(UserUtil.getLastUpdateTime("FightDefineUpdateTime"));
                Date localRecommendUpdateTime = CommonUtil.parseDate(UserUtil.getLastUpdateTime("RecommendAppUpdateTime"));
                Date localActionDefineUpdateTime = CommonUtil.parseDate(UserUtil.getLastUpdateTime("ActionDefineUpdateTime"));
                Date localProductDefineUpdateTime = CommonUtil.parseDate(UserUtil.getLastUpdateTime("VProductsUpdateTime"));
                GlobalSyncStatus.setVersionSync();
                if (localMissionUpdateTime.before(missionLastUpdateTime)) {
                    missionHandler.syncMissions(syncStatusHandler);
                } else {
                    GlobalSyncStatus.missionSynced = true;
                }
                if (localFightDefineUpdateTime.before(fightDefineUpdateTime)) {
                    systemHandler.syncFightDefine(syncStatusHandler);
                } else {
                    GlobalSyncStatus.fightDefineSynced = true;
                }
                if (localRecommendUpdateTime.before(recommendLastUpdateTime)) {
                    systemHandler.syncRecommendApp(syncStatusHandler);
                } else {
                    GlobalSyncStatus.recommendSynced = true;
                }
                if (localActionDefineUpdateTime.before(actionDefineUpdateTime)) {
                    systemHandler.syncActionDefine(syncStatusHandler);
                } else {
                    GlobalSyncStatus.actionDefineSynced = true;
                }
                if (localProductDefineUpdateTime.before(productLastUpdateTime)) {
                    virtualProductHandler.syncVProducts(syncStatusHandler);
                } else {
                    GlobalSyncStatus.productSynced = true;
                }
                //sync userInfo.
                userHandler.syncUserInfo(syncStatusHandler,UserUtil.getUserId());
                syncStatusHandler.sendEmptyMessage(1);
            } else {
                jumpActivity();
            }
        }
    };

    Handler syncStatusHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (GlobalSyncStatus.missionSynced &&
                    GlobalSyncStatus.fightDefineSynced &&
                    GlobalSyncStatus.recommendSynced &&
                    GlobalSyncStatus.actionDefineSynced &&
                    GlobalSyncStatus.productSynced &&
                    needJump) {
                jumpActivity();
            }
        }
    };

    public void jumpActivity() {
        needJump = false;
        if (UserUtil.getUserId() > 0) {
            Intent intent = new Intent();
            intent.putExtra("pageId", 1);
            intent.setClass(LoadingActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent();
            intent.setClass(LoadingActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
