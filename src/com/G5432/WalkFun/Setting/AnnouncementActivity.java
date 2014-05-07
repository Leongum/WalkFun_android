package com.G5432.WalkFun.Setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.G5432.Utils.UserUtil;
import com.G5432.WalkFun.R;
import com.G5432.WalkFun.WalkFunBaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-5-7
 * Time: 下午3:30
 * To change this template use File | Settings | File Templates.
 */
public class AnnouncementActivity extends WalkFunBaseActivity {

    private Button btnReturn;
    private TextView txtDesc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_announcement);

        initPageUIControl();
    }

    private void initPageUIControl() {
        UserUtil.saveNoticeUpdate(0);
        btnReturn = (Button) findViewById(R.id.setAnnouncementBtnReturn);
        txtDesc = (TextView) findViewById(R.id.setAnnouncementTxtDesc);

        txtDesc.setText(UserUtil.getVersionDesc());

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
