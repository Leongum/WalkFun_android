package com.G5432.WalkFun.History;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.*;
import com.G5432.Entity.ActionDefinition;
import com.G5432.Entity.FightDefinition;
import com.G5432.Entity.RunningHistory;
import com.G5432.Entity.WalkEvent;
import com.G5432.HttpClient.RunningHistoryHandler;
import com.G5432.HttpClient.SystemHandler;
import com.G5432.Utils.CommonUtil;
import com.G5432.WalkFun.R;
import com.G5432.WalkFun.WalkFunBaseActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-24
 * Time: 上午11:02
 * To change this template use File | Settings | File Templates.
 */
public class HistoryRunDetailActivity extends WalkFunBaseActivity {

    //init UI control
    private Button btnMap;
    private ListView listAction;
    private TextView txtTime;
    private TextView txtSteps;
    private TextView txtReward;

    private RunningHistoryHandler runningHistoryHandler;
    private SystemHandler systemHandler;

    private RunningHistory history;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();

    String runUuid = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        runUuid = extras.getString("runUuid");
        setContentView(R.layout.history_run_detail_main);
        //init page data
        runningHistoryHandler = new RunningHistoryHandler(getHelper());
        systemHandler = new SystemHandler(getHelper());
        history = runningHistoryHandler.fetchRunHistoryByRunId(runUuid);
        initPageUIControl();
    }

    private void initPageUIControl() {
        btnMap = (Button) findViewById(R.id.historyRunDetailBtnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryRunDetailActivity.this, HistoryRunMapActivity.class);
                intent.putExtra("runUuid", runUuid);
                startActivity(intent);
            }
        });
        listAction = (ListView) findViewById(R.id.historyRunDetailListAction);
        txtTime = (TextView) findViewById(R.id.historyRunDetailTxtTime);
        txtSteps = (TextView) findViewById(R.id.historyRunDetailTxtReward);
        txtReward = (TextView) findViewById(R.id.historyRunDetailTxtReward);
        showActions(history);
    }

    private void showActions(final RunningHistory historyDetail) {
        txtTime.setText(CommonUtil.transSecondToStandardFormat(history.getDuration()));
        txtSteps.setText("约" + history.getSteps() + "步");
        if (history.getActionIds() != null) {
            List<WalkEvent> walkEvents = gson.fromJson(historyDetail.getActionIds(), new TypeToken<List<WalkEvent>>() {
            }.getType());
            ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < walkEvents.size(); i++) {
                Map<String, Object> item = new HashMap<String, Object>();
                WalkEvent walkEvent = walkEvents.get(i);
                item.put("actionTime", "大约在" + CommonUtil.transSecondToStandardFormat(walkEvent.getTimes()) + "的时候");
                if (walkEvent.geteType().equalsIgnoreCase(CommonUtil.Type_Action)) {
                    ActionDefinition actionDefinition = systemHandler.fetchActionDefineById(walkEvent.geteId());
                    item.put("actionDesc", actionDefinition.getActionName());
                    item.put("actionReward", "获得：" + actionDefinition.getActionAttribute());
                } else if (walkEvent.geteType().equalsIgnoreCase(CommonUtil.Type_Fight)) {
                    FightDefinition fightDefinition = systemHandler.fetchFightDefineById(walkEvent.geteId());
                    if (walkEvent.geteWin() == 1) {
                        item.put("actionDesc", fightDefinition.getfWin());
                        item.put("actionReward", "获得：" + fightDefinition.getWinGot());
                    } else {
                        item.put("actionDesc", fightDefinition.getfLoose());
                        item.put("actionReward", "什么也没有获得~~");
                    }
                }
                mData.add(item);
            }

            SimpleAdapter adapter = new SimpleAdapter(this, mData, R.layout.history_run_detail_cell,
                    new String[]{"actionTime", "actionDesc", "actionReward"},
                    new int[]{R.id.historyRunDetailCellTxtTime, R.id.historyRunDetailCellTxtAction, R.id.historyRunDetailCellTxtReward});
            listAction.setAdapter(adapter);
        }
        if (historyDetail.getPropGet() != null) {
            String[] propList = historyDetail.getPropGet().split("\\|");
            if (propList.length == 2) {
                String reward = "";
                if (propList[0].length() > 0) {
                    reward = reward + "共获得" + propList[0] + "场战斗胜利\n";
                }
                if (propList[1].length() > 0) {
                    reward = reward + "获得道具：" + propList[1] + "\n";
                }
                txtReward.setText(reward);
            }
        }
    }
}
