package com.G5432.WalkFun.Item;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.G5432.Entity.*;
import com.G5432.HttpClient.*;
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
 * Date: 14-3-12
 * Time: 下午3:50
 * To change this template use File | Settings | File Templates.
 */
public class ItemMainActivity extends WalkFunBaseActivity {

    //init UI control
    private Button btnMall;
    private TextView txtMoney;
    private GridView gridProps;
    private RelativeLayout layoutUse;
    private Button btnUse;
    private TextView txtUseTitle;
    private ImageView imgUse;

    private UserHandler userHandler;
    private UserPropHandler userPropHandler;
    private VirtualProductHandler virtualProductHandler;

    private Integer userId;
    private UserBase userBase;
    private List<UserProp> userProps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_main);
        //init page data
        userId = UserUtil.getUserId();
        userHandler = new UserHandler(getHelper());
        userPropHandler = new UserPropHandler(getHelper());
        virtualProductHandler = new VirtualProductHandler(getHelper());
        userBase = userHandler.fetchUser(userId);
        userProps = userPropHandler.fetchUserProps(userId);
        initPageUIControl();
    }

    private void initPageUIControl() {
        btnMall = (Button) findViewById(R.id.itemMainBtnMall);
        txtMoney = (TextView) findViewById(R.id.itemMainTxtMoney);
        gridProps = (GridView) findViewById(R.id.itemMainGridProps);

        layoutUse = (RelativeLayout) findViewById(R.id.itemMainLayoutUse);
        btnUse = (Button) findViewById(R.id.itemMainBtnUse);
        txtUseTitle = (TextView) findViewById(R.id.itemMainTxtUseTitle);
        imgUse = (ImageView) findViewById(R.id.itemMainImgUseShow);

        btnMall.setOnClickListener(mallClickListener);

        txtMoney.setText(String.valueOf(Math.floor(userBase.getUserInfo().getGoldCoin())));

        showUserProps(userProps);
    }

    private void showUserProps(final List<UserProp> userProps) {
        ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < userProps.size(); i++) {
            VProduct vProduct = virtualProductHandler.fetchVProduct(userProps.get(i).getProductId());
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("imageUrl", virtualProductHandler.getImageURI(vProduct));
            item.put("numbers", "X " + userProps.get(i).getOwnNumber());
            mData.add(item);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, mData, R.layout.item_prop_cell,
                new String[]{"imageUrl", "numbers"},
                new int[]{R.id.itemCellImgProp, R.id.itemCellTxtQuantity});
        gridProps.setAdapter(adapter);
        gridProps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                showUseLayout(userProps.get(index).getProductId());
            }
        });
    }

    public void showUseLayout(final Integer propId) {
        VProduct vProduct = virtualProductHandler.fetchVProduct(propId);
        layoutUse.setVisibility(View.VISIBLE);
        layoutUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutUse.setVisibility(View.GONE);
            }
        });
        txtUseTitle.setText(vProduct.getProductDescription());
        imgUse.setImageURI(virtualProductHandler.getImageURI(vProduct));
        btnUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemMainActivity.this, ItemUseActivity.class);
                intent.putExtra("propId", propId);
                startActivity(intent);
            }
        });
    }

    private View.OnClickListener mallClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(ItemMainActivity.this, ItemMallActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onBackPressed() {
        //todo::do nothing now
    }
}
