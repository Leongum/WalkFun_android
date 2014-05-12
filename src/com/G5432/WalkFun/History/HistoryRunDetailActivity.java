package com.G5432.WalkFun.History;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.*;
import com.G5432.Entity.*;
import com.G5432.HttpClient.FriendHandler;
import com.G5432.HttpClient.RunningHistoryHandler;
import com.G5432.HttpClient.SystemHandler;
import com.G5432.HttpClient.VirtualProductHandler;
import com.G5432.Utils.CommonUtil;
import com.G5432.Utils.FontManager;
import com.G5432.Utils.UserUtil;
import com.G5432.WalkFun.R;
import com.G5432.WalkFun.WalkFunBaseActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.text.MessageFormat;
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
    private Button btnReturn;
    private ListView listAction;
    private TextView txtDate;
    private TextView txtTime;
    private TextView txtSteps;
    private TextView txtFight;
    private GridView gridGots;

    private RunningHistoryHandler runningHistoryHandler;
    private FriendHandler friendHandler;
    private SystemHandler systemHandler;
    private VirtualProductHandler virtualProductHandler;

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
        friendHandler = new FriendHandler(getHelper());
        virtualProductHandler = new VirtualProductHandler(getHelper());
        initPageUIControl();
        Typeface typeface = FontManager.wawaw5(this);
        FontManager.changeFonts(this.getWindow().getDecorView(),typeface);
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

        btnReturn = (Button) findViewById(R.id.historyRunDetailBtnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        listAction = (ListView) findViewById(R.id.historyRunDetailListAction);
        txtDate = (TextView) findViewById(R.id.historyRunDetailTxtDate);
        txtTime = (TextView) findViewById(R.id.historyRunDetailTxtTime);
        txtSteps = (TextView) findViewById(R.id.historyRunDetailTxtSteps);
        txtFight = (TextView) findViewById(R.id.historyRunDetailTxtFight);
        gridGots = (GridView) findViewById(R.id.historyRunDetailGridWin);

        showActions(history);
    }

    private void showActions(final RunningHistory historyDetail) {
        txtDate.setText(CommonUtil.parseDateToStringOnlyDate(historyDetail.getMissionDate()));
        txtTime.setText(CommonUtil.transSecondToStandardFormat(historyDetail.getDuration()));
        txtSteps.setText("约" + historyDetail.getSteps() + "步");
        if (history.getActionIds() != null) {
            List<WalkEvent> walkEvents = gson.fromJson(historyDetail.getActionIds(), new TypeToken<List<WalkEvent>>() {
            }.getType());
            ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
            for (int i = 0; i < walkEvents.size(); i++) {
                Map<String, Object> item = new HashMap<String, Object>();
                WalkEvent walkEvent = walkEvents.get(i);
                item.put("actionTime", CommonUtil.transSecondToStandardFormat(walkEvent.getTimes()) + "的时候");
                item.put("fightLevel", R.drawable.fight_icon_1);
                item.put("actionCost", "");
                if (walkEvent.geteType().equalsIgnoreCase(CommonUtil.RULE_Type_Action)) {
                    if (walkEvent.geteId().intValue() != CommonUtil.COIN_ACTION_ID.intValue()) {
                        ActionDefinition actionDefinition = systemHandler.fetchActionDefineById(walkEvent.geteId());
                        item.put("itemType", "event");
                        item.put("actionDesc", actionDefinition.getActionName());
                        if (actionDefinition.getActionAttribute() == null || actionDefinition.getActionAttribute().equalsIgnoreCase("")) {
                            item.put("actionReward", "");
                        } else {
                            item.put("actionReward", "获得：" + actionDefinition.getActionAttribute());
                        }
                        mData.add(item);
                    }
                } else if (walkEvent.geteType().equalsIgnoreCase(CommonUtil.RULE_Type_Fight)) {
                    FightDefinition fightDefinition = systemHandler.fetchFightDefineById(walkEvent.geteId());
                    item.put("itemType", "fight");
                    item.put("fightLevel", getFightIconWithMonsterLevel(fightDefinition.getmLevel()));
                    String actionDesc = fightDefinition.getfName().split("。")[0];
                    if (walkEvent.geteWin() > 0) {
                        String[] wins = fightDefinition.getfWin().split("\\|");
                        actionDesc = actionDesc + wins[walkEvent.geteWin() / 10];
                        if (walkEvent.geteWin().intValue() % 10 > 1 && fightDefinition.getWinGot() != null) {
                            item.put("actionReward", "获得：" + fightDefinition.getWinGot());
                        } else {
                            item.put("actionReward", "未获得战利品");
                        }
                    } else {
                        String[] lose = fightDefinition.getfLoose().split("\\|");
                        actionDesc = actionDesc + lose[Math.abs(walkEvent.geteWin() / 10)];
                        item.put("actionReward", "未获得战利品");
                    }
                    item.put("actionDesc", actionDesc + fightDefinition.getfName().split("。")[1]);
                    double powerCost;
                    if (walkEvent.geteWin().intValue() % 10 == 3) {
                        powerCost = Math.round(fightDefinition.getbPower().doubleValue() / 2);
                    } else {
                        powerCost = Math.round(fightDefinition.getbPower().doubleValue());
                    }
                    item.put("actionCost", "经验值+ " + (int) fightDefinition.getbExperience().doubleValue() + "体力-" + (int) powerCost);
                    mData.add(item);
                } else if (walkEvent.geteType().equalsIgnoreCase(CommonUtil.RULE_Type_Fight_Friend)) {
                    item.put("itemType", "fight");
                    item.put("fightLevel", R.drawable.fight_icon_2);
                    UserFriend fightFriend = friendHandler.fetchFriendByIds(UserUtil.getUserId(), walkEvent.geteId());
                    String actionDesc = "";
                    if (walkEvent.geteWin() > 0) {
                        String[] wins = CommonUtil.SENTENCE_FRIEND_FIGHT_WIN.split("\\|");
                        actionDesc = MessageFormat.format(wins[walkEvent.geteWin() / 10], fightFriend.getUserName());
                        item.put("actionReward", "获得：荣誉+1");
                    } else {
                        String[] lose = CommonUtil.SENTENCE_FRIEND_FIGHT_LOSE.split("\\|");
                        actionDesc = MessageFormat.format(lose[Math.abs(walkEvent.geteWin() / 10)], fightFriend.getUserName());
                        ;
                        item.put("actionReward", "未获得战利品");
                    }
                    item.put("actionDesc", actionDesc + "（等级" + (int) fightFriend.getLevel().doubleValue() + "战斗）");
                    item.put("actionCost", "体力-" + walkEvent.getPower());
                    mData.add(item);
                } else if (walkEvent.geteType().equalsIgnoreCase(CommonUtil.RULE_Type_Start)) {
                    item.put("itemType", "start_event");
                    if (historyDetail.getFriendName() != null) {
                        String[] sentenceList = CommonUtil.SENTENCE_START_WALKING_WITH.split("\\|");
                        item.put("actionDesc", MessageFormat.format(sentenceList[walkEvent.geteId().intValue()], historyDetail.getFriendName()));
                    } else {
                        String[] sentenceList = CommonUtil.SENTENCE_START_WALKING_ALONE.split("\\|");
                        item.put("actionDesc", sentenceList[walkEvent.geteId().intValue()]);
                    }
                    item.put("actionReward", "一切看起来都那么美好～");
                    item.put("actionTime", "");
                    mData.add(item);
                }
            }

            HistoryDetailEventAdapter adapter = new HistoryDetailEventAdapter(this, mData, R.layout.history_run_detail_cell,
                    new String[]{"actionTime", "fightLevel", "actionDesc", "actionReward"},
                    new int[]{R.id.historyRunDetailCellTxtTime, R.id.historyRunDetailCellImgFight, R.id.historyRunDetailCellTxtAction, R.id.historyRunDetailCellTxtReward});
            listAction.setAdapter(adapter);
        }

        if (historyDetail.getPropGet() != null) {
            String[] detailList = historyDetail.getPropGet().split("-");
            if (detailList.length > 1) {
                txtFight.setText(detailList[0]);
                Map<Integer, Integer> props = CommonUtil.explainPropHaveRule(detailList[1]);
                showUserProps(props);
            }
        }
    }

    private void showUserProps(final Map<Integer, Integer> userProps) {
        ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
        List<Integer> propIds = new ArrayList<Integer>();
        for (Integer propId : userProps.keySet()) {
            VProduct vProduct = virtualProductHandler.fetchVProduct(propId);
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("imageUrl", virtualProductHandler.getImageURI(vProduct));
            item.put("numbers", "X " + userProps.get(propId));
            mData.add(item);
            propIds.add(propId);
        }
        ViewGroup.LayoutParams params = gridGots.getLayoutParams();
        params.width = 95 * propIds.size();
        gridGots.setLayoutParams(params);
        gridGots.setNumColumns(propIds.size());
        final List<Integer> fPropIds = propIds;
        SimpleAdapter adapter = new SimpleAdapter(this, mData, R.layout.history_run_detail_prop_cell,
                new String[]{"imageUrl", "numbers"},
                new int[]{R.id.historyRunDetailPropCellImgProp, R.id.historyRunDetailPropCellTxtQuantity});
        gridGots.setAdapter(adapter);
        gridGots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                showUseLayout(fPropIds.get(index));
            }
        });
    }

    public void showUseLayout(final Integer propId) {
        VProduct vProduct = virtualProductHandler.fetchVProduct(propId);

        final RelativeLayout layoutUse = (RelativeLayout) findViewById(R.id.historyRunDetailLayoutProp);
        TextView txtUseTitle = (TextView) findViewById(R.id.historyRunDetailTxtPropName);
        TextView txtUseAttribute = (TextView) findViewById(R.id.historyRunDetailTxtPropAttribute);
        TextView txtUseDesc = (TextView) findViewById(R.id.historyRunDetailTxtPropDesc);
        ImageView imgUse = (ImageView) findViewById(R.id.historyRunDetailImgPropIcon);
        layoutUse.setVisibility(View.VISIBLE);
        layoutUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutUse.setVisibility(View.GONE);
            }
        });
        txtUseTitle.setText(vProduct.getProductName());
        txtUseAttribute.setText(vProduct.getProductDescription().split("\\|")[1]);
        txtUseDesc.setText(vProduct.getProductDescription().split("\\|")[0]);
        imgUse.setImageURI(virtualProductHandler.getImageURI(vProduct));
    }

    private int getFightIconWithMonsterLevel(Integer monsterLevel) {
        if (monsterLevel.intValue() == 1) {
            return R.drawable.fight_icon_1;
        } else if (monsterLevel.intValue() == 2) {
            return R.drawable.fight_icon_2;
        } else if (monsterLevel.intValue() == 3) {
            return R.drawable.fight_icon_3;
        } else if (monsterLevel.intValue() == 4) {
            return R.drawable.fight_icon_4;
        } else if (monsterLevel.intValue() == 5) {
            return R.drawable.fight_icon_5;
        }
        return R.drawable.fight_icon_1;
    }
}
