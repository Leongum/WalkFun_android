package com.G5432.WalkFun.Setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.G5432.Entity.Action;
import com.G5432.HttpClient.FriendHandler;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.UserUtil;
import com.G5432.WalkFun.R;
import com.G5432.WalkFun.WalkFunBaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-5-7
 * Time: 上午11:58
 * To change this template use File | Settings | File Templates.
 */
public class ActionHistoryMainActivity extends WalkFunBaseActivity {

    private Button btnReturn;
    private ListView listActionHistory;


    private FriendHandler friendHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_history_main);

        friendHandler = new FriendHandler(getHelper());

        List<Action> actions = friendHandler.fetchUserActions(UserUtil.getUserId());
        //init page data
        initPageUIControl();

        showActions(actions);
    }

    private void showActions(List<Action> actions) {
        ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < actions.size(); i++) {
            Action action = actions.get(i);
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("time", CommonUtil.parseDateToStringOnlyMins(actions.get(i).getUpdateTime()));
            item.put("desc", action.getActionFromId().intValue() == UserUtil.getUserId().intValue() ? "你" : action.getActionFromName() + action.getActionName());
            mData.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, mData, R.layout.action_histoy_cell,
                new String[]{"time", "desc"},
                new int[]{R.id.actionHistoryCellTxtTime, R.id.actionHistoryCellTxtDesc});
        listActionHistory.setAdapter(adapter);
    }

    private void initPageUIControl() {
        UserUtil.saveActionUpdate(0);
        btnReturn = (Button) findViewById(R.id.actionHistoryBtnReturn);
        listActionHistory = (ListView) findViewById(R.id.actionHistoryListActions);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
}
