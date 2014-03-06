package com.G5432.HttpClient;

import android.os.Handler;
import android.util.Log;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.Service.VirtualProductService;
import com.G5432.Utils.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-3
 * Time: 下午3:08
 * To change this template use File | Settings | File Templates.
 */
public class VirtualProductHandler {

    private HttpClientHelper httpClientHelper = new HttpClientHelper();

    private VirtualProductService virtualProductService = null;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").excludeFieldsWithoutExposeAnnotation().create();

    public VirtualProductHandler(DatabaseHelper helper) {
        this.virtualProductService = new VirtualProductService(helper);
    }

    /**
     * 同步virtual product 返回empty message handler
     *
     * @param handler
     */
    public void syncVProducts(final Handler handler) {
        GlobalSyncStatus.productSynced = false;
        String lastUpdateTime = UserUtil.getLastUpdateTime("VProductsUpdateTime");
        String url = CommonUtil.getUrl(MessageFormat.format(Constant.VIRTUAL_PRODUCT_GET_URL, lastUpdateTime));
        httpClientHelper.get(url, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, String response) {
                Log.i(this.getClass().getName(), response);
                GlobalSyncStatus.productSynced = true;
                if (statusCode == 200 || statusCode == 204) {
                    List<VProduct> vProductList = gson.fromJson(response, new TypeToken<List<VProduct>>() {
                    }.getType());
                    virtualProductService.saveVProductsToDB(vProductList);
                    UserUtil.saveLastUpdateTime("VProductsUpdateTime");
                    handler.sendEmptyMessage(1);
                } else {
                    Log.e(this.getClass().getName(), response);
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                GlobalSyncStatus.productSynced = true;
                Log.e(this.getClass().getName(), error.getMessage());
                handler.sendEmptyMessage(0);
            }
        });
    }

    /**
     * 获取所有vproduct的列表
     *
     * @return virtual product 列表
     */
    public List<VProduct> fetchAllVProduct() {
        return virtualProductService.fetchAllVProducts();
    }

    /**
     * 根据product id 获取单个product 信息
     *
     * @return virtual product
     */
    public VProduct fetchVProduct(Integer prodcutId) {
        return virtualProductService.fetchVProduct(prodcutId);
    }
}
