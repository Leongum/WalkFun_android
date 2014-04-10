package com.G5432.WalkFun.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.UserBase;
import com.G5432.HttpClient.*;
import com.G5432.Utils.*;
import com.G5432.WalkFun.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.UMSsoHandler;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.db.OauthHelper;
import com.umeng.socialize.exception.SocializeException;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-5
 * Time: 下午1:35
 * To change this template use File | Settings | File Templates.
 */
public class LoginActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    //page ui control
    private Button btnLogin;
    private Button btnRegister;
    private RelativeLayout layoutMain;
    private RelativeLayout layoutFeild;
    private EditText txtUserName;
    private EditText txtPassword;
    private EditText txtNickName;
    private Button btnReturn;
    private Button btnSubmit;
    private Button btnQQ;
    private Button btnWeibo;
    private Boolean isLogin = true;
    private Boolean loginSuccessful = true;
    //Handler
    private UserHandler userHandler;
    private FriendHandler friendHandler;
    private MissionHistoryHandler missionHistoryHandler;
    private RunningHistoryHandler runningHistoryHandler;
    private UserPropHandler userPropHandler;

    private Boolean jumped = false;

    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initPageUIControl();
        userHandler = new UserHandler(getHelper());
        friendHandler = new FriendHandler(getHelper());
        runningHistoryHandler = new RunningHistoryHandler(getHelper());
        missionHistoryHandler = new MissionHistoryHandler(getHelper());
        userPropHandler = new UserPropHandler(getHelper());
    }

    private void initPageUIControl() {
        btnLogin = (Button) findViewById(R.id.loginBtnLogin);
        btnRegister = (Button) findViewById(R.id.loginBtnRegister);
        btnSubmit = (Button) findViewById(R.id.loginBtnSubmit);
        btnReturn = (Button) findViewById(R.id.loginBtnReturn);
        btnQQ = (Button) findViewById(R.id.loginBtnQQ);
        btnWeibo = (Button) findViewById(R.id.loginBtnWeibo);
        txtPassword = (EditText) findViewById(R.id.loginTxtPassword);
        txtUserName = (EditText) findViewById(R.id.loginTxtUserName);
        txtNickName = (EditText) findViewById(R.id.loginTxtNickName);
        layoutMain = (RelativeLayout) findViewById(R.id.loginLayoutMain);
        layoutFeild = (RelativeLayout) findViewById(R.id.loginLayoutFeild);

        btnWeibo.setOnClickListener(weiboLoginListener);
        btnLogin.setOnClickListener(loginListener);
        btnRegister.setOnClickListener(registerListener);
        btnReturn.setOnClickListener(returnListener);
        btnSubmit.setOnClickListener(sumbitListener);
    }

    private View.OnClickListener weiboLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (OauthHelper.isAuthenticated(getApplication(), SHARE_MEDIA.SINA)) {

            } else {
                mController.doOauthVerify(LoginActivity.this, SHARE_MEDIA.SINA, new SocializeListeners.UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA platform) {

                    }

                    @Override
                    public void onError(SocializeException e, SHARE_MEDIA platform) {
                        //Toast.makeText(mContext, "授权错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(Bundle value, SHARE_MEDIA platform) {
                        //Toast.makeText(mContext, "授权完成", Toast.LENGTH_SHORT).show();
                        //获取相关授权信息或者跳转到自定义的分享编辑页面
                        String uid = value.getString("uid");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA platform) {
                        //Toast.makeText(mContext, "授权取消", Toast.LENGTH_SHORT).show();
                    }
                } );
            }
        }
    };

    private View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isLogin = true;
            layoutMain.setVisibility(View.GONE);
            layoutFeild.setVisibility(View.VISIBLE);
            txtNickName.setVisibility(View.GONE);
        }
    };

    private View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isLogin = false;
            layoutMain.setVisibility(View.GONE);
            layoutFeild.setVisibility(View.VISIBLE);
            txtNickName.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener returnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            layoutFeild.setVisibility(View.GONE);
            layoutMain.setVisibility(View.VISIBLE);
        }
    };

    private View.OnClickListener sumbitListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!isLegalInput()) return;
            if (isLogin) {
                userHandler.syncUserInfoByLogin(txtUserName.getText().toString(), txtPassword.getText().toString(), loginHandler);
            } else {
                UserBase userBase = new UserBase();
                userBase.setUserName(txtUserName.getText().toString());
                userBase.setPassword(txtPassword.getText().toString());
                userBase.setNickName(txtNickName.getText().toString());
                userBase.setPlatformInfo("android");
                //todo:: add device id
                userHandler.registerUser(userBase, registerHandler);
            }
        }
    };

    private Boolean isLegalInput() {
        if (txtUserName.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty()) {
            ToastUtil.showMessage(getApplicationContext(), "密码和昵称不能不为空");
            return false;
        }
        if (!isLogin && txtNickName.getText().toString().isEmpty()) {
            ToastUtil.showMessage(getApplicationContext(), "昵称不能为空");
            return false;
        }
        if (!isLogin && txtPassword.getText().toString().length() < 6) {
            ToastUtil.showMessage(getApplicationContext(), "密码太短");
            return false;
        }
        return true;

    }

    Handler loginHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                loginSuccessful = true;
                GlobalSyncStatus.setUserBaseSync();
                userPropHandler.syncUserProps(loginSuccessHandler);
                friendHandler.syncActions(loginSuccessHandler);
                friendHandler.syncFriendSort(loginSuccessHandler);
                friendHandler.syncFriends(loginSuccessHandler);
                missionHistoryHandler.syncMissionHistories(loginSuccessHandler);
                runningHistoryHandler.syncRunningHistories(loginSuccessHandler);
            } else {
                ToastUtil.showMessage(getApplicationContext(), "登录失败，请输入正确的用户名密码");
            }
        }
    };


    Handler loginSuccessHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                loginSuccessful = false;
            }
            if (GlobalSyncStatus.userPropsSynced &&
                    GlobalSyncStatus.userFriendSynced &&
                    GlobalSyncStatus.userFriendSortSynced &&
                    GlobalSyncStatus.userActionSynced &&
                    GlobalSyncStatus.missionHistorySynced &&
                    GlobalSyncStatus.historySynced) {
                if (loginSuccessful && !jumped) {
                    jumped = true;
                    Intent intent = new Intent();
                    intent.putExtra("pageId", 1);
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (!loginSuccessful) {
                    userHandler.logout();
                    ToastUtil.showMessage(getApplicationContext(), "网络连接错误，登录失败。");
                }
            }
        }
    };

    Handler registerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, SetSelectionActivity.class);
                startActivity(intent);
            } else {
                ToastUtil.showMessage(getApplicationContext(), "注册失败，用户名已存在");
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        //todo::do nothing now
    }
}
