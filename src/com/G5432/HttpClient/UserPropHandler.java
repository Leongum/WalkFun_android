package com.G5432.HttpClient;

import android.os.Handler;
import android.util.Log;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.UserProp;
import com.G5432.Entity.VirtualProductBuy;
import com.G5432.Service.UserPropServices;
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
 * Date: 14-3-4
 * Time: 下午1:23
 * To change this template use File | Settings | File Templates.
 */
public class UserPropHandler {
    private HttpClientHelper httpClientHelper = new HttpClientHelper();

    private UserPropServices userPropServices = null;

    private UserHandler userHandler = null;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();

    public UserPropHandler(DatabaseHelper helper) {
        userPropServices = new UserPropServices(helper);
        userHandler = new UserHandler(helper);
    }

    /**
     * 同步服务器 User Props 返回empty message handler
     *
     * @param handler
     */
    public void syncUserProps(final Handler handler) {
        GlobalSyncStatus.userPropsSynced = false;
        String lastUpdateTime = UserUtil.getLastUpdateTime("UserPropUpdateTime");
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.PROP_GET_URL, UserUtil.getUserId(), lastUpdateTime));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                GlobalSyncStatus.userPropsSynced = true;
                if (statusCode == 200 || statusCode == 204) {
                    List<UserProp> userPropList = gson.fromJson(response, new TypeToken<List<UserProp>>() {
                    }.getType());
                    userPropServices.saveUserPropsToDB(userPropList);
                    UserUtil.saveLastUpdateTime("UserPropUpdateTime");
                    handler.sendEmptyMessage(1);
                } else {
                    Log.e(this.getClass().getName(), response);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                GlobalSyncStatus.userPropsSynced = true;
                Log.e(this.getClass().getName(), error.getMessage());
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 根据用户Id获取该用户的prop 列表
     *
     * @param userId
     * @return
     */
    public List<UserProp> fetchUserProps(Integer userId) {
        return userPropServices.fetchUserProps(userId);
    }

    /**
     * 购买道具
     *
     * @param productId 购买道具的id
     * @param numbers   购买道具个数
     * @param handler
     */
    public void buyUserProps(Integer productId, Integer numbers, final Handler handler) {
        final Integer userId = UserUtil.getUserId();
        VirtualProductBuy virtualProductBuy = new VirtualProductBuy();
        virtualProductBuy.setUserId(userId);
        virtualProductBuy.setProductId(productId);
        virtualProductBuy.setNumbers(numbers);

        String requestBody = gson.toJson(virtualProductBuy, VirtualProductBuy.class);
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.VIRTUAL_PRODUCT_BUY_URL, userId));
        httpClientHelper.post(url, null, requestBody, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.d(this.getClass().getName(), response);
                if (statusCode == 200 || statusCode == 204) {
                    syncUserProps(new Handler());
                    userHandler.syncUserInfo(new Handler(), userId);
                    handler.sendEmptyMessageDelayed(1, 1000);
                } else {
                    Log.e(this.getClass().getName(), response);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Log.e(this.getClass().getName(), error.getMessage() + content);
                handler.sendEmptyMessage(0);
            }
        });
    }
}
