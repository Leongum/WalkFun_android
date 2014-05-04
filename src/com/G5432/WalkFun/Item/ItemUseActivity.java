package com.G5432.WalkFun.Item;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;
import com.G5432.Entity.*;
import com.G5432.HttpClient.*;
import com.G5432.Utils.*;
import com.G5432.WalkFun.Main.MainActivity;
import com.G5432.WalkFun.R;
import com.G5432.WalkFun.WalkFunBaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-13
 * Time: 下午2:02
 * To change this template use File | Settings | File Templates.
 */
public class ItemUseActivity extends WalkFunBaseActivity {

    //init UI
    private Button btnReturn;
    private ListView listFriend;

    private UserHandler userHandler;
    private FriendHandler friendHandler;
    private SystemHandler systemHandler;

    private AlertDialog.Builder friendToUseBuilder = null;

    private Integer propId;

    private ActionDefinition actionDefinition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        propId = extras.getInt("propId");
        setContentView(R.layout.item_use);
        //init page data
        userHandler = new UserHandler(getHelper());
        friendHandler = new FriendHandler(getHelper());
        systemHandler = new SystemHandler(getHelper());
        actionDefinition = systemHandler.fetchActionDefineByPropId(propId);
        initPageUIControl();
    }

    private void initPageUIControl() {
        btnReturn = (Button) findViewById(R.id.itemUseBtnReturn);
        listFriend = (ListView) findViewById(R.id.itemUseListFriend);

        friendToUseBuilder = new AlertDialog.Builder(this);

        btnReturn.setOnClickListener(returnListener);

        List<UserFriend> fanEach = friendHandler.fetchFriendEachFansList(UserUtil.getUserId());
        UserFriend me = new UserFriend();
        UserBase userBase = userHandler.fetchUser(UserUtil.getUserId());
        me.setSex(userBase.getSex());
        me.setUserName("自己");
        me.setUserId(userBase.getUserId());
        me.setFriendId(userBase.getUserId());
        me.setLevel(userBase.getUserInfo().getLevel());
        me.setUserTitle(userBase.getUserInfo().getUserTitle());
        fanEach.add(0, me);
        showFriendList(fanEach);
    }

    private void showFriendList(final List<UserFriend> friendList) {
        ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
        //add friend
        for (int i = 0; i < friendList.size(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("userName", friendList.get(i).getUserName());
            mData.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, mData, R.layout.item_use_friend_cell,
                new String[]{"userName"},
                new int[]{R.id.itemUseFriendTxtName});
        listFriend.setAdapter(adapter);
        listFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                final int friendIndex = index;
                friendToUseBuilder.setTitle("选择目标");
                friendToUseBuilder.setMessage("确定对【" + friendList.get(friendIndex).getUserName() + "】使用吗？");
                friendToUseBuilder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (actionDefinition != null) {
                            friendHandler.createAction(actionDefinition.getActionId(), friendList.get(friendIndex).getFriendId(),
                                    friendList.get(friendIndex).getUserName(), actionHandler);
                        } else {
                            systemHandler.syncActionDefine(new Handler());
                            ToastUtil.showMessage(getApplicationContext(), "网络通信失败");
                        }
                    }
                });
                friendToUseBuilder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                AlertDialog ad = friendToUseBuilder.create();
                ad.show();
            }
        });
    }

    Handler actionHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                ToastUtil.showMessage(getApplicationContext(), "使用成功");
            } else {
                ToastUtil.showMessage(getApplicationContext(), "网络通信失败");
            }
        }
    };

    private View.OnClickListener returnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            Intent intent = new Intent();
            intent.putExtra("pageId", 0);
            intent.setClass(ItemUseActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onBackPressed() {

    }
}
