package com.G5432.WalkFun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.UserBase;
import com.G5432.HttpClient.UserHandler;
import com.G5432.Utils.UserUtil;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

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
    private ImageView imgUserInfo;
    private TextView txtUserName;
    private TextView txtLevel;

    private UserHandler userHandler;
    private UserBase userBase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        userHandler = new UserHandler(getHelper());
        userBase = userHandler.fetchUser(UserUtil.getUserId());
        initPageUIControl();
    }

    private void initPageUIControl() {
        btnSync = (Button) findViewById(R.id.mainBtnSync);
        btnSetting = (Button) findViewById(R.id.mainBtnSet);
        imgUserInfo = (ImageView) findViewById(R.id.mainImgUserInfo);
        txtUserName = (TextView) findViewById(R.id.mainTxtName);
        txtLevel = (TextView) findViewById(R.id.mainTxtLevel);

        txtUserName.setText(userBase.getNickName());
        txtLevel.setText("Lv." + userBase.getUserInfo().getLevel().intValue());

        btnSync.setOnClickListener(syncListener);
        btnSetting.setOnClickListener(settingListener);
        imgUserInfo.setOnClickListener(userInfoListener);
    }

    private View.OnClickListener syncListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
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
            //todo:: do jump
        }
    };

    @Override
    public void onBackPressed() {
        //todo::do nothing now
    }
}
