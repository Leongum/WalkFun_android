package com.G5432.WalkFun.History;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.G5432.Entity.RunningHistory;
import com.G5432.Entity.UserBase;
import com.G5432.HttpClient.RunningHistoryHandler;
import com.G5432.HttpClient.UserHandler;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.FontManager;
import com.G5432.Utils.UserUtil;
import com.G5432.WalkFun.R;
import com.G5432.WalkFun.WalkFunBaseActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-17
 * Time: 下午2:38
 * To change this template use File | Settings | File Templates.
 */
public class HistoryRunMainActivity extends WalkFunBaseActivity {

    //init UI control
    private Button btnReturn;
    private ListView listHistory;
    private TextView textTime;
    private TextView textStep;

    private RunningHistoryHandler runningHistoryHandler;
    private UserHandler userHandler;

    private UserBase userBase;

    private List<RunningHistory> historyList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_run_main);
        //init page data
        runningHistoryHandler = new RunningHistoryHandler(getHelper());
        userHandler = new UserHandler(getHelper());
        historyList = runningHistoryHandler.fetchRunHistoryByUserId(UserUtil.getUserId());
        userBase = userHandler.fetchUser(UserUtil.getUserId());
        initPageUIControl();
        Typeface typeface = FontManager.wawaw5(this);
        FontManager.changeFonts(this.getWindow().getDecorView(), typeface);
    }

    private void initPageUIControl() {
        btnReturn = (Button) findViewById(R.id.historyRunMainBtnReturn);
        listHistory = (ListView) findViewById(R.id.historyRunMainList);
        textTime.setText(CommonUtil.transSecondToStandardFormat(userBase.getUserInfo().getTotalWalkingTimes()));
        textStep.setText(userBase.getUserInfo().getTotalSteps() + "步");
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        showHistoryLists(historyList);
    }

    private void showHistoryLists(List<RunningHistory> historyList) {
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        Date lastDate = new Date();
        for (RunningHistory runningHistory : historyList) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("cellType", "context");
            map.put("runUuid", runningHistory.getRunUuid());
            if (!runningHistory.getMissionDate().equals(lastDate)) {
                HashMap<String, Object> titleMap = new HashMap<String, Object>();
                titleMap.put("cellType", "title");
                titleMap.put("dateTime", CommonUtil.parseDateToStringOnlyDate(runningHistory.getMissionDate()));
                listItem.add(titleMap);
                lastDate = runningHistory.getMissionDate();
            }
            map.put("runSteps", runningHistory.getSteps() + "");
            map.put("runTime", CommonUtil.transSecondToStandardFormat(runningHistory.getDuration()));
            listItem.add(map);
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("cellType", "tail");
        listItem.add(map);

        HistoryRunCellAdapter listItemAdapter = new HistoryRunCellAdapter(this, listItem, R.layout.history_run_cell,
                new String[]{"runTime", "runSteps",},
                new int[]{R.id.historyRunCellTxtTime, R.id.historyRunCellTxtSteps}
        );

        listHistory.setAdapter(listItemAdapter);
    }
}
