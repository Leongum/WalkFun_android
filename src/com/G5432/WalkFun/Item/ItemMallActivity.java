package com.G5432.WalkFun.Item;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.*;
import com.G5432.Entity.UserBase;
import com.G5432.Entity.VProduct;
import com.G5432.HttpClient.UserHandler;
import com.G5432.HttpClient.UserPropHandler;
import com.G5432.HttpClient.VirtualProductHandler;
import com.G5432.Utils.ProgressDlgUtil;
import com.G5432.Utils.ToastUtil;
import com.G5432.Utils.UserUtil;
import com.G5432.WalkFun.Main.MainActivity;
import com.G5432.WalkFun.R;
import com.G5432.WalkFun.WalkFunBaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-14
 * Time: 上午11:12
 * To change this template use File | Settings | File Templates.
 */
public class ItemMallActivity extends WalkFunBaseActivity {

    //init UI control
    private Button btnReturn;
    private TextView txtMoney;
    private ListView listProps;

    private TextView txtNumbers;
    private RelativeLayout layoutBuy;
    private Button btnBuy;
    private TextView txtTitle;
    private TextView txtBuyMoney;
    private Button btnDecrease;
    private Button btnIncrease;

    private UserHandler userHandler;
    private UserPropHandler userPropHandler;
    private VirtualProductHandler virtualProductHandler;

    private Integer userId;
    private Integer totalMoney;
    private UserBase userBase;
    private List<VProduct> props;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_mall_main);
        //init page data
        userId = UserUtil.getUserId();
        userHandler = new UserHandler(getHelper());
        virtualProductHandler = new VirtualProductHandler(getHelper());
        userPropHandler = new UserPropHandler(getHelper());
        userBase = userHandler.fetchUser(userId);
        totalMoney = (int)userBase.getUserInfo().getGoldCoin().doubleValue();
        initPageUIControl();
    }

    private void initPageUIControl() {
        btnReturn = (Button) findViewById(R.id.itemMallBtnReturn);
        txtMoney = (TextView) findViewById(R.id.itemMallMainTxtMoney);
        listProps = (ListView) findViewById(R.id.itemMallListProps);
        layoutBuy = (RelativeLayout) findViewById(R.id.itemMallMainLayoutPopup);
        txtNumbers = (TextView) findViewById(R.id.itemMallMainTxtNumber);
        btnBuy = (Button) findViewById(R.id.itemMallMainBtnBuy);
        txtTitle = (TextView) findViewById(R.id.itemMallMainTxtTitle);
        txtBuyMoney = (TextView) findViewById(R.id.itemMallMainPopTxtMoney);
        btnDecrease = (Button) findViewById(R.id.itemMallMainBtnDecrease);
        btnIncrease = (Button) findViewById(R.id.itemMallMainBtnIncrease);

        txtMoney.setText(String.valueOf(totalMoney));

        btnReturn.setOnClickListener(returnListener);

        layoutBuy.setVisibility(View.GONE);
        layoutBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutBuy.setVisibility(View.GONE);
            }
        });

        showProps();
    }

    Handler initHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            SimpleAdapter adapter = new SimpleAdapter(ItemMallActivity.this, (ArrayList<Map<String, Object>>) msg.obj, R.layout.item_mall_item_cell,
                    new String[]{"imageUrl", "propName", "propDesc", "propMoney"},
                    new int[]{R.id.itemMallCellImgIcon, R.id.itemMallCellTxtTitle, R.id.itemMallCellTxtDesc, R.id.itemMallCellTxtMoney});
            listProps.setAdapter(adapter);
            listProps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                    showBuyLayout(props.get(index));

                }
            });
            ProgressDlgUtil.stopProgressDlg();
        }
    };

    private void showProps() {
        ProgressDlgUtil.showProgressDlg(ItemMallActivity.this);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                props = new ArrayList<VProduct>();
                List<VProduct> vProductList = virtualProductHandler.fetchAllVProduct();
                for (VProduct vProduct : vProductList) {
                    if (vProduct.getVirtualPrice() > 0) {
                        props.add(vProduct);
                    }
                }
                ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
                for (int i = 0; i < props.size(); i++) {
                    Map<String, Object> item = new HashMap<String, Object>();
                    item.put("imageUrl", virtualProductHandler.getImageURI(props.get(i)));
                    item.put("propName", props.get(i).getProductName());
                    item.put("propDesc", props.get(i).getProductDescription().replace("|","\n"));
                    item.put("propMoney", String.valueOf(Math.floor(props.get(i).getVirtualPrice())));
                    mData.add(item);
                }

                Message message = new Message();
                message.what = 1;
                message.obj = mData;
                initHandler.sendMessage(message);
            }
        });
        thread.start();
    }

    public void showBuyLayout(final VProduct vProduct) {
        if (vProduct.getVirtualPrice() > totalMoney) {
            ToastUtil.showMessage(getApplicationContext(), "好像买不起哦");
        } else {
            layoutBuy.setVisibility(View.VISIBLE);
            layoutBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    layoutBuy.setVisibility(View.GONE);
                }
            });
            txtBuyMoney.setText("$ " + vProduct.getVirtualPrice());
            txtTitle.setText(vProduct.getProductName());
            btnDecrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer currentNumbers = Integer.valueOf(txtNumbers.getText().toString());
                    if (currentNumbers > 1) {
                        currentNumbers--;
                    }
                    txtNumbers.setText(currentNumbers + "");
                    txtBuyMoney.setText("$ " + currentNumbers * vProduct.getVirtualPrice());
                }
            });
            btnIncrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer currentNumbers = Integer.valueOf(txtNumbers.getText().toString());
                    if ((currentNumbers + 1) * vProduct.getVirtualPrice() < totalMoney) {
                        currentNumbers++;
                    }
                    txtNumbers.setText(currentNumbers + "");
                    txtBuyMoney.setText("$ " + currentNumbers * vProduct.getVirtualPrice());
                }
            });
            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer currentNumbers = Integer.valueOf(txtNumbers.getText().toString());
                    userPropHandler.buyUserProps(vProduct.getProductId(), currentNumbers, buyHandler);
                }
            });
        }
    }

    Handler buyHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                ToastUtil.showMessage(getApplicationContext(), "购买成功");
                userBase = userHandler.fetchUser(userId);
                totalMoney = (int)userBase.getUserInfo().getGoldCoin().doubleValue();
                txtMoney.setText(String.valueOf(totalMoney));
                layoutBuy.setVisibility(View.GONE);
            } else {
                ToastUtil.showMessage(getApplicationContext(), "网络通信失败");
            }
        }
    };

    private View.OnClickListener returnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //finish();
            Intent intent = new Intent();
            intent.putExtra("pageId", 0);
            intent.setClass(ItemMallActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onBackPressed() {

    }
}
