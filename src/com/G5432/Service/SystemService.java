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

    private Dao<SystemMessage, Integer> systemMessageDao = null;

    private Dao<RecommendApp, Integer> recommendAppDao = null;

    private Dao<ActionDefinition,Integer> actionDefinitionDao =null;

    public SystemService(DatabaseHelper helper) {
        try {
            systemMessageDao = helper.getSystemMessageDao();
            recommendAppDao = helper.getRecommendAppDao();
            actionDefinitionDao = helper.getActionDefinitionDao();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveSystemMessageToDB(List<SystemMessage> systemMessageList) {
        try {
            for (SystemMessage systemMessage : systemMessageList) {
                systemMessageDao.createOrUpdate(systemMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public SystemMessage fetchSystemMessageInfo(Integer messageId) {
        SystemMessage systemMessage = null;
        try {
            systemMessage = systemMessageDao.queryForId(messageId);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return systemMessage;
    }

    public String getSystemMessage(Integer messageId) {
        return getSystemMessage(messageId, null);
    }

    public String getSystemMessage(Integer messageId, Double region) {
        String result = "";
        SystemMessage systemMessage = fetchSystemMessageInfo(messageId);
        if (systemMessage != null) {
            //todo:: add exception handler
        }

        if (systemMessage != null) {
            result = systemMessage.getMessage();
            if (region != null && !systemMessage.getRule().isEmpty()) {
                String[] messageArray = systemMessage.getMessage().split("\\|");
                String[] ruleArray = systemMessage.getRule().split("\\|");
                if (messageArray.length == ruleArray.length && ruleArray.length > 1) {
                    for (int i = 0; i < ruleArray.length; i++) {
                        String[] ruleDetails = ruleArray[i].split(",");
                        double left = Double.parseDouble(ruleDetails[0]);
                        double right = Double.parseDouble(ruleDetails[1]);
                        if (region <= right && region >= left) {
                            result = messageArray[i];
                        }
                    }
                }
            }
        }
        return result;
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
            queryBuilder.where().eq("actionType" ,actionType.ordinal());
            queryBuilder.orderBy("triggerProbability", true);
            actionDefinitionList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return actionDefinitionList;
    }
}
