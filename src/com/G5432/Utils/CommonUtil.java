package com.G5432.Utils;

import com.G5432.Entity.BMapInfo;
import com.baidu.mapapi.search.MKRoute;
import com.baidu.mapapi.utils.CoordinateConvert;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-15
 * Time: 下午3:11
 * To change this template use File | Settings | File Templates.
 */
public class CommonUtil {

    //是否可以掉落在地上
    public static String RULE_Drop_Down = "D";
    //掉落在的花盆上
    public static String RULE_Drop_Pot = "DP";
    //是否可以显示在脸上
    public static String RULE_On_Face = "OF";
    //是否需要改变脸的颜色
    public static String RULE_Face_Color = "FC";
    //标注肥肉的改变值
    public static String RULE_Fatness = "F";
    //标注肥肉的直接增加值
    public static String RULE_Fight_Add = "FA";
    //标注肥肉的百分比真价值
    public static String RULE_Fight_Percent = "FPE";
    //标注体力的临时上限增加
    public static String RULE_Physical_Power_Add = "PPA";
    //标注体力的临时上限的增加的百分比
    public static String RULE_Physical_Power_Percent = "PPP";
    //标注为道具。
    public static String RULE_Prop_Yes = "PY";
    //标注不是道具
    public static String RULE_Prop_No = "PN";
    //标注是否是钱
    public static String RULE_Money = "M";
    //标注触发是一个action事件
    public static String RULE_Type_Action = "TA";
    //标注触发是一个战斗事件
    public static String RULE_Type_Fight = "TF";
    //标注触发是一个好友战斗事件
    public static String RULE_Type_Fight_Friend = "TFF";
    //标注出发事件。
    public static String RULE_Type_Start = "TS";

    public static Integer COIN_ACTION_ID = 0;

    public static String SENTENCE_START_WALKING_ALONE = "独自一人从村里出发了|从村里出发了|毫无悬念的一个人从村里出发了";
    public static String SENTENCE_START_WALKING_WITH = "与小伙伴{0}一起从村里出发了";
    public static String SENTENCE_FRIEND_FIGHT_WIN = "遇到{0}并主动与之切磋武艺，几招过后轻松取胜，被围观群众投来崇拜的目光。|巧遇{0}与之切磋，大战250回合后终于艰难取胜。";
    public static String SENTENCE_FRIEND_FIGHT_LOSE = "遇到{0}并主动与之切磋武艺，结果连对方三招都没接住。|巧遇{0}与之切磋，大战250回合后遗憾落败。";


