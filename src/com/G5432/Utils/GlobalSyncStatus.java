package com.G5432.Utils;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-2-28
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
public class GlobalSyncStatus {

    //default sync logic
    public static Boolean messageSynced = true;
    public static Boolean missionSynced = true;
    public static Boolean recommendSynced = true;
    public static Boolean productSynced = true;
    public static Boolean actionDefineSynced = true;

    //weather sync logic
    public static Boolean weatherSynced = true;
    public static Boolean pm25Synced = true;

    //history and mission history
    public static Boolean historySynced = true;
    public static Boolean missionHistorySynced = true;

    //user sync logic
    public static Boolean userInfoSynced = true;
    public static Boolean userPropsSynced = true;
    public static Boolean userFriendSynced = true;
    public static Boolean userFriendSortSynced = true;
    public static Boolean userActionSynced = true;

    public static void setVersionSync() {
        messageSynced = false;
        missionSynced = false;
        recommendSynced = false;
        productSynced = false;
        actionDefineSynced = false;
        userInfoSynced = false;
    }

    public static void setUserBaseSync() {
        GlobalSyncStatus.userPropsSynced = false;
        GlobalSyncStatus.userFriendSynced = false;
        GlobalSyncStatus.userFriendSortSynced = false;
        GlobalSyncStatus.userActionSynced = false;
        GlobalSyncStatus.missionHistorySynced = false;
        GlobalSyncStatus.historySynced = false;
    }
}
