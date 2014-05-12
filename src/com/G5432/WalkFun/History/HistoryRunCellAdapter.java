package com.G5432.WalkFun.History;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.*;
import com.G5432.Utils.FontManager;
import com.G5432.WalkFun.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-17
 * Time: 下午4:38
 * To change this template use File | Settings | File Templates.
 */
public class HistoryRunCellAdapter extends SimpleAdapter {

    private Context context;
    private Typeface typeface;

    public HistoryRunCellAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.typeface = FontManager.wawaw5(context);
    }

    @Override
    public boolean isEnabled(int position) {
        Map<String, Object> currentData = (HashMap<String, Object>) getItem(position);
        String cellType = (String) currentData.get("cellType");
        if (cellType.equalsIgnoreCase("title") || cellType.equalsIgnoreCase("tail")) {
            return false;
        }
        return super.isEnabled(position);
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        View view = convertView;
        final Map<String, Object>  currentData = (HashMap<String, Object>) getItem(position);
        String cellType = (String) currentData.get("cellType");
        if (cellType.equalsIgnoreCase("title") ) {
            view = LayoutInflater.from(context).inflate(R.layout.history_run_cell_title, null);
            TextView txtTitle = (TextView) view.findViewById(R.id.historyRunCellTitleTxtDate);
            txtTitle.setText((String) currentData.get("dateTime"));
            FontManager.changeFonts(view, typeface);
        } else if (cellType.equalsIgnoreCase("context")){
            view = LayoutInflater.from(context).inflate(R.layout.history_run_cell, null);
            TextView txtRunTime = (TextView) view.findViewById(R.id.historyRunCellTxtTime);
            txtRunTime.setText((String) currentData.get("runTime"));
            TextView txtSteps = (TextView) view.findViewById(R.id.historyRunCellTxtSteps);
            txtSteps.setText((String) currentData.get("runSteps"));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HistoryRunDetailActivity.class);
                    intent.putExtra("runUuid", currentData.get("runUuid").toString());
                    context.startActivity(intent);
                }
            });
            FontManager.changeFonts(view, typeface);
        } else if(cellType.equalsIgnoreCase("tail")){
            view = LayoutInflater.from(context).inflate(R.layout.history_run_cell_bottom, null);
        }
        return view;
    }

}
