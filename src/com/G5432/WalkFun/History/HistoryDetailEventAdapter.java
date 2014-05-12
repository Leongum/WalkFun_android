package com.G5432.WalkFun.History;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.G5432.Utils.FontManager;
import com.G5432.WalkFun.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-5-8
 * Time: 下午2:36
 * To change this template use File | Settings | File Templates.
 */
public class HistoryDetailEventAdapter extends SimpleAdapter {

    private LayoutInflater mInflater;
    private ArrayList<Map<String, Object>> mData;
    private Typeface typeface;

    public HistoryDetailEventAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.mInflater = LayoutInflater.from(context);
        this.mData = (ArrayList<Map<String, Object>>) data;
        this.typeface = FontManager.wawaw5(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.history_run_detail_cell, null);
            FontManager.changeFonts(convertView, typeface);
            viewHolder.imgFight = (ImageView) convertView.findViewById(R.id.historyRunDetailCellImgFight);
            viewHolder.txtTime = (TextView) convertView.findViewById(R.id.historyRunDetailCellTxtTime);
            viewHolder.txtDesc = (TextView) convertView.findViewById(R.id.historyRunDetailCellTxtAction);
            viewHolder.txtCost = (TextView) convertView.findViewById(R.id.historyRunDetailCellExperience);
            viewHolder.txtGot = (TextView) convertView.findViewById(R.id.historyRunDetailCellTxtReward);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String itemType = (String) mData.get(position).get("itemType");
        if (itemType.equalsIgnoreCase("event")) {
            viewHolder.txtTime.setVisibility(View.VISIBLE);
            viewHolder.imgFight.setVisibility(View.GONE);
            viewHolder.txtCost.setVisibility(View.GONE);
        } else if(itemType.equalsIgnoreCase("start_event")){
            viewHolder.imgFight.setVisibility(View.GONE);
            viewHolder.txtCost.setVisibility(View.GONE);
            viewHolder.txtTime.setVisibility(View.GONE);
        }else {
            viewHolder.txtTime.setVisibility(View.VISIBLE);
            viewHolder.imgFight.setVisibility(View.VISIBLE);
            viewHolder.txtCost.setVisibility(View.VISIBLE);
            viewHolder.imgFight.setBackgroundResource((Integer) mData.get(position).get("fightLevel"));
            viewHolder.txtCost.setText((String) mData.get(position).get("actionCost"));
        }
        viewHolder.txtTime.setText((String) mData.get(position).get("actionTime"));
        viewHolder.txtDesc.setText((String) mData.get(position).get("actionDesc"));
        viewHolder.txtGot.setText((String) mData.get(position).get("actionReward"));
        return convertView;
    }

    //提取出来方便点
    public class ViewHolder {
        public ImageView imgFight;
        public TextView txtTime;
        public TextView txtDesc;
        public TextView txtCost;
        public TextView txtGot;
    }
}
