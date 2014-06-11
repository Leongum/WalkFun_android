package com.G5432.WalkFun.Main;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.*;
import com.G5432.Entity.UserBase;
import com.G5432.Entity.UserFriend;
import com.G5432.Entity.VProduct;
import com.G5432.HttpClient.*;
import com.G5432.Utils.*;
import com.G5432.WalkFun.Friend.FriendMainActivity;
import com.G5432.WalkFun.Item.ItemMainActivity;
import com.G5432.WalkFun.R;
import com.G5432.WalkFun.Run.RunFriendSelectActivity;
import com.G5432.WalkFun.Run.RunPropSelectActivity;
import com.G5432.WalkFun.Setting.SetMainActivity;
import com.G5432.WalkFun.User.UserMainActivity;
import com.G5432.WalkFun.WalkFunBaseActivity;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-6
 * Time: 上午11:10
 * To change this template use File | Settings | File Templates.
 */
public class MainActivity extends WalkFunBaseActivity {

    //init UI control
    private Button btnSync;
    private Button btnSetting;
    private ViewPager viewPager;
    private ImageView imgUpdated;
    private RelativeLayout layoutReadyRun;
    private Button btnReadyRun;

    private TextView txtFight;
    private TextView txtFightPlus;
    private TextView txtPower;
    private TextView txtPowerPlus;
    private ImageView imgProp;
    private TextView txtProp;
    private ImageView imgDelProp;
    private ImageView imgFriend;
    private TextView txtFriendName;
    private ImageView imgDelFriend;

    private UserHandler userHandler;
    private RunningHistoryHandler runningHistoryHandler;
    private MissionHistoryHandler missionHistoryHandler;
    private FriendHandler friendHandler;
    private UserPropHandler userPropHandler;
    private VirtualProductHandler virtualProductHandler;

    LocalActivityManager manager = null;
    private Integer TITLE_HEIGHT = 130;
    private Integer currIndex = 1;

    private View userMain;
    private View itemMain;
    private View friendMain;

    private View userMainTitle;
    private View itemMainTitle;
    private View friendMainTitle;

    private Boolean syncSuccess = true;
    private int eatPropId = -1;
    private int withFriendId = -1;
    private int fightPlus_friend = 0;
    private int fightPlus_prop = 0;
    private int powerPlus_prop = 0;

