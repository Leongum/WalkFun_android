package com.G5432.WalkFun.History;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.BMapInfo;
import com.G5432.Entity.RunningHistory;
import com.G5432.HttpClient.RunningHistoryHandler;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.WalkFunApplication;
import com.G5432.WalkFun.R;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.RouteOverlay;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-24
 * Time: 下午2:39
 * To change this template use File | Settings | File Templates.
 */
public class HistoryRunMapActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    //init UI
    private Button btnReturn;
    private MapView mMapView;

    private WalkFunApplication bMapApp = null;
    private MapController mMapController = null;
    private BMapInfo mapInfo;

    private RunningHistoryHandler runningHistoryHandler;
    private RunningHistory history;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bMapApp = (WalkFunApplication) this.getApplication();
        Bundle extras = getIntent().getExtras();
        String runUuid = extras.getString("runUuid");
        setContentView(R.layout.history_run_map);
        //init page data
        runningHistoryHandler = new RunningHistoryHandler(getHelper());
        history = runningHistoryHandler.fetchRunHistoryByRunId(runUuid);
        initPageUIControl();
    }

    private void initPageUIControl() {
        btnReturn = (Button) findViewById(R.id.historyRunMapBtnReturn);
        mMapView = (MapView) findViewById(R.id.historyRunMapViewMap);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        showMap();
    }

    private void showMap() {
        mapInfo = CommonUtil.getRoutesFromString(history.getMissionRoute());

        RouteOverlay routeOverlay = new RouteOverlay(HistoryRunMapActivity.this, mMapView);
        routeOverlay.setData(mapInfo.getRoute());

        mMapController = mMapView.getController();

        mMapView.getController().enableClick(true);
        //向地图添加构造好的RouteOverlay
        mMapView.getOverlays().add(routeOverlay);

        //执行刷新使生效
        mMapView.refresh();

        mapHandler.sendEmptyMessageDelayed(1, 800);
    }

    // 定义Handler
    Handler mapHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                mMapController.zoomToSpan(mapInfo.getSpanLat(), mapInfo.getSpanLon());
                mMapController.animateTo(mapInfo.getCenterPoint());
            }
        }
    };
}
