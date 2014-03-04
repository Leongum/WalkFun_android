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
 * Date: 13-11-28
 * Time: 上午10:49
 * To change this template use File | Settings | File Templates.
 */
public class BMapApplication extends Application {

    private static BMapApplication mInstance = null;
    public boolean m_bKeyRight = true;
    public BMapManager mBMapManager = null;
    public LocationClient mLocationClient = null;
    public GeofenceClient mGeofenceClient;

    //debug
    public static final String strKey = "GsyoFG2FRKxupsmQTxFdXcoG";
    //prod
    //public static final String strKey = "1eKnSBZdGqw8HBAxXagPk2QY";

    //d696105b23e4d528be16e7f308aad37f;com.G5432.Cyberace
    /*
    	注意：为了给用户提供更安全的服务，Android SDK自v2.1.3版本开始采用了全新的Key验证体系。
    	因此，当您选择使用v2.1.3及之后版本的SDK时，需要到新的Key申请页面进行全新Key的申请，
    	申请及配置流程请参考开发指南的对应章节
    */

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

        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
            ToastUtil.showMessage(BMapApplication.getInstance().getApplicationContext(),"BMapManager  初始化错误!");
        }

        if(mLocationClient == null){
            mLocationClient = new LocationClient(context);
        }
        mLocationClient.setAK(strKey);
        mGeofenceClient = new GeofenceClient(this);
    }

    public static BMapApplication getInstance() {
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
                ToastUtil.showMessage(BMapApplication.getInstance().getApplicationContext(),"您的网络出错啦!");
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                ToastUtil.showMessage(BMapApplication.getInstance().getApplicationContext(),"输入正确的检索条件!");
            }
        }

        @Override
        public void onGetPermissionState(int iError) {
            if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
                //授权Key错误
                ToastUtil.showMessage(BMapApplication.getInstance().getApplicationContext(),"输入正确的授权Key!");
                BMapApplication.getInstance().m_bKeyRight = false;
            }
        }
    }
}
