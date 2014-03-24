package com.G5432.WalkFun.Main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.PM25DetailInfo;
import com.G5432.Entity.UserBase;
import com.G5432.Entity.WeatherDetailInfo;
import com.G5432.HttpClient.SystemHandler;
import com.G5432.HttpClient.UserHandler;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.ToastUtil;
import com.G5432.Utils.UserUtil;
import com.G5432.Utils.WalkFunApplication;
import com.G5432.WalkFun.Friend.FriendMainActivity;
import com.G5432.WalkFun.History.HistoryRunMainActivity;
import com.G5432.WalkFun.Item.ItemMainActivity;
import com.G5432.WalkFun.R;
import com.baidu.location.*;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.text.MessageFormat;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-6
 * Time: 上午11:10
 * To change this template use File | Settings | File Templates.
 */
public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    //init UI control
    private Button btnSync;
    private Button btnSetting;
    private Button btnTraffic;
    private ImageView imgUserInfo;
    private TextView txtUserName;
    private TextView txtLevel;

    public MyLocationListener myListener = new MyLocationListener();
    private WalkFunApplication bMapApp = null;

    private Button btnItem;

    private UserHandler userHandler;
    private SystemHandler systemHandler;
    private UserBase userBase;

    private Integer weatherStatus = 0; //0 获取中 －1天气获取失败。
    private String weatherInformation = "天气信息获取中...";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bMapApp = (WalkFunApplication) this.getApplication();
        bMapApp.mLocationClient.registerLocationListener(myListener);
        bMapApp.setWeatherLocationOption();
        bMapApp.mLocationClient.start();
        bMapApp.mLocationClient.requestLocation();

        setContentView(R.layout.main);
        userHandler = new UserHandler(getHelper());
        systemHandler = new SystemHandler(getHelper());
        userBase = userHandler.fetchUser(UserUtil.getUserId());
        initPageUIControl();
    }


    private void initPageUIControl() {
        btnSync = (Button) findViewById(R.id.mainBtnSync);
        btnSetting = (Button) findViewById(R.id.mainBtnSet);
        btnTraffic = (Button) findViewById(R.id.mainBtnTrafficlight);
        imgUserInfo = (ImageView) findViewById(R.id.mainImgUserInfo);
        txtUserName = (TextView) findViewById(R.id.mainTxtName);
        txtLevel = (TextView) findViewById(R.id.mainTxtLevel);


        txtUserName.setText(userBase.getNickName());
        txtLevel.setText("Lv." + userBase.getUserInfo().getLevel().intValue());

        btnSync.setOnClickListener(syncListener);
        btnSetting.setOnClickListener(settingListener);
        imgUserInfo.setOnClickListener(userInfoListener);
        btnTraffic.setOnClickListener(trafficLightListener);

        //todo:: need remove
        btnItem = (Button) findViewById(R.id.mainBtnProps);
        btnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ItemMainActivity.class);
                startActivity(intent);
            }
        });
    }

    private View.OnClickListener syncListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, FriendMainActivity.class);
            startActivity(intent);
            //todo:: do sync things
        }
    };

    private View.OnClickListener settingListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            userHandler.logout();
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            //todo:: do jump
        }
    };

    private View.OnClickListener userInfoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, HistoryRunMainActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener trafficLightListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (weatherStatus == -1) {
                ToastUtil.showMessage(getApplicationContext(), "天气信息获取失败");
            } else {
                ToastUtil.showMessageLong(getApplicationContext(), weatherInformation);
            }
        }

    };

    /**
     * 监听函数，有更新位置的时候，格式化成字符串，输出到屏幕中
     */
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return;
            if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                String cityName = location.getCity();
                String districtName = location.getDistrict();
                if (cityName != null || districtName != null) {
                    if (bMapApp.mLocationClient.isStarted()) bMapApp.mLocationClient.stop();
                    systemHandler.syncPM25Info(districtName, cityName, weatherHandler);
                    systemHandler.syncWeatherInfo(CommonUtil.getCityCode(cityName, districtName), weatherHandler);
                }
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
            //do nothing
        }
    }

    // 定义Handler
    Handler weatherHandler = new Handler() {
        WeatherDetailInfo weatherDetailInfo = null;
        PM25DetailInfo pm25DetailInfo = null;
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                weatherDetailInfo = (WeatherDetailInfo) msg.obj;
            } else if (msg.what == 2) {
                pm25DetailInfo = (PM25DetailInfo) msg.obj;
            } else {
                weatherStatus = 0;
            }
            if (weatherDetailInfo != null && pm25DetailInfo != null) {
                Integer temp = weatherDetailInfo.getTemp();
                Integer pm25 = pm25DetailInfo.getAqi();
                if (temp != Integer.MIN_VALUE && temp != Integer.MAX_VALUE) {
                    int index = -1;
                    if (temp < 38 && pm25 < 300) {
                        index = (int) ((100 - pm25 / 3) * 0.6 + (100 - Math.abs(temp - 22) * 5) * 0.4);
                    } else {
                        index = 0;
                    }
                    weatherInformation = MessageFormat.format("{0}  {1}℃  {2}{3}  PM2.5: {4} {5}",
                            weatherDetailInfo.getCity(), temp, weatherDetailInfo.getWindDirection(), weatherDetailInfo.getWindSpeed(),
                            pm25, pm25DetailInfo.getQuality());
                    if (index > 75) {
                        Drawable drawableBg = getResources().getDrawable(R.drawable.main_trafficlight_green);
                        btnTraffic.setBackgroundDrawable(drawableBg);
                    } else if (temp < 0 || temp > 38 || pm25 > 250 || index < 50) {
                        Drawable drawableBg = getResources().getDrawable(R.drawable.main_trafficlight_red);
                        btnTraffic.setBackgroundDrawable(drawableBg);
                    } else if (index <= 75 && index >= 50) {
                        Drawable drawableBg = getResources().getDrawable(R.drawable.main_trafficlight_yellow);
                        btnTraffic.setBackgroundDrawable(drawableBg);
                    }
                }
            }
        }
    };

    @Override
    public void onBackPressed() {
        //todo::do nothing now
    }
}
