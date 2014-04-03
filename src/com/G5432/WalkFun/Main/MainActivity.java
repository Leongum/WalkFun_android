package com.G5432.WalkFun.Main;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.*;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.HttpClient.UserHandler;
import com.G5432.Utils.ProgressDlgUtil;
import com.G5432.WalkFun.Friend.FriendMainActivity;
import com.G5432.WalkFun.Item.ItemMainActivity;
import com.G5432.WalkFun.R;
import com.G5432.WalkFun.User.UserMainActivity;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.util.ArrayList;


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
    private ViewPager viewPager;

    private UserHandler userHandler;
    LocalActivityManager manager = null;
    private Integer TITLE_HEIGHT = 130;
    private Integer currIndex = 1;

    private View userMain;
    private View itemMain;
    private View friendMain;

    private View userMainTitle;
    private View itemMainTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        currIndex = extras.getInt("pageId");
        setContentView(R.layout.main);
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);
        userHandler = new UserHandler(getHelper());

        initPageUIControl();
    }

    private void initPageUIControl() {
        btnSync = (Button) findViewById(R.id.mainBtnSync);
        btnSetting = (Button) findViewById(R.id.mainBtnSet);

        btnSync.setOnClickListener(syncListener);
        btnSetting.setOnClickListener(settingListener);

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

        ((RelativeLayout) itemMainTitle.getParent()).removeView(itemMainTitle);
        ((RelativeLayout) userMainTitle.getParent()).removeView(userMainTitle);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 0;
        addContentView(itemMainTitle, params);
        addContentView(userMainTitle, params);

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
        } else if (pageIndex == 1) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = -TITLE_HEIGHT;
            itemMainTitle.setLayoutParams(params);
        }
    }

    private View.OnClickListener syncListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //todo:: do sync things
            ProgressDlgUtil.showProgressDlg(MainActivity.this);
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

    @Override
    public void onBackPressed() {
        //todo::do nothing now
    }
}