    private UserBase userBase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        currIndex = extras.getInt("pageId");
        setContentView(R.layout.main);
        Typeface typeface = FontManager.wawaw5(this);
        FontManager.changeFonts(this.getWindow().getDecorView(), typeface);
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);
        userHandler = new UserHandler(getHelper());
        runningHistoryHandler = new RunningHistoryHandler(getHelper());
        missionHistoryHandler = new MissionHistoryHandler(getHelper());
        friendHandler = new FriendHandler(getHelper());
        userPropHandler = new UserPropHandler(getHelper());
        virtualProductHandler = new VirtualProductHandler(getHelper());
        userBase = userHandler.fetchUser(UserUtil.getUserId());
        PushAgent.getInstance(this).enable();
        checkUserDeviceToken();
        initPageUIControl();
    }

    private void checkUserDeviceToken() {
        String deviceToken = UmengRegistrar.getRegistrationId(this);
        if (!userBase.getDeviceId().equalsIgnoreCase(deviceToken)) {
            userBase.setDeviceId(deviceToken);
            userBase.setPlatformInfo("android");
            userHandler.updateUserBase(userBase,new Handler());
        }
    }

    private void initPageUIControl() {
        btnSync = (Button) findViewById(R.id.mainBtnSync);
        btnSetting = (Button) findViewById(R.id.mainBtnSet);
        imgUpdated = (ImageView) findViewById(R.id.mainImgUpdated);
        layoutReadyRun = (RelativeLayout) findViewById(R.id.mainLayoutRun);
        btnReadyRun = (Button) findViewById(R.id.mainBtnReadyRun);

        txtFight = (TextView) findViewById(R.id.mainTxtRunFight);
        txtFightPlus = (TextView) findViewById(R.id.mainTxtRunFightPlus);
        txtPower = (TextView) findViewById(R.id.mainTxtRunPower);
        txtPowerPlus = (TextView) findViewById(R.id.mainTxtRunPowerPlus);
        imgProp = (ImageView) findViewById(R.id.mainImgRunProp);
        txtProp = (TextView) findViewById(R.id.mainTxtRunProp);
        imgDelProp = (ImageView) findViewById(R.id.mainImgRunPropDel);
        imgFriend = (ImageView) findViewById(R.id.mainImgRunFriend);
        txtFriendName = (TextView) findViewById(R.id.mainTxtRunFriendName);
        imgDelFriend = (ImageView) findViewById(R.id.mainImgRunFriendDel);

        layoutReadyRun.setVisibility(View.GONE);

        if (UserUtil.getNoticeUpdate().intValue() == 1 || UserUtil.getActionUpdate().intValue() == 1) {
            imgUpdated.setVisibility(View.VISIBLE);
        }

        btnSync.setOnClickListener(syncListener);
        btnSetting.setOnClickListener(settingListener);
        btnReadyRun.setOnClickListener(readyRunListener);

        viewPager = (ViewPager) findViewById(R.id.mainVPagerContainer);

        final ArrayList<View> list = new ArrayList<View>();
        Intent itemIntent = new Intent(MainActivity.this, ItemMainActivity.class);
        itemMain = getView("itemMain", itemIntent);
        list.add(itemMain);
        Intent userIntent = new Intent(MainActivity.this, UserMainActivity.class);
        userMain = getView("userMain", userIntent);
        list.add(userMain);
        Intent friendIntent = new Intent(MainActivity.this, FriendMainActivity.class);
        friendMain = getView("friendMain", friendIntent);
        list.add(friendMain);

        viewPager.setAdapter(new MainPagerAdapter(list));
        viewPager.setCurrentItem(currIndex);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        itemMainTitle = itemMain.findViewById(R.id.itemMainLayoutTitle);
        userMainTitle = userMain.findViewById(R.id.userMainLayoutTitle);
        friendMainTitle = friendMain.findViewById(R.id.friendmainLayoutTitle);

        ((RelativeLayout) itemMainTitle.getParent()).removeView(itemMainTitle);
        ((RelativeLayout) userMainTitle.getParent()).removeView(userMainTitle);
        ((RelativeLayout) friendMainTitle.getParent()).removeView(friendMainTitle);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 0;
        addContentView(itemMainTitle, params);
        addContentView(userMainTitle, params);
        addContentView(friendMainTitle, params);

        setTitleDismiss(currIndex);

    }

    /**
     * 通过activity获取视图
     *
     * @param id
     * @param intent
     * @return
     */
    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }

    public class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {

        public void onPageScrolled(int arg0, float arg1, int arg2) {
            //arg0 :当前页面，及你点击滑动的页面
            //arg1:当前页面偏移的百分比
            //arg2:当前页面偏移的像素位置
            if (arg1 > 0) {
                if (arg0 != currIndex) {
                    refreshTitleLayout(arg0 + 1, arg0, 1 - arg1);
                } else {
                    refreshTitleLayout(arg0, arg0 + 1, arg1);
                }
            } else {
                currIndex = arg0;
                setTitleDismiss(currIndex);
            }
        }
    }

    private void refreshTitleLayout(Integer disMissPage, Integer showPage, double offset) {
        if (offset <= 0.5) {
            if (disMissPage == 0) {
                View layoutUse = itemMain.findViewById(R.id.itemMainLayoutUse);
                layoutUse.setVisibility(View.GONE);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                params.topMargin = -(int) (offset * 2 * TITLE_HEIGHT);
                itemMainTitle.setLayoutParams(params);

            } else if (disMissPage == 1) {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                params.topMargin = -(int) (offset * 2 * TITLE_HEIGHT);
                userMainTitle.setLayoutParams(params);
            } else if (disMissPage == 2) {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                params.topMargin = -(int) (offset * 2 * TITLE_HEIGHT);
                friendMainTitle.setLayoutParams(params);
            }
        } else if (offset > 0.5) {
            if (showPage == 0) {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                params.topMargin = -(int) ((1 - offset) * 2 * TITLE_HEIGHT);
                itemMainTitle.setLayoutParams(params);
            } else if (showPage == 1) {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                params.topMargin = -(int) ((1 - offset) * 2 * TITLE_HEIGHT);
                userMainTitle.setLayoutParams(params);
            } else if (showPage == 2) {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                params.topMargin = -(int) ((1 - offset) * 2 * TITLE_HEIGHT);
                friendMainTitle.setLayoutParams(params);
            }
        }
    }

    public void setTitleDismiss(Integer pageIndex) {
        if (pageIndex == 0) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = -TITLE_HEIGHT;
            userMainTitle.setLayoutParams(params);
            friendMainTitle.setLayoutParams(params);
        } else if (pageIndex == 1) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = -TITLE_HEIGHT;
            itemMainTitle.setLayoutParams(params);
            friendMainTitle.setLayoutParams(params);
        } else if (pageIndex == 2) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = -TITLE_HEIGHT;
            userMainTitle.setLayoutParams(params);
            itemMainTitle.setLayoutParams(params);
        }
    }

    private View.OnClickListener syncListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            GlobalSyncStatus.setUserBaseSync();
            syncSuccess = true;
            runningHistoryHandler.uploadRunningHistories(syncSuccessHandler);
            missionHistoryHandler.uploadMissionHistories(syncSuccessHandler);
            userPropHandler.syncUserProps(syncSuccessHandler);
            friendHandler.syncActions(syncSuccessHandler);
            friendHandler.syncFriendSort(syncSuccessHandler);
            friendHandler.syncFriends(syncSuccessHandler);
        }
    };

    Handler syncSuccessHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                syncSuccess = false;
            }
            if (GlobalSyncStatus.userPropsSynced &&
                    GlobalSyncStatus.userFriendSynced &&
                    GlobalSyncStatus.userFriendSortSynced &&
                    GlobalSyncStatus.userActionSynced) {
                if (syncSuccess) {
                    ToastUtil.showMessage(getApplicationContext(), "同步成功");
                } else if (!syncSuccess) {
                    ToastUtil.showMessage(getApplicationContext(), "同步失败");
                }
            }
        }
    };

    private View.OnClickListener settingListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SetMainActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener readyRunListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            layoutReadyRun.setVisibility(View.VISIBLE);
            layoutReadyRun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layoutReadyRun.setVisibility(View.GONE);
                }
            });
            showReadyRun(userBase);
        }
    };

    private void showReadyRun(UserBase userBase) {
        Button btnRunGO = (Button) findViewById(R.id.mainBtnRunGO);
        btnRunGO.setEnabled(true);
        btnRunGO.setText("出发");
        btnRunGO.setOnClickListener(runListener);

        txtFight.setText((int) userBase.getUserInfo().getFight().doubleValue() + "");
        if (userBase.getUserInfo().getFightPlus() != null) {
            fightPlus_prop = (int) userBase.getUserInfo().getFightPlus().doubleValue();
            txtFightPlus.setText((fightPlus_friend + fightPlus_prop) + "");
        }
        txtPower.setText((int) userBase.getUserInfo().getPower().doubleValue() + "");
        if (userBase.getUserInfo().getPowerPlus() != null) {
            powerPlus_prop = (int) userBase.getUserInfo().getPowerPlus().doubleValue();
            txtPowerPlus.setText(powerPlus_prop + "");
        }

        int propId = UserUtil.getUsedPropId();
        if (propId > 0) {
            VirtualProductHandler virtualProductHandler = new VirtualProductHandler(getHelper());
            VProduct vProduct = virtualProductHandler.fetchVProduct(propId);
            imgProp.setImageURI(virtualProductHandler.getImageURI(vProduct));
            txtProp.setText(vProduct.getProductName());
        }
        imgDelProp.setOnClickListener(addPropListener);
        imgDelFriend.setOnClickListener(addFriendListener);
    }

    private View.OnClickListener delPropListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            eatPropId = -1;
            txtProp.setText("加强一下");
            fightPlus_prop = (int) userBase.getUserInfo().getFightPlus().doubleValue();
            txtFightPlus.setText((fightPlus_friend + fightPlus_prop) + "");
            powerPlus_prop = (int) userBase.getUserInfo().getPowerPlus().doubleValue();
            txtPowerPlus.setText(powerPlus_prop + "");
            imgProp.setImageURI(null);
            imgDelProp.setBackgroundResource(R.drawable.running_ready_add);
            imgDelProp.setOnClickListener(addPropListener);

        }
    };

    private View.OnClickListener addPropListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, RunPropSelectActivity.class);
            //使用startActivityForResult来启动
            startActivityForResult(intent, 200);
        }
    };

    private View.OnClickListener delFriendListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            txtFriendName.setText("带个伙伴");
            imgDelFriend.setBackgroundResource(R.drawable.running_ready_add);
            fightPlus_friend = 0;
            withFriendId = -1;
            txtFightPlus.setText((fightPlus_friend + fightPlus_prop) + "");
            imgDelFriend.setOnClickListener(addFriendListener);
        }
    };

    private View.OnClickListener addFriendListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, RunFriendSelectActivity.class);
            //使用startActivityForResult来启动
            startActivityForResult(intent, 100);
        }
    };

    private View.OnClickListener runListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            Intent intent = new Intent();
