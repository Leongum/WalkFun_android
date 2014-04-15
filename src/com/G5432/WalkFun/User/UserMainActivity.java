package com.G5432.WalkFun.User;

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
import com.G5432.Utils.*;
import com.G5432.WalkFun.History.HistoryRunMainActivity;
import com.G5432.WalkFun.R;
import com.G5432.WalkFun.WalkFunBaseActivity;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.text.MessageFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-25
 * Time: 下午12:50
 * To change this template use File | Settings | File Templates.
 */
public class UserMainActivity extends WalkFunBaseActivity {

    //init UI control
    private Button btnTraffic;
    private ImageView imgUserInfo;
    private TextView txtUserName;
    private TextView txtLevel;

    public MyLocationListener myListener = new MyLocationListener();
    private WalkFunApplication bMapApp = null;

    private UserHandler userHandler;
    private SystemHandler systemHandler;
    private UserBase userBase;

    private Integer weatherStatus = 0; //0 获取中 －1天气获取失败。
    private String weatherInformation = "天气信息获取中...";
    private Integer index = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bMapApp = (WalkFunApplication) this.getApplication();
        bMapApp.mLocationClient.registerLocationListener(myListener);

        setContentView(R.layout.user_main);
        Date now = new Date();
        long hours = (now.getTime() - UserUtil.getWeatherTime().getTime()) / (60 * 60 * 1000);
        if (hours > 1) {
            bMapApp.setWeatherLocationOption();
            bMapApp.mLocationClient.start();
            bMapApp.mLocationClient.requestLocation();
        } else {
            weatherHandler.sendEmptyMessageDelayed(3, 1000);
        }
        userHandler = new UserHandler(getHelper());
        systemHandler = new SystemHandler(getHelper());
        userBase = userHandler.fetchUser(UserUtil.getUserId());
        initPageUIControl();
    }


    private void initPageUIControl() {
        btnTraffic = (Button) findViewById(R.id.userMainBtnTrafficlight);
        imgUserInfo = (ImageView) findViewById(R.id.userMainImgUserInfo);
        txtUserName = (TextView) findViewById(R.id.userMainTxtName);
        txtLevel = (TextView) findViewById(R.id.userMainTxtLevel);


        txtUserName.setText(userBase.getNickName());
        txtLevel.setText("Lv." + userBase.getUserInfo().getLevel().intValue());

        imgUserInfo.setOnClickListener(userInfoListener);
        btnTraffic.setOnClickListener(trafficLightListener);
    }

    private View.OnClickListener userInfoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(UserMainActivity.this, HistoryRunMainActivity.class);
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
            } else if (msg.what == 3) {
                weatherInformation = UserUtil.getWeatherInfo();
                index = UserUtil.getWeatherIndex();
            } else {
                weatherStatus = 0;
            }
            if (weatherDetailInfo != null && pm25DetailInfo != null) {
                Integer temp = weatherDetailInfo.getTemp();
                Integer pm25 = pm25DetailInfo.getAqi();
                if (temp != Integer.MIN_VALUE && temp != Integer.MAX_VALUE) {
                    if (temp < 38 && pm25 < 300) {
                        index = (int) ((100 - pm25 / 3) * 0.6 + (100 - Math.abs(temp - 22) * 5) * 0.4);
                    } else {
                        index = 0;
                    }
                    weatherInformation = MessageFormat.format("{0}  {1}℃  {2}{3}  PM2.5: {4} {5}",
                            weatherDetailInfo.getCity(), temp, weatherDetailInfo.getWindDirection(), weatherDetailInfo.getWindSpeed(),
                            pm25, pm25DetailInfo.getQuality());
                    UserUtil.initWeatherInfo(index, weatherInformation);
                }
            }
            if (index >= 0) {
                if (index > 75) {
                    Drawable drawableBg = getResources().getDrawable(R.drawable.main_trafficlight_green);
                    btnTraffic.setBackgroundDrawable(drawableBg);
                } else if (index < 50) {
                    Drawable drawableBg = getResources().getDrawable(R.drawable.main_trafficlight_red);
                    btnTraffic.setBackgroundDrawable(drawableBg);
                } else if (index <= 75 && index >= 50) {
                    Drawable drawableBg = getResources().getDrawable(R.drawable.main_trafficlight_yellow);
                    btnTraffic.setBackgroundDrawable(drawableBg);
                }
            }
        }
    };

    @Override
    public void onBackPressed() {
        //todo::do nothing now
    }
}
