package com.G5432.Service;

import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.Entity.Enum.MissionTypeEnum;
import com.G5432.Utils.UserUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-15
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public class MissionService {

    private Dao<Mission, Integer> missionDao = null;

    public MissionService(DatabaseHelper helper) {
        try {
            missionDao = helper.getMissionDao();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveMissionToDB(Mission mission) {
        try {
            missionDao.createOrUpdate(mission);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void saveMissionToDB(List<Mission> missionList) {
        try {
            for (Mission mission : missionList) {
                missionDao.createOrUpdate(mission);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public Mission fetchMissionByMissionId(Integer missionId) {
        Mission mission = null;
        try {
            mission = missionDao.queryForId(missionId);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return mission;
    }

    public List<Mission> fetchMissionListByType(MissionTypeEnum missionType) {
        List<Mission> missionsList = null;

        try {
            QueryBuilder<Mission, Integer> queryBuilder = missionDao.queryBuilder();
            queryBuilder.where().eq("missionTypeId", missionType.ordinal());
            queryBuilder.orderBy("missionId", true);
            missionsList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return missionsList;
    }
}
