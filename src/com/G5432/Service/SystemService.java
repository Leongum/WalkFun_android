package com.G5432.Service;

import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.G5432.Entity.Enum.*;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-15
 * Time: 上午11:40
 * To change this template use File | Settings | File Templates.
 */
public class SystemService {

    private Dao<FightDefinition, Integer> fightDefinitionDao = null;

    private Dao<RecommendApp, Integer> recommendAppDao = null;

    private Dao<ActionDefinition, Integer> actionDefinitionDao = null;

    public SystemService(DatabaseHelper helper) {
        try {
            fightDefinitionDao = helper.getFightDefinitionDao();
            recommendAppDao = helper.getRecommendAppDao();
            actionDefinitionDao = helper.getActionDefinitionDao();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveRecommendAppToDB(List<RecommendApp> recommendAppList) {
        try {
            for (RecommendApp recommendApp : recommendAppList) {
                recommendAppDao.createOrUpdate(recommendApp);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public List<RecommendApp> fetchRecommendList() {
        List<RecommendApp> recommendAppList = null;

        try {
            QueryBuilder<RecommendApp, Integer> queryBuilder = recommendAppDao.queryBuilder();
            queryBuilder.orderBy("sequence", true);
            recommendAppList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return recommendAppList;
    }

    public void saveActionDefineToDB(List<ActionDefinition> actionDefinitionList) {
        try {
            for (ActionDefinition actionDefinition : actionDefinitionList) {
                actionDefinitionDao.createOrUpdate(actionDefinition);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public List<ActionDefinition> fetchAllActionDefine(ActionDefineEnum actionType) {
        List<ActionDefinition> actionDefinitionList = null;

        try {
            QueryBuilder<ActionDefinition, Integer> queryBuilder = actionDefinitionDao.queryBuilder();
            queryBuilder.where().eq("actionType", actionType.ordinal());
            queryBuilder.orderBy("triggerProbability", true);
            actionDefinitionList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return actionDefinitionList;
    }

    public ActionDefinition fetchActionDefineById(Integer id) {
        ActionDefinition actionDefinition = null;
        try {
            actionDefinition = actionDefinitionDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return actionDefinition;
    }

    public void saveFightDefineToDB(List<FightDefinition> fightDefinitionList) {
        try {
            for (FightDefinition fightDefinition : fightDefinitionList) {
                fightDefinitionDao.createOrUpdate(fightDefinition);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public List<FightDefinition> fetchFightDefineByLevel(Integer level) {
        List<FightDefinition> fightDefinitionList = null;

        try {
            QueryBuilder<FightDefinition, Integer> queryBuilder = fightDefinitionDao.queryBuilder();
            queryBuilder.where().ge("minLevelLimit", level).and().le("maxLevelLimit", level).and().eq("inUsing", 0);
            queryBuilder.orderBy("monsterMinFight", true);
            fightDefinitionList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return fightDefinitionList;
    }

    public FightDefinition fetchFightDefineById(Integer id) {
        FightDefinition fightDefinition = null;
        try {
            fightDefinition = fightDefinitionDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return fightDefinition;
    }
}
