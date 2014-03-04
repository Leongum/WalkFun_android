package com.G5432.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-3
 * Time: 下午1:51
 * To change this template use File | Settings | File Templates.
 */
public class WeatherDetailInfo {

    private String city;

    private Integer temp;

    private String windDirection;

    private String windSpeed;

    private String humidity;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
