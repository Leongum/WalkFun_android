package com.G5432.WalkFun.Friend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.G5432.Entity.Enum.FollowStatusEnum;
import com.G5432.Entity.SearchUserInfo;
import com.G5432.HttpClient.FriendHandler;
import com.G5432.HttpClient.UserHandler;
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
 * Date: 14-3-7
 * Time: 上午10:58
 * To change this template use File | Settings | File Templates.
 */
public class FriendSearchActivity extends WalkFunBaseActivity {

    //init UI control
    private Button btnReturn;
    private Button btnNext;
    private Button btnSearch;
    private EditText searchNickName;
    private ListView listSearch;

    private UserHandler userHandler;
    private FriendHandler friendHandler;
    private Integer currentPage = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_search);
        //init page data
        userHandler = new UserHandler(getHelper());
        friendHandler = new FriendHandler(getHelper());
        initPageUIControl();
    }

    private void initPageUIControl() {
        btnReturn = (Button) findViewById(R.id.friendSearchBtnReturn);
        btnNext = (Button) findViewById(R.id.friendSearchBtnNext);
        searchNickName = (EditText) findViewById(R.id.friendSearchTxtInput);
        listSearch = (ListView) findViewById(R.id.friendSearchList);
        btnSearch = (Button) findViewById(R.id.friendsearchBtnSearch);

        friendHandler.fetchRecommendFriends(0, recommendHandler);
        btnReturn.setOnClickListener(backListener);
        btnSearch.setOnClickListener(searchListener);
        btnNext.setOnClickListener(nextListener);
    }

    public View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
            Intent intent = new Intent();
            intent.putExtra("pageId", 2);
            intent.setClass(FriendSearchActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    public View.OnClickListener nextListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            currentPage += 1;
            friendHandler.fetchRecommendFriends(currentPage, recommendHandler);
        }
    };

    public View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            userHandler.searchFriend(searchNickName.getText().toString(), recommendHandler);
        }
    };

    Handler recommendHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                List<SearchUserInfo> recommendList = (List<SearchUserInfo>) msg.obj;
                if (recommendList.size() > 0) {
                    showRecommendFriendList(recommendList);
                } else {
                    ToastUtil.showMessage(getApplicationContext(), "没有了~");
                }
            } else {
                ToastUtil.showMessage(getApplicationContext(), "网络获取失败");
            }
        }
    };

    private void showRecommendFriendList(final List<SearchUserInfo> friendList) {
        ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < friendList.size(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("userId", friendList.get(i).getUserId());
            if (friendList.get(i).getSex().equalsIgnoreCase("女")) {
                item.put("sex", R.drawable.female);
            } else {
                item.put("sex", R.drawable.male);
            }
            item.put("userName", friendList.get(i).getNickName());
            item.put("level", "Lv." + friendList.get(i).getLevel());
            item.put("userTitle", friendList.get(i).getUserTitle());
            FollowStatusEnum friendStatus = friendHandler.getFollowStatus(friendList.get(i).getUserId());
            if (friendStatus == FollowStatusEnum.FOLLOWED) {
                item.put("follow", "取消关注");
            } else {
                item.put("follow", "关注");
            }
            mData.add(item);
        }

        FriendSearchAdapter adapter = new FriendSearchAdapter(this, mData, R.layout.friend_search_cell,
                new String[]{"userName", "sex", "level", "userTitle", "follow"},
                new int[]{R.id.friendSearchCellName, R.id.friendSearchCellSex,
                        R.id.friendSearchCellLevel, R.id.friendSearchCellTitle, R.id.friendSearchCellFollow});
        listSearch.setAdapter(adapter);
    }

    public class FriendSearchAdapter extends SimpleAdapter {

        private LayoutInflater mInflater;
        private ArrayList<Map<String, Object>> mData;

        public FriendSearchAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
            this.mInflater = LayoutInflater.from(context);
            this.mData = (ArrayList<Map<String, Object>>) data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.friend_search_cell, null);
                viewHolder.userName = (TextView) convertView.findViewById(R.id.friendSearchCellName);
                viewHolder.level = (TextView) convertView.findViewById(R.id.friendSearchCellLevel);
                viewHolder.userTitle = (TextView) convertView.findViewById(R.id.friendSearchCellTitle);
                viewHolder.sex = (ImageView) convertView.findViewById(R.id.friendSearchCellSex);
                viewHolder.follow = (Button) convertView.findViewById(R.id.friendSearchCellFollow);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.userName.setText((String) mData.get(position).get("userName"));
            viewHolder.level.setText((String) mData.get(position).get("level"));
            viewHolder.userTitle.setText((String) mData.get(position).get("userTitle"));
            viewHolder.sex.setBackgroundDrawable(getResources().getDrawable((Integer) mData.get(position).get("sex")));
            viewHolder.follow.setText((String) mData.get(position).get("follow"));

            FollowListener myListener = new FollowListener((Integer) mData.get(position).get("userId"), viewHolder.follow);
            viewHolder.follow.setOnClickListener(myListener);
            return convertView;
        }

        //提取出来方便点
        public class ViewHolder {
            public TextView userName;
            public ImageView sex;
            public TextView level;
            public TextView userTitle;
            public Button follow;
        }
    }

    private class FollowListener implements View.OnClickListener {
        Integer userId;
        Button btnFollow;
        String friendStatusString = "关注";

        public FollowListener(Integer id, Button follow) {
            userId = id;
            btnFollow = follow;
        }

        @Override
        public void onClick(View v) {
            FollowStatusEnum friendStatus = friendHandler.getFollowStatus(userId);
            if (friendStatus == FollowStatusEnum.FOLLOWED) {
                friendStatusString = "关注";
                friendHandler.deFollowFriend(userId, changeFriendStatusHandler);
            } else {
                friendStatusString = "取消关注";
                friendHandler.followFriend(userId, changeFriendStatusHandler);
            }
        }

        Handler changeFriendStatusHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    btnFollow.setText(friendStatusString);
                } else {
                    ToastUtil.showMessage(getApplicationContext(), "网络通信失败");
                }
            }
        };
    }

    @Override
    public void onBackPressed() {

    }

}