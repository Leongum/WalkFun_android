package com.G5432.WalkFun.Setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.G5432.WalkFun.R;
import com.G5432.WalkFun.WalkFunBaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-5-7
 * Time: 下午4:04
 * To change this template use File | Settings | File Templates.
 */
public class AboutUsActivity extends WalkFunBaseActivity {

    private Button btnReturn;
    private TextView txtVersion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_about_us);

        initPageUIControl();
    }

    private void initPageUIControl() {
        btnReturn = (Button) findViewById(R.id.setAboutBtnReturn);
        txtVersion = (TextView) findViewById(R.id.setAboutTxtVersion);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
