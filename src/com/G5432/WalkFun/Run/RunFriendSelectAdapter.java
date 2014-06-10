package com.G5432.WalkFun.Run;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.G5432.Utils.FontManager;
import com.G5432.WalkFun.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-5-14
 * Time: 上午10:26
 * To change this template use File | Settings | File Templates.
 */
public class RunFriendSelectAdapter extends SimpleAdapter {

    private Context context;
    private Typeface typeface;

    public RunFriendSelectAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.typeface = FontManager.wawaw5(context);
    }

    @Override
    public boolean isEnabled(int position) {
        Map<String, Object> currentData = (HashMap<String, Object>) getItem(position);
        String cellType = (String) currentData.get("cellType");
        if (cellType.equalsIgnoreCase("cantUse")) {
            return false;
        }
        return super.isEnabled(position);
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        View view = convertView;
        final Map<String, Object> currentData = (HashMap<String, Object>) getItem(position);
        String cellType = (String) currentData.get("cellType");
        if (cellType.equalsIgnoreCase("cantUse")) {
            view = LayoutInflater.from(context).inflate(R.layout.run_friend_select_disable_cell, null);
            TextView txtName = (TextView) view.findViewById(R.id.runFriendSelectDisCellTxtName);
            TextView txtFight = (TextView) view.findViewById(R.id.runFriendSelectDisCellTxtFight);
            TextView txtWait = (TextView) view.findViewById(R.id.runFriendSelectDisCellTxtWait);
            txtName.setText((String) currentData.get("friendName"));
            txtFight.setText((String) currentData.get("fight"));
            txtWait.setText((String) currentData.get("leftDays"));
            FontManager.changeFonts(view, typeface);
        } else if (cellType.equalsIgnoreCase("canUse")) {
            view = LayoutInflater.from(context).inflate(R.layout.run_friend_select_cell, null);
            TextView txtName = (TextView) view.findViewById(R.id.runFriendSelectCellTxtName);
            TextView txtFight = (TextView) view.findViewById(R.id.runFriendSelectCellTxtFight);
            txtName.setText((String) currentData.get("friendName"));
            txtFight.setText((String) currentData.get("fight"));
            final Integer friendId = (Integer) currentData.get("friendId");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent data = new Intent();
                    data.putExtra("friendId", friendId);
                    //请求代码可以自己设置，这里设置成20
                    ((Activity) context).setResult(20, data);
                    //关闭掉这个Activity
                    ((Activity) context).finish();
                }
            });
            FontManager.changeFonts(view, typeface);
        }
        return view;
    }
}
