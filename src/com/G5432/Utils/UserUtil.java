package com.G5432.Utils;

import android.content.SharedPreferences;
import com.G5432.Entity.UserBase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-15
 * Time: 下午3:12
 * To change this template use File | Settings | File Templates.
 */
public class UserUtil {

    public static SharedPreferences sharedPreferences;

    public static SharedPreferences weatherPreferences;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();

    public static Integer getUserId() {
        return sharedPreferences.getInt("userId", -1);
    }

    public static String getUserName() {
        return sharedPreferences.getString("userName", "");
    }

    public static String getUserUuid() {
        return sharedPreferences.getString("uuid", "");
    }

    public static Date getSystemTime() {
        String dateString = sharedPreferences.getString("systemTime", "");
        return CommonUtil.parseDate(dateString);
    }

    public static void logout() {
        String localMissionUpdateTime = UserUtil.getLastUpdateTime("MissionUpdateTime");
        String localMessageUpdateTime = UserUtil.getLastUpdateTime("SystemMessageUpdateTime");
        String localRecommendUpdateTime = UserUtil.getLastUpdateTime("RecommendAppUpdateTime");
        String localActionDefineUpdateTime = UserUtil.getLastUpdateTime("ActionDefineUpdateTime");
        String localProductDefineUpdateTime = UserUtil.getLastUpdateTime("VProductsUpdateTime");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putInt("userId", -1);
        editor.putString("MissionUpdateTime", localMissionUpdateTime);
        editor.putString("SystemMessageUpdateTime", localMessageUpdateTime);
        editor.putString("RecommendAppUpdateTime", localRecommendUpdateTime);
        editor.putString("ActionDefineUpdateTime", localActionDefineUpdateTime);
        editor.putString("VProductsUpdateTime", localProductDefineUpdateTime);
        editor.commit();
    }

    public static String getLastUpdateTime(String key) {
        return sharedPreferences.getString(key, "2000-01-01 00:00:00");
    }

    public static void saveLastUpdateTime(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, sharedPreferences.getString("systemTime", ""));
        editor.commit();
    }

    public static void saveSystemTime(Date systemTime) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("systemTime", CommonUtil.parseDateToString(systemTime));
        editor.commit();
    }

    public static Integer getDescVersion() {
        return UserUtil.sharedPreferences.getInt("descVersion", 0);
    }

    public static String getVersionDesc() {
        return UserUtil.sharedPreferences.getString("versionDesc", "如果你看到这段文字，说明网络连接有问题~");
    }

    public static void saveDescVersion(Integer descVersion, String description) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("descVersion", descVersion);
        editor.putString("versionDesc", description);
        editor.commit();
    }

    public static Integer getActionUpdate() {
        return UserUtil.sharedPreferences.getInt("actionUpdate", 0);
    }

    public static void saveActionUpdate(Integer updated) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("actionUpdate", updated);
        editor.commit();
    }

    public static Integer getNoticeUpdate() {
        return UserUtil.sharedPreferences.getInt("noticeUpdate", 0);
    }

    public static void saveNoticeUpdate(Integer updated) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("noticeUpdate", updated);
        editor.commit();
    }

    public static void saveUserInfoToList(UserBase user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", user.getUserId());
        editor.putString("userName", user.getUserName());
        editor.putString("uuid", user.getUuid());
        editor.putString("sex", user.getSex());
        editor.commit();
    }

    public static String getUserSex() {
        return UserUtil.sharedPreferences.getString("sex", "男");
    }

    public static Integer getDailyMissionId() {
        return sharedPreferences.getInt("dailyMissionId", -1);
    }

    public static void saveLastUpdateTime(Integer missionId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("dailyMissionId", missionId);
        editor.commit();
    }


    public static void initWeatherInfo(Integer index, String weatherInfo) {
        SharedPreferences.Editor editor = weatherPreferences.edit();
        editor.putInt("index", index);
        editor.putString("weatherInfo", weatherInfo);
        editor.putString("weatherSyncTime", CommonUtil.parseDateToString(new Date()));
        editor.commit();
    }

    public static String getWeatherInfo() {
        return UserUtil.weatherPreferences.getString("weatherInfo", "");
    }

    public static Integer getWeatherIndex() {
        return weatherPreferences.getInt("index", -1);
    }

    public static void saveWeatherTime(Date systemTime) {
        SharedPreferences.Editor editor = weatherPreferences.edit();
        editor.putString("weatherSyncTime", CommonUtil.parseDateToString(systemTime));
        editor.commit();
    }

    public static Date getWeatherTime() {
        return CommonUtil.parseDate(UserUtil.weatherPreferences.getString("weatherSyncTime", "2000-01-01 00:00:00"));
    }

    public static Integer getUsedPropId() {
        int propId = UserUtil.sharedPreferences.getInt("UsedPropId", -1);
        int day = UserUtil.sharedPreferences.getInt("UsedPropDay", -1);
        if (day == -1) {
            return -1;
        } else if (day != (new Date()).getDay()) {
            return -1;
        } else {
            return propId;
        }
    }

    public static void saveUsedPropId(Integer propId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("UsedPropId", propId);
        editor.putInt("UsedPropDay", (new Date()).getDay());
        editor.commit();
    }
}
