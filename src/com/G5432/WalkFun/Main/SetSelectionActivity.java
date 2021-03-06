package com.G5432.WalkFun.Main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;
import com.G5432.Entity.UserBase;
import com.G5432.HttpClient.*;
import com.G5432.Utils.ToastUtil;
import com.G5432.Utils.UserUtil;
import com.G5432.WalkFun.R;
import com.G5432.WalkFun.WalkFunBaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-6
 * Time: 上午10:17
 * To change this template use File | Settings | File Templates.
 */
public class SetSelectionActivity extends WalkFunBaseActivity {

    //init UI Control
    private ImageView imgWoman;
    private ImageView imgMan;

    private UserHandler userHandler;
    private String sex = "男";

    private AlertDialog.Builder sexSetBuilder = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sex_selection);
        initPageUIControl();
        userHandler = new UserHandler(getHelper());
    }

    private void initPageUIControl() {
        imgWoman = (ImageView) findViewById(R.id.setSelectionImgWoman);
        imgMan = (ImageView) findViewById(R.id.setSelectionImgMan);

        sexSetBuilder = new AlertDialog.Builder(this);

        sexSetBuilder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (sex.equalsIgnoreCase("女")) {
                    UserBase userBase = userHandler.fetchUser(UserUtil.getUserId());
                    userBase.setSex(sex);
                    userHandler.updateUserBase(userBase, sexUpdateHandler);
                } else {
                    sexUpdateHandler.sendEmptyMessage(1);
                }

            }
        });
        sexSetBuilder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });

        imgMan.setOnClickListener(manListener);
        imgWoman.setOnClickListener(womanListener);
    }

    Handler sexUpdateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Intent intent = new Intent();
                intent.putExtra("pageId", 1);
                intent.setClass(SetSelectionActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                ToastUtil.showMessage(getApplicationContext(), "性别设置失败，请重试");
            }
        }
    };

    private View.OnClickListener womanListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sex = "女";
            sexSetBuilder.setMessage("确定选【女】吗？");
            AlertDialog ad = sexSetBuilder.create();
            ad.show();
        }
    };

    private View.OnClickListener manListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sex = "男";
            sexSetBuilder.setMessage("确定选【男】吗？");
            AlertDialog ad = sexSetBuilder.create();
            ad.show();
        }
    };

    @Override
    public void onBackPressed() {
        //todo::do nothing now
    }
}
