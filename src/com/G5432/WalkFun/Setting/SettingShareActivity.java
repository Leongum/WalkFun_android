package com.G5432.WalkFun.Setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.G5432.Utils.ToastUtil;
import com.G5432.WalkFun.R;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-28
 * Time: 下午2:48
 * To change this template use File | Settings | File Templates.
 */
public class SettingShareActivity extends Activity{

    private Button btnWeibo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_share_main);

        initPageUIControl();
    }

    private void initPageUIControl() {
        btnWeibo = (Button) findViewById(R.id.settingShareMainBtnWeibo);

        btnWeibo.setOnClickListener(weiboListener);
    }

    View.OnClickListener weiboListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ToastUtil.showMessage(getApplicationContext(),"这是微博分享");
        }
    };
}
