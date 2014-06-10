package com.G5432.WalkFun.Run;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.G5432.Entity.UserFriend;
import com.G5432.HttpClient.FriendHandler;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.FontManager;
import com.G5432.Utils.UserUtil;
import com.G5432.WalkFun.R;
import com.G5432.WalkFun.WalkFunBaseActivity;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-5-12
 * Time: 下午4:15
 * To change this template use File | Settings | File Templates.
 */
public class RunFriendSelectActivity extends WalkFunBaseActivity {

    private ListView listRunFriends;
    private Button btnReturn;
    private FriendHandler friendHandler;

    private Map<Integer, Integer> cdDict;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_friend_select);

        friendHandler = new FriendHandler(getHelper());
        //init page data
        initPageUIControl();
        List<UserFriend> fan = friendHandler.fetchFriendFansList(UserUtil.getUserId());
        cdDict = new HashMap<Integer, Integer>();
        for (UserFriend userFriend : fan) {
            int days = CommonUtil.daysBetween(userFriend.getLastWalkTime(), new Date());
            cdDict.put(userFriend.getUserId(), days);
        }
        showFans(fan);
        Typeface typeface = FontManager.wawaw5(this);
        FontManager.changeFonts(this.getWindow().getDecorView(), typeface);
    }

    private void showFans(List<UserFriend> fan) {

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        for (UserFriend userFriend : fan) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            int days = cdDict.get(userFriend.getUserId());
            if (days > 4) {
                map.put("cellType", "canUse");

            } else {
                map.put("cellType", "cantUse");
                map.put("leftDays", (5 - days) + "天");
            }
            map.put("friendId", userFriend.getUserId());
            map.put("friendName", userFriend.getUserName());
            map.put("fight", (int)(userFriend.getFight().doubleValue() + userFriend.getFightPlus().doubleValue()) / 5 + "");
            listItem.add(map);
        }

        RunFriendSelectAdapter listItemAdapter = new RunFriendSelectAdapter(this, listItem, R.layout.run_friend_select_cell,
                new String[]{"friendName", "fight",},
                new int[]{R.id.runFriendSelectCellTxtName, R.id.runFriendSelectCellTxtFight}
        );

        listRunFriends.setAdapter(listItemAdapter);
    }

    private void initPageUIControl() {
        listRunFriends = (ListView) findViewById(R.id.runFriendSelectListFriend);
        btnReturn = (Button) findViewById(R.id.runFriendSelectBtnReturn);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra("friendId", -1);
                //请求代码可以自己设置，这里设置成20
                setResult(20, data);
                //关闭掉这个Activity
                finish();
            }
        });
    }
}
