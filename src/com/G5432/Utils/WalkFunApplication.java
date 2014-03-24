package com.G5432.Utils;

import android.app.Application;
import android.content.Context;

import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-23
 * Time: 下午3:25
 * To change this template use File | Settings | File Templates.
 */
public class WalkFunApplication extends Application {
    private static WalkFunApplication mInstance = null;
    public boolean m_bKeyRight = true;
    BMapManager mBMapManager = null;
    public LocationClient mLocationClient;
    public GeofenceClient mGeofenceClient;

    //test key.
    public static final String strKey = "CmhB94lOObFepqhFpf89Y0ur";

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initEngineManager(this);
    }

    public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(strKey, new MyGeneralListener())) {
            ToastUtil.showMessage(WalkFunApplication.getInstance().getApplicationContext(), "BMapManager 初始化错误!");
        }

        if(mLocationClient == null){
            mLocationClient = new LocationClient(context);
        }
        mLocationClient.setAK(strKey);
        mGeofenceClient = new GeofenceClient(this);
    }

    public static WalkFunApplication getInstance() {
        return mInstance;
    }

    //设置相关参数
    public void setWeatherLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);                //打开gps
        option.setAddrType("all");
        option.setScanSpan(3000);
        option.setPriority(LocationClientOption.NetWorkFirst);        //不设置，默认是gps优先
        option.setPoiNumber(0);
        option.disableCache(true);
        mLocationClient.setLocOption(option);
    }

    //设置相关参数
    public void setMapLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);                //打开gps
        option.setScanSpan(1000);
        option.setPriority(LocationClientOption.GpsFirst);        //不设置，默认是gps优先
        option.setCoorType("bd09ll");     //设置坐标类型
        option.disableCache(true);
        mLocationClient.setLocOption(option);
    }

    // 常用事件监听，用来处理通常的网络错误，授权验证错误等
    static class MyGeneralListener implements MKGeneralListener {

        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                ToastUtil.showMessage(WalkFunApplication.getInstance().getApplicationContext(), "您的网络出错啦!");
            } else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                ToastUtil.showMessage(WalkFunApplication.getInstance().getApplicationContext(), "输入正确的检索条件！");
            }
        }

        @Override
        public void onGetPermissionState(int iError) {
            //非零值表示key验证未通过
            if (iError != 0) {
                //授权Key错误：
                ToastUtil.showMessage(WalkFunApplication.getInstance().getApplicationContext(),
                        "请在 DemoApplication.java文件输入正确的授权Key,并检查您的网络连接是否正常！error: " + iError);
                WalkFunApplication.getInstance().m_bKeyRight = false;
            } else {
                WalkFunApplication.getInstance().m_bKeyRight = true;
                ToastUtil.showMessage(WalkFunApplication.getInstance().getApplicationContext(),
                        "key认证成功");
            }
        }
    }
}
