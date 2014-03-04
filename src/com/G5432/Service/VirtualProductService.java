package com.G5432.Service;

import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.VProduct;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-3
 * Time: 下午3:08
 * To change this template use File | Settings | File Templates.
 */
public class VirtualProductService {

    private Dao<VProduct, Integer> vProductDao = null;

    public VirtualProductService(DatabaseHelper helper) {
        try {
            vProductDao = helper.getvProductDao();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveVProductsToDB(List<VProduct> vProductList) {
        try {
            for (VProduct vProduct : vProductList) {
                vProductDao.createOrUpdate(vProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public List<VProduct> fetchAllVProducts() {
        List<VProduct> vProductList = null;

        try {
            QueryBuilder<VProduct, Integer> queryBuilder = vProductDao.queryBuilder();
            queryBuilder.orderBy("productId", true);
            vProductList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return vProductList;
    }

    public VProduct fetchVProduct(Integer productId) {
        VProduct vProduct = null;
        try {
            QueryBuilder<VProduct, Integer> queryBuilder = vProductDao.queryBuilder();
            queryBuilder.where().eq("productId", productId);
            vProduct = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return vProduct;
    }
}
