package com.G5432.WalkFun.Friend;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.G5432.Entity.Action;
import com.G5432.Entity.SimpleRunningHistory;
import com.G5432.Entity.UserBase;
import com.G5432.HttpClient.FriendHandler;
import com.G5432.HttpClient.RunningHistoryHandler;
import com.G5432.HttpClient.UserHandler;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.ToastUtil;
import com.G5432.WalkFun.Main.MainActivity;
import com.G5432.WalkFun.R;
import com.G5432.WalkFun.WalkFunBaseActivity;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-5-6
 * Time: 下午2:38
 * To change this template use File | Settings | File Templates.
 */
public class FriendInfoMainActivity extends WalkFunBaseActivity {

    private TextView txtUserName;
    private TextView txtLevel;
    private TextView txtFight;
    private TextView txtUpdating;
    private TextView txtLastWalk;
    private TextView txtLastWalkTime;
    private TextView txtLastAction;
    private TextView txtLastActionTime;
    private Button btnReturn;

    private UserHandler userHandler;
    private FriendHandler friendHandler;
    private RunningHistoryHandler runningHistoryHandler;
    private Integer userId;
    private UserBase userBase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        userId = extras.getInt("userId");
        setContentView(R.layout.friend_info_main);
        //init page data
        userHandler = new UserHandler(getHelper());
        friendHandler = new FriendHandler(getHelper());
        runningHistoryHandler = new RunningHistoryHandler(getHelper());
        initPageUIControl();
        userBase = userHandler.fetchUser(userId);
        if (userBase != null) {
            showUserInfo();
        }
        userHandler.syncUserInfo(userInfoHandler, userId);
        friendHandler.fetchUserLatestActions(userId, userActionHandler);
        runningHistoryHandler.getSimpleRunningHistories(userId, userRunningHandler);
    }

    private void showUserInfo() {
        txtUserName.setText(userBase.getNickName());
        txtLevel.setText("Lv." + String.valueOf((int) userBase.getUserInfo().getLevel().doubleValue()));
        txtFight.setText(String.valueOf(userBase.getUserInfo().getFriendFightWin()));
    }

    Handler userInfoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            txtUpdating.setVisibility(View.GONE);
            if (msg.what == 1) {
                userBase = userHandler.fetchUser(userId);
                showUserInfo();
            } else {
                ToastUtil.showMessage(getApplicationContext(), "用户信息获取失败");
            }
        }
    };

    Handler userActionHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                List<Action> actionList = (List<Action>) msg.obj;
                if (actionList != null && actionList.size() > 0) {
                    Action latestFriendAction = actionList.get(0);
                    if (latestFriendAction != null) {
                        int days = CommonUtil.daysBetween(latestFriendAction.getUpdateTime(), new Date());
                        txtLastActionTime.setText(days > 0 ? days + "天前" : "今天");
                        String actDesString = latestFriendAction.getActionName();
                        if (userBase.getSex().equalsIgnoreCase("男")) {
                            actDesString = actDesString.replaceAll("你", "他");
                        } else {
                            actDesString = actDesString.replaceAll("你", "她");
                        }
                        txtLastAction.setText(latestFriendAction.getActionFromName() + actDesString);
                    } else {
                        txtLastActionTime.setText("");
                        txtLastAction.setText("最近没什么人理这家伙");
                    }
                }
            }
        }
    };

    Handler userRunningHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                List<SimpleRunningHistory> runningHistoryList = (List<SimpleRunningHistory>) msg.obj;
                if (runningHistoryList != null && runningHistoryList.size() > 0) {
                    SimpleRunningHistory latestWorkout = runningHistoryList.get(0);
                    if (latestWorkout != null) {
                        int days = CommonUtil.daysBetween(latestWorkout.getMissionEndTime(), new Date());
                        txtLastWalkTime.setText(days > 0 ? days + "天前" : "今天");
                    } else {
                        txtLastWalkTime.setText("上辈子");
                    }
                }
            }
        }
    };

    private void initPageUIControl() {
        txtUserName = (TextView) findViewById(R.id.friendInfoMainTxtUserName);
        txtLevel = (TextView) findViewById(R.id.friendInfoMainTxtLevel);
        txtFight = (TextView) findViewById(R.id.friendInfoMainTxtFight);
        txtUpdating = (TextView) findViewById(R.id.friendInfoMainTxtUpdating);
        txtLastWalk = (TextView) findViewById(R.id.friendInfoMainTxtWalk);
        txtLastWalkTime = (TextView) findViewById(R.id.friendInfoMainTxtWalkTime);
        txtLastAction = (TextView) findViewById(R.id.friendInfoMainTxtPropUse);
        txtLastActionTime = (TextView) findViewById(R.id.friendInfoMainTxtPropUseTime);
        btnReturn = (Button) findViewById(R.id.friendInfoMainBtnReturn);
        btnReturn.setOnClickListener(backListener);
    }

    public View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    };

}
