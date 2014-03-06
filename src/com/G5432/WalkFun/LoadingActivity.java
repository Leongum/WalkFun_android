package com.G5432.WalkFun;

import android.content.Intent;
import android.os.*;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.HttpClient.*;
import com.G5432.Utils.*;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

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

    private MissionHandler missionHandler = null;

    private VirtualProductHandler virtualProductHandler = null;

    private Boolean needJump = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        UserUtil.sharedPreferences = getSharedPreferences("UserBaseInfo", MODE_APPEND);
        systemHandler = new SystemHandler(getHelper());
        missionHandler = new MissionHandler(getHelper());
        virtualProductHandler = new VirtualProductHandler(getHelper());
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
                Date messageLastUpdateTime = version.getMessageLastUpdateTime();
                Date recommendLastUpdateTime = version.getRecommendLastUpdateTime();
                Date actionDefineUpdateTime = version.getActionDefineUpdateTime();
                Date productLastUpdateTime = version.getProductLastUpdateTime();
                //time from local
                Date localMissionUpdateTime = CommonUtil.parseDate(UserUtil.getLastUpdateTime("MissionUpdateTime"));
                Date localMessageUpdateTime = CommonUtil.parseDate(UserUtil.getLastUpdateTime("SystemMessageUpdateTime"));
                Date localRecommendUpdateTime = CommonUtil.parseDate(UserUtil.getLastUpdateTime("RecommendAppUpdateTime"));
                Date localActionDefineUpdateTime = CommonUtil.parseDate(UserUtil.getLastUpdateTime("ActionDefineUpdateTime"));
                Date localProductDefineUpdateTime = CommonUtil.parseDate(UserUtil.getLastUpdateTime("VProductsUpdateTime"));
                GlobalSyncStatus.setVersionSync();
                if (localMissionUpdateTime.before(missionLastUpdateTime)) {
                    missionHandler.syncMissions(syncStatusHandler);
                } else {
                    GlobalSyncStatus.missionSynced = true;
                }
                if (localMessageUpdateTime.before(messageLastUpdateTime)) {
                    systemHandler.syncSystemMessages(syncStatusHandler);
                } else {
                    GlobalSyncStatus.messageSynced = true;
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
                    GlobalSyncStatus.messageSynced &&
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
            intent.setClass(LoadingActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent();
            intent.setClass(LoadingActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
