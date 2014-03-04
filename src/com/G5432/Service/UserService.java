package com.G5432.Service;

import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-11
 * Time: 上午10:39
 * To change this template use File | Settings | File Templates.
 */
public class UserService {

    private Dao<UserBase, Integer> userBaseDao = null;

    private Dao<UserInfo, Integer> userInfoDao = null;

    public UserService(DatabaseHelper helper) {
        try {
            userBaseDao = helper.getUserBaseDao();
            userInfoDao = helper.getUserInfoDao();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void saveUserInfoToDB(UserBase userBase){
        try {
            userBaseDao.createOrUpdate(userBase);
            userInfoDao.createOrUpdate(userBase.getUserInfo());
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public UserBase fetchUserById(Integer userId) {
        UserBase userBase = null;
        UserInfo userInfo = null;
        try {
            userBase = userBaseDao.queryForId(userId);
            userInfo = userInfoDao.queryForId(userId);
            userBase.setUserInfo(userInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return userBase;
    }
}