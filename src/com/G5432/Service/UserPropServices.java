package com.G5432.Service;

import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.UserProp;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-4
 * Time: 上午11:44
 * To change this template use File | Settings | File Templates.
 */
public class UserPropServices {

    private Dao<UserProp, String> userPropDao = null;

    public UserPropServices(DatabaseHelper helper) {
        try {
            userPropDao = helper.getUserPropDao();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveUserPropsToDB(List<UserProp> props) {
        try {
            for (UserProp prop : props) {
                userPropDao.createOrUpdate(prop);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public List<UserProp> fetchUserProps(Integer userId) {
        List<UserProp> props = null;
        try {
            QueryBuilder<UserProp, String> queryBuilder = userPropDao.queryBuilder();
            queryBuilder.where().eq("userId", userId).and().gt("ownNumber", 0);
            queryBuilder.orderBy("productId", true);
            props = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return props;
    }

    public UserProp fetchUserPropByPropId(Integer userId, Integer productId) {
        UserProp prop = null;
        try {
            QueryBuilder<UserProp, String> queryBuilder = userPropDao.queryBuilder();
            queryBuilder.where().eq("userId", userId).and().eq("productId", productId);
            prop = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return prop;
    }
}
