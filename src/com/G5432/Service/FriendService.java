package com.G5432.Service;

import com.G5432.DBUtils.DatabaseHelper;
import com.G5432.Entity.*;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-4
 * Time: 下午1:50
 * To change this template use File | Settings | File Templates.
 */
public class FriendService {
    private Dao<UserFriend, String> userFriendDao = null;
    private Dao<Action, Void> actionDao = null;
    private Dao<FriendSort, Integer> friendSortDao = null;

    public FriendService(DatabaseHelper helper) {
        try {
            userFriendDao = helper.getUserFriendDao();
            actionDao = helper.getActionDao();
            friendSortDao = helper.getFriendSortDao();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveFriendsToDB(List<UserFriend> userFriendList) {
        try {
            for (UserFriend userFriend : userFriendList) {
                userFriendDao.createOrUpdate(userFriend);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void saveFriendSortsToDB(List<FriendSort> friendSortList) {
        try {
            for (FriendSort friendSort : friendSortList) {
                friendSortDao.createOrUpdate(friendSort);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public UserFriend fetchFriendByIds(Integer userId, Integer friendId) {
        UserFriend userFriend = null;
        try {
            QueryBuilder<UserFriend, String> queryBuilder = userFriendDao.queryBuilder();
            queryBuilder.where().eq("userId", userId).and().eq("friendId", friendId);
            userFriend = queryBuilder.queryForFirst();
            if (userFriend != null) {
                FriendSort friendSort = friendSortDao.queryForId(userFriend.getUserId());
                if (friendSort != null) {
                    userFriend.setSex(friendSort.getSex());
                    userFriend.setLevel(friendSort.getLevel());
                    userFriend.setUserName(friendSort.getFriendName());
                    userFriend.setUserTitle(friendSort.getUserTitle());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return userFriend;
    }

    public List<UserFriend> fetchFriendFansList(Integer userId) {
        List<UserFriend> friendList = null;
        try {
            QueryBuilder<UserFriend, String> queryBuilder = userFriendDao.queryBuilder();
            queryBuilder.where().eq("friendId", userId).and().isNotNull("friendEach");
            queryBuilder.orderBy("addTime", false);
            friendList = queryBuilder.query();
            for (UserFriend userFriend : friendList) {
                FriendSort friendSort = friendSortDao.queryForId(userFriend.getUserId());
                userFriend.setSex(friendSort.getSex());
                userFriend.setLevel(friendSort.getLevel());
                userFriend.setUserName(friendSort.getFriendName());
                userFriend.setUserTitle(friendSort.getUserTitle());
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return friendList;
    }

    public List<UserFriend> fetchFriendFollowsList(Integer userId) {
        List<UserFriend> friendList = null;
        try {
            QueryBuilder<UserFriend, String> queryBuilder = userFriendDao.queryBuilder();
            queryBuilder.where().eq("userId", userId).and().eq("friendStatus", 0);
            queryBuilder.orderBy("addTime", false);
            friendList = queryBuilder.query();
            for (UserFriend userFriend : friendList) {
                FriendSort friendSort = friendSortDao.queryForId(userFriend.getFriendId());
                userFriend.setSex(friendSort.getSex());
                userFriend.setLevel(friendSort.getLevel());
                userFriend.setUserName(friendSort.getFriendName());
                userFriend.setUserTitle(friendSort.getUserTitle());
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return friendList;
    }

    public List<UserFriend> fetchFriendEachFansList(Integer userId) {
        List<UserFriend> friendList = null;
        try {
            QueryBuilder<UserFriend, String> queryBuilder = userFriendDao.queryBuilder();
            queryBuilder.where().eq("userId", userId).and().eq("friendEach", 1);
            queryBuilder.orderBy("addTime", false);
            friendList = queryBuilder.query();
            for (UserFriend userFriend : friendList) {
                FriendSort friendSort = friendSortDao.queryForId(userFriend.getFriendId());
                userFriend.setSex(friendSort.getSex());
                userFriend.setLevel(friendSort.getLevel());
                userFriend.setUserName(friendSort.getFriendName());
                userFriend.setUserTitle(friendSort.getUserTitle());
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return friendList;
    }

    public void saveActionToDB(Action action) {
        try {
            actionDao.create(action);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void saveActionsToDB(List<Action> actionList) {
        try {
            for (Action action : actionList) {
                actionDao.create(action);
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public List<Action> fetchUserAction(Integer userId) {
        List<Action> actionList = null;
        try {
            QueryBuilder<Action, Void> queryBuilder = actionDao.queryBuilder();
            queryBuilder.where().eq("actionFromId", userId).or().eq("actionToId", userId);
            queryBuilder.orderBy("updateTime", false);
            actionList = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return actionList;
    }
}
