package com.G5432.WalkFun;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.Mission;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-1
 * Time: 下午3:42
 * To change this template use File | Settings | File Templates.
 */
public class TestViewActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testview);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                List<Mission> missionList = (List<Mission>)msg.obj;
                Log.i("tag",missionList.toString());
            }
        }
    };


    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


}
