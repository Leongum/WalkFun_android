package com.G5432.WalkFun.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.G5432.HttpClient.UserHandler;
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
    }


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
