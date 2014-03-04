package com.G5432.DBUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.G5432.Entity.*;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-8
 * Time: 下午4:15
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "account.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Action.class);
            TableUtils.createTable(connectionSource, ActionDefinition.class);
            TableUtils.createTable(connectionSource, FriendSort.class);
            TableUtils.createTable(connectionSource, Mission.class);
            TableUtils.createTable(connectionSource, MissionHistory.class);
            TableUtils.createTable(connectionSource, RecommendApp.class);
            TableUtils.createTable(connectionSource, RunningHistory.class);
            TableUtils.createTable(connectionSource, SystemMessage.class);
            TableUtils.createTable(connectionSource, UserBase.class);
            TableUtils.createTable(connectionSource, UserFriend.class);
            TableUtils.createTable(connectionSource, UserInfo.class);
            TableUtils.createTable(connectionSource, UserProp.class);
            TableUtils.createTable(connectionSource, VProduct.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to create databases", e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private Dao<Action, Void> actionDao;

    public Dao<Action, Void> getActionDao() throws SQLException {
        if (actionDao == null) {
            actionDao = getDao(Action.class);
        }
        return actionDao;
    }

    private Dao<ActionDefinition, Integer> actionDefinitionDao;

    public Dao<ActionDefinition, Integer> getActionDefinitionDao() throws SQLException {
        if (actionDefinitionDao == null) {
            actionDefinitionDao = getDao(ActionDefinition.class);
        }
        return actionDefinitionDao;
    }

    private Dao<FriendSort, Integer> friendSortDao;

    public Dao<FriendSort, Integer> getFriendSortDao() throws SQLException {
        if (friendSortDao == null) {
            friendSortDao = getDao(FriendSort.class);
        }
        return friendSortDao;
    }

    private Dao<Mission, Integer> missionDao;

    public Dao<Mission, Integer> getMissionDao() throws SQLException {
        if (missionDao == null) {
            missionDao = getDao(Mission.class);
        }
        return missionDao;
    }

    private Dao<RecommendApp, Integer> recommendAppDao;

    public Dao<RecommendApp, Integer> getRecommendAppDao() throws SQLException {
        if (recommendAppDao == null) {
            recommendAppDao = getDao(RecommendApp.class);
        }
        return recommendAppDao;
    }

    private Dao<MissionHistory, String> missionHistoryDao;

    public Dao<MissionHistory, String> getMissionHistoryDao() throws SQLException {
        if (missionHistoryDao == null) {
            missionHistoryDao = getDao(MissionHistory.class);
        }
        return missionHistoryDao;
    }

    private Dao<RunningHistory, String> runningHistoryDao;

    public Dao<RunningHistory, String> getRunningHistoryDao() throws SQLException {
        if (runningHistoryDao == null) {
            runningHistoryDao = getDao(RunningHistory.class);
        }
        return runningHistoryDao;
    }

    private Dao<SystemMessage, Integer> systemMessageDao;

    public Dao<SystemMessage, Integer> getSystemMessageDao() throws SQLException {
        if (systemMessageDao == null) {
            systemMessageDao = getDao(SystemMessage.class);
        }
        return systemMessageDao;
    }

    private Dao<UserBase, Integer> userBaseDao;

    public Dao<UserBase, Integer> getUserBaseDao() throws SQLException {
        if (userBaseDao == null) {
            userBaseDao = getDao(UserBase.class);
        }
        return userBaseDao;
    }

    private Dao<UserFriend, String> userFriendDao;

    public Dao<UserFriend, String> getUserFriendDao() throws SQLException {
        if (userFriendDao == null) {
            userFriendDao = getDao(UserFriend.class);
        }
        return userFriendDao;
    }

    private Dao<UserInfo, Integer> userInfoDao;

    public Dao<UserInfo, Integer> getUserInfoDao() throws SQLException {
        if (userInfoDao == null) {
            userInfoDao = getDao(UserInfo.class);
        }
        return userInfoDao;
    }

    private Dao<UserProp, String> userPropDao;

    public Dao<UserProp, String> getUserPropDao() throws SQLException {
        if (userPropDao == null) {
            userPropDao = getDao(UserProp.class);
        }
        return userPropDao;
    }

    private Dao<VProduct, Integer> vProductDao;

    public Dao<VProduct, Integer> getvProductDao() throws SQLException {
        if (vProductDao == null) {
            vProductDao = getDao(VProduct.class);
        }
        return vProductDao;
    }
}