//            intent.setClass(MainActivity.this, SetMainActivity.class);
//            startActivity(intent);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //可以根据多个请求代码来作相应的操作  20 为 带好友
        if (20 == resultCode) {
            int friendId = data.getExtras().getInt("friendId");
            if (friendId != -1) {
                withFriendId = friendId;
                UserFriend userFriend = friendHandler.fetchFanFriendByIds(UserUtil.getUserId(), friendId);
                txtFriendName.setText(userFriend.getUserName());
                fightPlus_friend = (int) (userFriend.getFight().doubleValue() + userFriend.getFightPlus().doubleValue()) / 5;
                txtFightPlus.setText((fightPlus_friend + fightPlus_prop) + "");
                imgDelFriend.setBackgroundResource(R.drawable.running_ready_del);
                imgDelFriend.setOnClickListener(delFriendListener);
            }
        }
        //30为道具使用
        if (30 == resultCode) {
            int propId = data.getExtras().getInt("propId");
            if (propId != -1) {
                eatPropId = propId;
                VProduct vProduct = virtualProductHandler.fetchVProduct(propId);
                Map<String, Integer> effectiveRule = CommonUtil.explainActionEffectiveRule(vProduct.getEffectiveRule());
                if (effectiveRule.get(CommonUtil.RULE_Fight_Add) != null) {
                    fightPlus_prop = effectiveRule.get(CommonUtil.RULE_Fight_Add).intValue();
                }
                if (effectiveRule.get(CommonUtil.RULE_Fight_Percent) != null) {
                    fightPlus_prop = userBase.getUserInfo().getFight().intValue() * effectiveRule.get(CommonUtil.RULE_Fight_Percent).intValue() / 100;
                }
                if (effectiveRule.get(CommonUtil.RULE_Physical_Power_Add) != null) {
                    powerPlus_prop = effectiveRule.get(CommonUtil.RULE_Physical_Power_Add).intValue();
                }
                if (effectiveRule.get(CommonUtil.RULE_Physical_Power_Percent) != null) {
                    powerPlus_prop = userBase.getUserInfo().getPower().intValue() * effectiveRule.get(CommonUtil.RULE_Physical_Power_Percent).intValue() / 100;
                }
                txtFightPlus.setText((fightPlus_friend + fightPlus_prop) + "");
                txtPowerPlus.setText(powerPlus_prop + "");
                txtProp.setText(vProduct.getProductName());
                imgProp.setImageURI(virtualProductHandler.getImageURI(vProduct));
                imgDelProp.setBackgroundResource(R.drawable.running_ready_del);
                imgDelProp.setOnClickListener(delPropListener);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        //todo::do nothing now
    }
}
