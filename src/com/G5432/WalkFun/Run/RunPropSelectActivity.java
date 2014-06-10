package com.G5432.WalkFun.Run;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.G5432.Entity.Enum.PropFlagEnum;
import com.G5432.Entity.UserProp;
import com.G5432.Entity.VProduct;
import com.G5432.HttpClient.UserPropHandler;
import com.G5432.HttpClient.VirtualProductHandler;
import com.G5432.Utils.FontManager;
import com.G5432.Utils.ToastUtil;
import com.G5432.Utils.UserUtil;
import com.G5432.WalkFun.R;
import com.G5432.WalkFun.WalkFunBaseActivity;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-5-14
 * Time: 上午11:53
 * To change this template use File | Settings | File Templates.
 */
public class RunPropSelectActivity extends WalkFunBaseActivity {

    private ListView listRunProps;
    private Button btnReturn;
    private UserPropHandler userPropHandler;
    private VirtualProductHandler virtualProductHandler;

    private List<VProduct> vProducts;
    private List<UserProp> userProps;

    private AlertDialog.Builder propToUseBuilder = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_prop_select);

        userPropHandler = new UserPropHandler(getHelper());
        virtualProductHandler = new VirtualProductHandler(getHelper());
        //init page data
        initPageUIControl();
        List<UserProp> userPropList = userPropHandler.fetchUserProps(UserUtil.getUserId());
        vProducts = new ArrayList<VProduct>();
        userProps = new ArrayList<UserProp>();
        for (UserProp userProp : userPropList) {
            VProduct vProduct = virtualProductHandler.fetchVProduct(userProp.getProductId());
            if (vProduct.getPropFlag().intValue() == PropFlagEnum.PROPFLAGFIGHT.ordinal()) {
                userProps.add(userProp);
                vProducts.add(vProduct);
            }
        }
        showUserProps(vProducts, userProps);
        Typeface typeface = FontManager.wawaw5(this);
        FontManager.changeFonts(this.getWindow().getDecorView(), typeface);
    }

    private void showUserProps(final List<VProduct> vProducts, final List<UserProp> userProps) {

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < vProducts.size(); i++) {
            VProduct vProduct = vProducts.get(i);
            UserProp userProp = userProps.get(i);
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("propName", vProduct.getProductName());
            map.put("propQuantity", "X" + userProp.getOwnNumber());
            String[] descList = vProduct.getProductDescription().split("\\|");
            if (descList.length > 1) {
                map.put("propDesc", descList[1]);
            } else {
                map.put("propDesc", "");
            }
            map.put("propImg", virtualProductHandler.getImageURI(vProduct));
            listItem.add(map);
        }

        SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem, R.layout.run_prop_select_cell,
                new String[]{"propName", "propQuantity", "propDesc", "propImg",},
                new int[]{R.id.runPropSelectCellTxtName, R.id.runPropSelectCellTxtNum, R.id.runPropSelectCellTxtDesc, R.id.runPropSelectCellImgIcon}
        );

        listRunProps.setAdapter(listItemAdapter);
        listRunProps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                final int propIndex = index;
                propToUseBuilder.setTitle("使用道具");
                String effectiveString = "";
                String[] descList = vProducts.get(propIndex).getProductDescription().split("\\|");
                if (descList.length > 1) {
                    effectiveString = descList[1];
                }

                propToUseBuilder.setMessage("确定要使用【" + vProducts.get(propIndex).getProductName() + "】\\n\\n" + effectiveString + "\\n？");
                propToUseBuilder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent data = new Intent();
                        data.putExtra("propId", vProducts.get(propIndex).getProductId());
                        setResult(30, data);
                        //关闭掉这个Activity
                        finish();
                    }
                });
                propToUseBuilder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
                AlertDialog ad = propToUseBuilder.create();
                ad.show();
            }
        }

        );
    }

    private void initPageUIControl() {
        listRunProps = (ListView) findViewById(R.id.runPropSelectListProps);
        btnReturn = (Button) findViewById(R.id.runPropSelectBtnReturn);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra("propId", -1);
                //请求代码可以自己设置，这里设置成20
                setResult(30, data);
                //关闭掉这个Activity
                finish();
            }
        });

        propToUseBuilder = new AlertDialog.Builder(this);
    }
}
