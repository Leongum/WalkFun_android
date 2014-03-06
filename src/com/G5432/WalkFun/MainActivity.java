package com.G5432.WalkFun;

import android.os.Bundle;
import android.widget.*;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.HttpClient.UserHandler;
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

    private UserHandler userHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sex_selection);
        initPageUIControl();
        userHandler = new UserHandler(getHelper());
    }

    private void initPageUIControl() {
    }

    @Override
    public void onBackPressed() {
        //todo::do nothing now
    }
}