    public static Date parseDate(String date) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date == null || date.isEmpty())
            return null;

        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public static String parseDateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    public static String parseDateToStringOnlyDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static String parseDateToStringOnlyMins(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(date);
    }

    public static String getUrl(String url, String... params) {
        String newUrl = MessageFormat.format(url, params);
        return newUrl.replace(" ", "%20");
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            smdate = sdf.parse(sdf.format(smdate));

            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return 0;
    }

    public static String getMD5(String source) {
        String s = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};// 用来将字节转换成16进制表示的字符
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(source.getBytes());
            byte tmp[] = md.digest();// MD5 的计算结果是一个 128 位的长整数，
            // 用字节表示就是 16 个字节
            char str[] = new char[16 * 2];// 每个字节用 16 进制表示的话，使用两个字符， 所以表示成 16
            // 进制需要 32 个字符
            int k = 0;// 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) {// 从第一个字节开始，对 MD5 的每一个字节// 转换成 16
                // 进制字符的转换
                byte byte0 = tmp[i];// 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 取字节中高 4 位的数字转换,// >>>
                // 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf];// 取字节中低 4 位的数字转换

            }
            s = new String(str);// 换后的结果转换为字符串

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static String transSecondToStandardFormat(Integer seconds) {
        int min = 0;
        int hour = 0;
        min = (int) (seconds / 60);
        int second = (int) (seconds % 60);
        hour = (int) (min / 60);
        min = (int) (min % 60);
        if (hour > 0) {
            return MessageFormat.format("{0}小时{1}分{2}秒", hour, min, second);
        } else {
            return MessageFormat.format("{0}分{1}秒", min, second);
        }
    }

    public static String transDistanceToStandardFormat(double distance) {
        if (distance < 1000) {
            return MessageFormat.format("{0} m", String.format("%.2f", distance));
        }
        return MessageFormat.format("{0} km", String.format("%.2f", distance / 1000));
    }

    public static String getCityCode(String cityName, String districtName) {
        CityCode cityCodeClass = new CityCode();
        Map<String, String> cityCodes = CityCode.cityCodes;
        for (String city : cityCodes.keySet()) {
            if (districtName.contains(city) || cityName.contains(city)) {
                return cityCodes.get(city);
            }
        }
        return null;
    }

    public static BMapInfo getRoutesFromString(String routeString) {
        BMapInfo bMapInfo = new BMapInfo();
        MKRoute route = new MKRoute();
        GeoPoint start = new GeoPoint(0, 0);
        GeoPoint end = new GeoPoint(0, 0);
        Integer maxLat = -90 * 1000000;
        Integer minLat = 90 * 1000000;
        Integer maxLon = -180 * 1000000;
        Integer minLon = 180 * 1000000;
        String[] routeArray = routeString.split("\\|");
        GeoPoint[][] routeList = new GeoPoint[routeArray.length][];
        for (int i = 0; i < routeArray.length; i++) {
            String[] points = routeArray[i].split(" ");
            GeoPoint[] pointRoute = new GeoPoint[points.length];
            for (int j = 0; j < points.length; j++) {
                String[] geoPoint = points[j].split(",");
                GeoPoint point = new GeoPoint((int) (Double.valueOf(geoPoint[0]).doubleValue() * 1E6), (int) (Double.valueOf(geoPoint[1]).doubleValue() * 1E6));
                point = CoordinateConvert.fromGcjToBaidu(point);
                maxLat = Math.max(maxLat, point.getLatitudeE6());
                maxLon = Math.max(maxLon, point.getLongitudeE6());
                minLat = Math.min(minLat, point.getLatitudeE6());
                minLon = Math.min(minLon, point.getLongitudeE6());
                pointRoute[j] = point;
                if (i == 0 && j == 0) {
                    start = point;
                }
                if (i == routeArray.length - 1 && j == points.length - 1) {
                    end = point;
                }
            }
            routeList[i] = pointRoute;
        }
        route.customizeRoute(start, end, routeList);
        bMapInfo.setRoute(route);
        GeoPoint centerPoint = new GeoPoint((maxLat + minLat) / 2, (maxLon + minLon) / 2);
        bMapInfo.setCenterPoint(centerPoint);
        bMapInfo.setSpanLat((int) (Math.abs(maxLat - minLat) * 1.2));
        bMapInfo.setSpanLon((int) (Math.abs(maxLon - minLon) * 1.2));
        return bMapInfo;
    }

    public static Map<Integer, Integer> explainActionRule(String actionRule) {
        Map<Integer, Integer> vProductIds = new HashMap<Integer, Integer>();
        //9,1,PY|6,1,PN
        if (actionRule != null) {
            String[] ruleArray = actionRule.split("\\|");
            for (int i = 0; i < ruleArray.length; i++) {
                String[] ruleDetails = ruleArray[i].split(",");
                if (ruleDetails != null && ruleDetails.length >= 3) {
                    Integer productId = Integer.parseInt(ruleDetails[0]);
                    Integer numb = Integer.parseInt(ruleDetails[1]);
                    String propFlag = ruleDetails[2];
                    if (propFlag.equalsIgnoreCase(RULE_Prop_Yes)) {
                        vProductIds.put(productId, numb);
                    }
                }
            }
        }
        return vProductIds;
    }

    public static Map<Integer, Integer> explainPropHaveRule(String propHaving) {
        Map<Integer, Integer> vProductIds = new HashMap<Integer, Integer>();
        //17,1|1,1|18,1|3,1|
        if (propHaving != null) {
            String[] ruleArray = propHaving.split("\\|");
            for (int i = 0; i < ruleArray.length; i++) {
                String[] ruleDetails = ruleArray[i].split(",");
                if (ruleDetails != null && ruleDetails.length >= 2) {
                    Integer productId = Integer.parseInt(ruleDetails[0]);
                    Integer numb = Integer.parseInt(ruleDetails[1]);
                    vProductIds.put(productId, numb);
                }
            }
        }
        return vProductIds;
    }

    public static Map<String, Integer> explainActionEffectiveRule(String effectiveRule) {
        Map<String, Integer> userStatusMap = new HashMap<String, Integer>();
        //B,1|H,-1
        if (effectiveRule != null) {
            String[] ruleArray = effectiveRule.split("\\|");
            for (int i = 0; i < ruleArray.length; i++) {
                String[] ruleDetails = ruleArray[i].split(",");
                if (ruleDetails != null && ruleDetails.length >= 2) {
                    String effectiveName = ruleDetails[0];
                    Integer numb = Integer.parseInt(ruleDetails[1]);
                    userStatusMap.put(effectiveName, numb);
                }
            }
        }
        return userStatusMap;
    }

}
