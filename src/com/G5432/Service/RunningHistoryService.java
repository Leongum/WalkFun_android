package com.G5432.Service;

import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.Utils.UserUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-15
 * Time: 下午12:08
 * To change this template use File | Settings | File Templates.
 */
public class RunningHistoryService {

    private Dao<RunningHistory, String> runningHistoryDao = null;

    public RunningHistoryService(DatabaseHelper helper) {
        try {
            runningHistoryDao = helper.getRunningHistoryDao();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveRunListToDB(RunningHistory runningHistory) {
        try {
            if (runningHistory.getRunUuid() != null) {
                runningHistoryDao.createOrUpdate(runningHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void saveRunListToDB(List<RunningHistory> runningHistoryList) {
        try {
            for (RunningHistory runningHistory : runningHistoryList) {
                if (runningHistory.getRunUuid() != null) {
                    runningHistoryDao.createOrUpdate(runningHistory);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public List<RunningHistory> fetchRunHistoryByUserId(Integer userId) {
        List<RunningHistory> runningHistoryList = null;
        try {
            QueryBuilder<RunningHistory, String> queryBuilder = runningHistoryDao.queryBuilder();
            queryBuilder.where().eq("userId", userId);
            queryBuilder.orderBy("missionEndTime", false);
            runningHistoryList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return runningHistoryList;
    }

    public List<RunningHistory> fetchUnsyncedRunHistory(Integer userId) {
        List<RunningHistory> runningHistoryList = null;
        try {
            QueryBuilder<RunningHistory, String> queryBuilder = runningHistoryDao.queryBuilder();
            Where where = queryBuilder.where();
            where.eq("userId", userId).and().isNull("commitTime");
            runningHistoryList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return runningHistoryList;
    }

    public void updateUnsyncedRunHistories(Integer userId) {
        List<RunningHistory> runningHistoryList = fetchUnsyncedRunHistory(userId);
        if (runningHistoryList == null || runningHistoryList.size() == 0) {
            return;
        }
        try {
            for (RunningHistory runningHistory : runningHistoryList) {
                runningHistory.setCommitTime(UserUtil.getSystemTime());
                runningHistoryDao.update(runningHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public RunningHistory fetchRunHistoryByRunId(String runId) {
        RunningHistory runningHistory = null;
        try {
            runningHistory = runningHistoryDao.queryForId(runId);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return runningHistory;
    }

    public RunningHistory fetchRunHistoryByMissionUuid(String missionUuid) {
        RunningHistory runningHistory = null;
        try {
            QueryBuilder<RunningHistory, String> queryBuilder = runningHistoryDao.queryBuilder();
            Where where = queryBuilder.where();
            where.eq("missionUuid", missionUuid).and().eq("valid", 1);
            runningHistory = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return runningHistory;
    }
}
