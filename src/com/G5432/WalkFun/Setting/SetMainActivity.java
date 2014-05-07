package com.G5432.WalkFun.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.G5432.HttpClient.UserHandler;
import com.G5432.Utils.UserUtil;
import com.G5432.WalkFun.Main.LoginActivity;
import com.G5432.WalkFun.Main.MainActivity;
import com.G5432.WalkFun.R;
import com.G5432.WalkFun.WalkFunBaseActivity;
import com.umeng.fb.FeedbackAgent;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-4-15
 * Time: 下午2:33
 * To change this template use File | Settings | File Templates.
 */
public class SetMainActivity extends WalkFunBaseActivity {

    //init UI
    private Button btnReturn;
    private Button btnLogout;
    private Button btnWriteUs;
    private TextView txtActions;
    private Button btnActionDetails;
    private TextView txtNotice;
    private Button btnNoticeDetails;
    private TextView txtAbout;
    private TextView txtWalkfun;
    private Button btnAboutDetails;

    private ImageView actionUpdated;
    private ImageView noticeUpdated;

    private UserHandler userHandler;

    private FeedbackAgent agent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_main);
        userHandler = new UserHandler(getHelper());
        agent = new FeedbackAgent(this);
        agent.sync();
        //init page data
        initPageUIControl();
    }

    private void initPageUIControl() {
        btnReturn = (Button) findViewById(R.id.setMainBtnReturn);
        btnLogout = (Button) findViewById(R.id.setMainBtnLogout);
        btnWriteUs = (Button) findViewById(R.id.setMainBtnWriteUs);

        btnLogout.setOnClickListener(logoutListener);
        btnReturn.setOnClickListener(returnListener);
        btnWriteUs.setOnClickListener(writeUsListener);

        txtActions = (TextView) findViewById(R.id.setMainTxtActions);
        txtNotice = (TextView) findViewById(R.id.setMainTxtNotice);
        txtAbout = (TextView) findViewById(R.id.setMainTxtAbout);
        txtWalkfun = (TextView) findViewById(R.id.setMainTxtAboutWalkfun);
        btnActionDetails = (Button) findViewById(R.id.setMainBtnActions);
        btnNoticeDetails = (Button) findViewById(R.id.setMainBtnNotice);
        btnAboutDetails = (Button) findViewById(R.id.setMainBtnAbout);

        txtActions.setOnClickListener(actionsListener);
        txtNotice.setOnClickListener(noticeListener);
        txtAbout.setOnClickListener(aboutListener);
        txtWalkfun.setOnClickListener(aboutListener);
        btnActionDetails.setOnClickListener(actionsListener);
        btnNoticeDetails.setOnClickListener(noticeListener);
        btnAboutDetails.setOnClickListener(aboutListener);

        actionUpdated = (ImageView) findViewById(R.id.setMainImgActionUpdated);
        noticeUpdated = (ImageView) findViewById(R.id.setMainImgNoticeUpdated);

        if (UserUtil.getNoticeUpdate().intValue() == 1) {
            noticeUpdated.setVisibility(View.VISIBLE);
        }

        if (UserUtil.getActionUpdate().intValue() == 1) {
            actionUpdated.setVisibility(View.VISIBLE);
        }
    }

    private View.OnClickListener aboutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(SetMainActivity.this, AboutUsActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener noticeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(SetMainActivity.this, AnnouncementActivity.class);
            startActivity(intent);
        }
    };


    private View.OnClickListener actionsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(SetMainActivity.this, ActionHistoryMainActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener logoutListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            userHandler.logout();
            Intent intent = new Intent();
            intent.setClass(SetMainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener returnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //finish();
            Intent intent = new Intent();
            intent.putExtra("pageId", 1);
            intent.setClass(SetMainActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener writeUsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            agent.startFeedbackActivity();
        }
    };
}
