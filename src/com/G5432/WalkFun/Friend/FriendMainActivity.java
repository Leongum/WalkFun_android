package com.G5432.WalkFun.Friend;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.HttpClient.FriendHandler;
import com.G5432.HttpClient.UserHandler;
import com.G5432.Utils.*;
import com.G5432.WalkFun.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-6
 * Time: 下午9:29
 * To change this template use File | Settings | File Templates.
 */
public class FriendMainActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    //UI control
    private Button btnRemove;
    private Button btnAdd;
    private Button btnFollow;
    private Button btnFan;
    private Button btnOK;
    private ListView listFriend;

    private FriendHandler friendHandler;
    private List<UserFriend> follows;
    private List<UserFriend> fans;
    private List<UserFriend> fanEach;

    private Boolean followClick = true;
    private Boolean fanClick = false;

    private Integer userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_main);
        //init page data
        userId = UserUtil.getUserId();
        friendHandler = new FriendHandler(getHelper());
        fans = friendHandler.fetchFriendFansList(userId);
        follows = friendHandler.fetchFriendFollowsList(userId);
        fanEach = friendHandler.fetchFriendEachFansList(userId);
        initPageUIControl();
    }

    private void initPageUIControl() {
        btnRemove = (Button) findViewById(R.id.friendmainBtnRemove);
        btnAdd = (Button) findViewById(R.id.friendmainBtnAdd);
        btnFollow = (Button) findViewById(R.id.friendmainBtnFollow);
        btnFan = (Button) findViewById(R.id.friendmainBtnFan);
        btnOK = (Button) findViewById(R.id.friendmainBtnOK);
        listFriend = (ListView) findViewById(R.id.friendmainListFriend);
        btnOK.setVisibility(View.GONE);
        btnFollow.setBackgroundColor(Color.RED);
        showFriendList(follows);

        btnFan.setOnClickListener(fanListener);
        btnFollow.setOnClickListener(followListener);
        btnAdd.setOnClickListener(addListener);
    }

    private void showFriendList(final List<UserFriend> friendList) {
        ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < friendList.size(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            if (friendList.get(i).getSex().equalsIgnoreCase("女")) {
                item.put("sex", R.drawable.female);
            } else {
                item.put("sex", R.drawable.male);
            }
            item.put("userName", friendList.get(i).getUserName());
            item.put("level", "Lv." + friendList.get(i).getLevel());
            item.put("userTitle", friendList.get(i).getUserTitle());
            mData.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, mData, R.layout.friend_cell,
                new String[]{"userName", "sex", "level", "userTitle"},
                new int[]{R.id.friendcellTxtUserName, R.id.friendcellImgSex, R.id.friendcellTxtLevel, R.id.friendcellTxtUserTitle});
        listFriend.setAdapter(adapter);
        listFriend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ToastUtil.showMessage(getApplicationContext(), friendList.get(i).getFriendId().toString());
            }
        });
    }

    private View.OnClickListener followListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (followClick) {
                followClick = false;
                btnFollow.setBackgroundColor(Color.BLUE);
                if (fanClick) {
                    showFriendList(fans);
                } else {
                    showFriendList(new ArrayList<UserFriend>());
                }
            } else {
                followClick = true;
                btnFollow.setBackgroundColor(Color.RED);
                if (fanClick) {
                    showFriendList(fanEach);
                } else {
                    showFriendList(follows);
                }
            }
        }
    };

    private View.OnClickListener fanListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (fanClick) {
                fanClick = false;
                btnFan.setBackgroundColor(Color.BLUE);
                if (followClick) {
                    showFriendList(follows);
                } else {
                    showFriendList(new ArrayList<UserFriend>());
                }

            } else {
                fanClick = true;
                btnFan.setBackgroundColor(Color.RED);
                if (followClick) {
                    showFriendList(fanEach);
                } else {
                    showFriendList(fans);
                }
            }
        }
    };

    private View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(FriendMainActivity.this, FriendSearchActivity.class);
            startActivity(intent);
        }
    };

//    @Override
//    public void onBackPressed() {
//        //todo::do nothing now
//    }
}
