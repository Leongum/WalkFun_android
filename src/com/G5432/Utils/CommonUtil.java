package com.G5432.Utils;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 13-10-15
 * Time: 下午3:11
 * To change this template use File | Settings | File Templates.
 */
public class CommonUtil {

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


    public static String getUrl(String url, String... params) {
        String newUrl = MessageFormat.format(url, params);
        return newUrl.replace(" ", "%20");
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
            return MessageFormat.format("{0}:{1}''{2}\"", hour, min, second);
        } else {
            return MessageFormat.format("{0}''{1}\"", min, second);
        }
    }

    public static String transDistanceToStandardFormat(double distance) {
        if (distance < 1000) {
            return MessageFormat.format("{0} m", String.format("%.2f", distance));
        }
        return MessageFormat.format("{0} km", String.format("%.2f", distance / 1000));
    }

    public static String transSpeedToStandardFormat(double kmPerHour) {
        double metersPerSec = kmPerHour / 3.6;
        String speedType = UserUtil.getSpeedFormat();
        if (speedType.equalsIgnoreCase(Constant.SPEED_FORMAT_MIN_PER_KM)) {
            double orginSpeed = metersPerSec;
            if (orginSpeed == 0)
                return "0''0\" /km";
            int minutes = (int) (1000 / (orginSpeed * 60));
            int seconds = ((int) (1000 / orginSpeed)) % 60;
            return MessageFormat.format("{0}''{1}\" km", minutes, seconds);
        }
        return MessageFormat.format("{0} km/h", String.format("%.2f", kmPerHour));
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
}
