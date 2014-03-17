package com.G5432.HttpClient;

import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.Service.VirtualProductService;
import com.G5432.Utils.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;

import java.io.File;
import java.io.FileOutputStream;
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

    private File imagePath = null;

    public VirtualProductHandler(DatabaseHelper helper) {
        this.virtualProductService = new VirtualProductService(helper);

        imagePath = new File(Environment.getExternalStorageDirectory(), "CrazyFoodieCache");
        if (!imagePath.exists()) {
            imagePath.mkdirs();
        }
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
                if (statusCode == 200 || statusCode == 204) {
                    List<VProduct> vProductList = gson.fromJson(response, new TypeToken<List<VProduct>>() {
                    }.getType());
                    virtualProductService.saveVProductsToDB(vProductList);
                    UserUtil.saveLastUpdateTime("VProductsUpdateTime");
                    getImageURI(vProductList);
                    GlobalSyncStatus.productSynced = true;
                    handler.sendEmptyMessage(1);
                } else {
                    Log.e(this.getClass().getName(), response);
                    GlobalSyncStatus.productSynced = true;
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
    public VProduct fetchVProduct(Integer productId) {
        return virtualProductService.fetchVProduct(productId);
    }

    /*
    * 从网络上获取图片，如果图片在本地存在的话就直接拿，如果不存在再去服务器上下载图片
    * 这里的path是图片的地址
    */
    public void getImageURI(List<VProduct> vProductList) {
        for (int i = 0; i < vProductList.size(); i++) {
            VProduct vProduct = vProductList.get(i);
            doImageSync(vProduct.getPicLink());
        }
    }

    public void doImageSync(final String imageUrl) {
        String[] allowedTypes = new String[]{"image/png", "image/jpeg"};
        httpClientHelper.get(imageUrl, new BinaryHttpResponseHandler(allowedTypes) {
            @Override
            public void onSuccess(int statusCode, byte[] response) {
                if (statusCode == 200 || statusCode == 204) {
                    super.onSuccess(response);
                    try {
                        final String name = getFileNameFromUrl(imageUrl);
                        File file = new File(imagePath, name);
                        FileOutputStream oStream = new FileOutputStream(file);
                        oStream.write(response);
                        oStream.flush();
                        oStream.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Throwable error, String content) {
                Log.e(this.getClass().getName(), error.getMessage());
            }
        });
    }

    public Uri getImageURI(VProduct vProduct) {
        try {
            String imageUrlPath = vProduct.getPicLink();
            final String name = getFileNameFromUrl(imageUrlPath);
            File file = new File(imagePath, name);
            // 如果图片存在本地缓存目录，则不去服务器下载
            if (file.exists()) {
                return Uri.fromFile(file); //Uri.fromFile(path)这个方法能得到文件的URI
            } else {
                doImageSync(imageUrlPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getFileNameFromUrl(String url) {
        String[] ruleArray = url.split("/");
        return ruleArray[ruleArray.length - 1];
    }
}
