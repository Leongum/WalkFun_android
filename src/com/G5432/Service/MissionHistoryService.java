package com.G5432.Service;

import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.MissionHistory;
import com.G5432.Utils.UserUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-3
 * Time: 下午3:04
 * To change this template use File | Settings | File Templates.
 */
public class MissionHistoryService {

    private Dao<MissionHistory, String> missionHistoryDao = null;

    public MissionHistoryService(DatabaseHelper helper) {
        try {
            missionHistoryDao = helper.getMissionHistoryDao();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveMissionHistoryToDB(MissionHistory missionHistory) {
        try {
            if (missionHistory.getMissionUuid() != null) {
                missionHistoryDao.createOrUpdate(missionHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void saveMissionHistoryToDB(List<MissionHistory> missionHistoryList) {
        try {
            for (MissionHistory missionHistory : missionHistoryList) {
                if (missionHistory.getMissionUuid() != null) {
                    missionHistoryDao.createOrUpdate(missionHistory);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public List<MissionHistory> fetchMissionHistoryByUserId(Integer userId) {
        List<MissionHistory> missionHistoryList = null;
        try {
            QueryBuilder<MissionHistory, String> queryBuilder = missionHistoryDao.queryBuilder();
            queryBuilder.where().eq("userId", userId);
            queryBuilder.orderBy("endTime", false);
            missionHistoryList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return missionHistoryList;
    }

    public List<MissionHistory> fetchUnsyncedMissionHistory(Integer userId) {
        List<MissionHistory> missionHistoryList = null;
        try {
            QueryBuilder<MissionHistory, String> queryBuilder = missionHistoryDao.queryBuilder();
            Where where = queryBuilder.where();
            where.eq("userId", userId).and().isNull("updateTime");
            missionHistoryList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return missionHistoryList;
    }

    public void updateUnsyncedMissionHistories(Integer userId) {
        List<MissionHistory> missionHistoryList = fetchUnsyncedMissionHistory(userId);
        if (missionHistoryList == null || missionHistoryList.size() == 0) {
            return;
        }
        try {
            for (MissionHistory missionHistory : missionHistoryList) {
                missionHistory.setUpdateTime(UserUtil.getSystemTime());
                missionHistoryDao.update(missionHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public MissionHistory fetchMissionHistoryByUuid(String missionUuid) {
        MissionHistory missionHistory = null;
        try {
            missionHistory = missionHistoryDao.queryForId(missionUuid);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return missionHistory;
    }
}
