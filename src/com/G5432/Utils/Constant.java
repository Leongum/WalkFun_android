package com.G5432.Utils;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-9
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
public class Constant {

    public static final String SERVICE_HOST = "http://121.199.56.231:8080/walkfun/service/api";

    // --- user api ---
    public static final String USER_LOGIN_URL = SERVICE_HOST + "/account/login/{0}/{1}";
    public static final String USER_GETINFO_BY_ID_URL = SERVICE_HOST + "/account/get/{0}?lastUpdateTime={1}";
    public static final String USER_REGISTER_URL = SERVICE_HOST + "/account/create";
    public static final String USER_BASE_UPDATE_URL = SERVICE_HOST + "/account/update/base/{0}";

    // --- firend api ---
    public static final String FRIEND_CREATE_OR_UPDATE_URL = SERVICE_HOST + "/account/friends/create/{0}";
    public static final String FRIEND_GET_URL = SERVICE_HOST + "/account/friends/get/{0}?lastUpdateTime={1}";
    public static final String FRIEND_SEARCH_URL = SERVICE_HOST + "/account/search/get/{0}";
    public static final String FRIEND_SORT_UPDATE_URL = SERVICE_HOST + "/account/friendsort/get/{0}?lastUpdateTime={1}";
    public static final String FRIEND_RECOMMEND_URL = SERVICE_HOST + "/account/friends/recommend/{0}";//page no form 0

    // --- action api ---
    public static final String ACTION_CREATE_ACTION_URL = SERVICE_HOST + "/account/action/create/{0}";
    public static final String ACTION_GET_ACTION_URL = SERVICE_HOST + "/account/action/get/{0}";
    public static final String ACTION_GET_ACTION_BY_USER_ID_URL = SERVICE_HOST + "/account/action/others/get/{0}";

    // --- prop api ---
    public static final String PROP_GET_URL = SERVICE_HOST + "/account/props/get/{0}?lastUpdateTime={1}";

    // --- reward api ---
    public static final String REWARD_GET_RANDOM_URL = SERVICE_HOST + "/account/reward/get/{0}";

    // --- system api ---
    public static final String SYSTEM_VERSION_URL = SERVICE_HOST + "/system/version/get/{0}";
    public static final String SYSTEM_RECOMMEND_APP_URL = SERVICE_HOST + "/system/recommend/get/{0}";
    public static final String SYSTEM_ACTION_DEFINE_URL = SERVICE_HOST + "/system/actionDefine/get/{0}";
    public static final String SYSTEM_FIGHT_DEFINE_URL = SERVICE_HOST + "/system/fightDefine/get/{0}";

    // --- mission api ---
    public static final String MISSION_GET_URL = SERVICE_HOST + "/missions/mission/get?lastUpdateTime={0}";
    public static final String MISSION_DAILY_GET_URL = SERVICE_HOST + "/missions/dailymission/get/{0}";

    // --- history api ---
    public static final String HISTORY_GET_RUNNING_HISTORY_URL = SERVICE_HOST + "/running/history/get/{0}?lastUpdateTime={1}";
    public static final String HISTORY_GET_SIMPLE_RUNNING_HISTORY_URL = SERVICE_HOST + "/running/history/simple/get/{0}";
    public static final String HISTORY_POST_RUNNING_HISTORY_URL = SERVICE_HOST + "/running/history/post/{0}";
    public static final String HISTORY_GET_MISSION_HISTORY_URL = SERVICE_HOST + "/running/history/mission/get/{0}?lastUpdateTime={1}";
    public static final String HISTORY_POST_MISSION_HISTORY_URL = SERVICE_HOST + "/running/history/mission/put/{0}";

    // --- virtual prop api ---
    public static final String VIRTUAL_PRODUCT_GET_URL = SERVICE_HOST + "/vproduct/product/get?lastUpdateTime={0}";
    public static final String VIRTUAL_PRODUCT_BUY_URL = SERVICE_HOST + "/vproduct/history/create/{0}";

    // --- third party api ---
    public static final String THIRD_PARTY_PM25_URL = "http://www.cyberace.cc/service/api/weather/pm25?cityName={0}&provinceName={1}";
    public static final String THIRD_PARTY_WEATHER_URL = "http://www.weather.com.cn/data/sk/{0}.html";


    //setting
    public static final String SYNC_MODE_WIFI_ONLY = "Only_Wifi";
    public static final String SYNC_MODE_ALL_MODE = "All_Mode";
    public static final String SPEED_FORMAT_KM_PER_HOUR = "km/h";
    public static final String SPEED_FORMAT_MIN_PER_KM = "min/km";

}
