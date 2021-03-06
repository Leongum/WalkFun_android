package com.G5432.HttpClient;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.Entity.Enum.ActionDefineEnum;
import com.G5432.Service.SystemService;
import com.G5432.Utils.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-3
 * Time: 上午11:20
 * To change this template use File | Settings | File Templates.
 */
public class SystemHandler {
    private HttpClientHelper httpClientHelper = new HttpClientHelper();

    private SystemService systemService = null;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();

    public SystemHandler(DatabaseHelper helper) {
        this.systemService = new SystemService(helper);
    }

    /**
     * 获取天气数据 message what = 1 and obj = weather info
     *
     * @param cityCode
     * @param handler
     */
    public void syncWeatherInfo(String cityCode, final Handler handler) {
        GlobalSyncStatus.weatherSynced = false;
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.THIRD_PARTY_WEATHER_URL, cityCode));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                GlobalSyncStatus.weatherSynced = true;
                Log.i(this.getClass().getName(), response);
                Message msg = Message.obtain();
                if (statusCode == 200 || statusCode == 204) {
                    JSONObject jsonObject = null;
                    WeatherDetailInfo weatherDetailInfo = new WeatherDetailInfo();
                    try {
                        jsonObject = new JSONObject(response);
                        jsonObject = jsonObject.getJSONObject("weatherinfo");
                        weatherDetailInfo.setCity(jsonObject.getString("city"));
                        weatherDetailInfo.setTemp(jsonObject.getInt("temp"));
                        weatherDetailInfo.setWindDirection(jsonObject.getString("WD"));
                        weatherDetailInfo.setWindSpeed(jsonObject.getString("WS"));
                        weatherDetailInfo.setHumidity(jsonObject.getString("SD"));
                    } catch (JSONException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    msg.obj = weatherDetailInfo;
                    msg.what = 1;
                } else {
                    Log.e(this.getClass().getName(), response);
                    msg.what = 0;
                }
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Throwable error, String content) {
                GlobalSyncStatus.weatherSynced = true;
                Log.e(this.getClass().getName(), error.getMessage());
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 获取天气数据 message what = 2 and obj = pm25 info
     *
     * @param city
     * @param province
     * @param handler
     */
    public void syncPM25Info(String city, String province, final Handler handler) {
        GlobalSyncStatus.pm25Synced = false;
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.THIRD_PARTY_PM25_URL, city, province));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                GlobalSyncStatus.pm25Synced = true;
                Log.i(this.getClass().getName(), response);
                Message msg = Message.obtain();
                if (statusCode == 200 || statusCode == 204) {
                    PM25DetailInfo pm25DetailInfo = gson.fromJson(response, PM25DetailInfo.class);
                    msg.obj = pm25DetailInfo;
                    msg.what = 2;
                } else {
                    Log.e(this.getClass().getName(), response);
                    msg.what = 0;
                }
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Throwable error, String content) {
                GlobalSyncStatus.pm25Synced = true;
                Log.e(this.getClass().getName(), error.getMessage());
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 方法在handler中的message.obj中返回VersionControl实体。
     *
     * @param handler 用户接收消息的handler。
     */
    public void syncVersion(final Handler handler) {
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.SYSTEM_VERSION_URL, "android"));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.i(this.getClass().getName(), response);
                Message msg = Message.obtain();
                if (statusCode == 200 || statusCode == 204) {
                    VersionControl versionControl = gson.fromJson(response, VersionControl.class);
                    msg.obj = versionControl;
                    msg.what = 1;

                } else {
                    Log.e(this.getClass().getName(), response);
                    msg.what = 0;
                }
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Log.e(this.getClass().getName(), "" + error.getMessage());
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 同步system recommend app 返回empty message handler
     *
     * @param handler
     */
    public void syncRecommendApp(final Handler handler) {
        GlobalSyncStatus.recommendSynced = false;
        String lastUpdateTime = UserUtil.getLastUpdateTime("RecommendAppUpdateTime");
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.SYSTEM_RECOMMEND_APP_URL, lastUpdateTime));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.i(this.getClass().getName(), response);
                GlobalSyncStatus.recommendSynced = true;
                if (statusCode == 200 || statusCode == 204) {
                    List<RecommendApp> recommendAppList = gson.fromJson(response, new TypeToken<List<RecommendApp>>() {
                    }.getType());
                    systemService.saveRecommendAppToDB(recommendAppList);
                    UserUtil.saveLastUpdateTime("RecommendAppUpdateTime");
                    handler.sendEmptyMessage(1);
                } else {
                    Log.e(this.getClass().getName(), response);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                GlobalSyncStatus.recommendSynced = true;
                Log.e(this.getClass().getName(), error.getMessage());
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * @return 返回recommend app的列表
     */
    public List<RecommendApp> fetchAllRecommedInfo() {
        return systemService.fetchRecommendList();
    }


    /**
     * 同步action define 返回empty message handler
     *
     * @param handler
     */
    public void syncActionDefine(final Handler handler) {
        GlobalSyncStatus.actionDefineSynced = false;
        String lastUpdateTime = UserUtil.getLastUpdateTime("ActionDefineUpdateTime");
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.SYSTEM_ACTION_DEFINE_URL, lastUpdateTime));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.i(this.getClass().getName(), response);
                GlobalSyncStatus.actionDefineSynced = true;
                if (statusCode == 200 || statusCode == 204) {
                    List<ActionDefinition> actionDefinitions = gson.fromJson(response, new TypeToken<List<ActionDefinition>>() {
                    }.getType());
                    systemService.saveActionDefineToDB(actionDefinitions);
                    UserUtil.saveLastUpdateTime("ActionDefineUpdateTime");
                    handler.sendEmptyMessage(1);
                } else {
                    Log.e(this.getClass().getName(), response);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                GlobalSyncStatus.actionDefineSynced = true;
                Log.e(this.getClass().getName(), error.getMessage());
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 根据action define type 获取action define list
     *
     * @param actionType RUN USE REWARD
     * @return action define 列表
     */
    public List<ActionDefinition> fetchAllActionDefine(ActionDefineEnum actionType) {
        return systemService.fetchAllActionDefine(actionType);
    }

    /**
     * 根据prod id获取对应的action id。
     * @param propId
     * @return
     */
    public ActionDefinition fetchActionDefineByPropId(Integer propId) {
        List<ActionDefinition> actionDefinitionList = fetchAllActionDefine(ActionDefineEnum.USE);
        List<ActionDefinition> findActions = new ArrayList<ActionDefinition>();
        for (ActionDefinition actionDefinition : actionDefinitionList) {
            Map<Integer, Integer> actionDic = CommonUtil.explainActionRule(actionDefinition.getActionRule());
            if (actionDic.containsKey(propId)) {
                findActions.add(actionDefinition);
            }
        }
        if (findActions.size() > 1) {
            Random random = new Random(new Date().getTime());
            int index = random.nextInt(findActions.size());
            return findActions.get(index);
        } else if (findActions.size() == 1) {
            return findActions.get(0);
        }
        return null;
    }

    /**
     * 根据action id 获取action
     * @param id
     * @return
     */
    public ActionDefinition fetchActionDefineById(Integer id){
        return systemService.fetchActionDefineById(id);
    }

    /**
     * fight define 返回empty message handler
     *
     * @param handler
     */
    public void syncFightDefine(final Handler handler) {
        GlobalSyncStatus.fightDefineSynced = false;
        String lastUpdateTime = UserUtil.getLastUpdateTime("FightDefineUpdateTime");
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.SYSTEM_FIGHT_DEFINE_URL, lastUpdateTime));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.i(this.getClass().getName(), response);
                GlobalSyncStatus.fightDefineSynced = true;
                if (statusCode == 200 || statusCode == 204) {
                    List<FightDefinition> fightDefinitions = gson.fromJson(response, new TypeToken<List<FightDefinition>>() {
                    }.getType());
                    for(FightDefinition fightDefinition : fightDefinitions){
                        if(fightDefinition.getfWin() == null || fightDefinition.getfWin().equalsIgnoreCase("")){
                           fightDefinition.setfWin("蹂躏之。|陷入苦战，最后使出饱含信念的一击将其击倒。|战斗中全程被压制，最后使出了封印已久的招式才险胜。|拼尽余下全部体力发出奋力一击，勉强获胜。");
                        }
                        if(fightDefinition.getfLoose() == null || fightDefinition.getfLoose().equalsIgnoreCase("")){
                            fightDefinition.setfLoose("感觉好厉害的样子，绕道而行。|上前挑战，但被无视了。|与之大战三百回合，即将获胜之时却被逃走了。");
                        }
                    }
                    systemService.saveFightDefineToDB(fightDefinitions);
                    UserUtil.saveLastUpdateTime("FightDefineUpdateTime");
                    handler.sendEmptyMessage(1);
                } else {
                    Log.e(this.getClass().getName(), response);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                GlobalSyncStatus.fightDefineSynced = true;
                Log.e(this.getClass().getName(), error.getMessage());
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 根据用户的等级 获取fight 战斗
     *
     * @param level
     * @return
     */
    public List<FightDefinition> fetchFightDefineByLevel(Integer level) {
        return systemService.fetchFightDefineByLevel(level);
    }

    /**
     * 根据fight id 获取 fight
     *
     * @param id
     * @return
     */
    public FightDefinition fetchFightDefineById(Integer id) {
        return systemService.fetchFightDefineById(id);
    }
}
