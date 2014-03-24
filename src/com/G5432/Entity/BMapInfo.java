package com.G5432.Entity;

import com.baidu.mapapi.search.MKRoute;
import com.baidu.platform.comapi.basestruct.GeoPoint;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-24
 * Time: 下午3:00
 * To change this template use File | Settings | File Templates.
 */
public class BMapInfo {

    private MKRoute route;

    private int spanLat;

    private int spanLon;

    private GeoPoint centerPoint;

    public MKRoute getRoute() {
        return route;
    }

    public void setRoute(MKRoute route) {
        this.route = route;
    }


    public int getSpanLat() {
        return spanLat;
    }

    public void setSpanLat(int spanLat) {
        this.spanLat = spanLat;
    }

    public int getSpanLon() {
        return spanLon;
    }

    public void setSpanLon(int spanLon) {
        this.spanLon = spanLon;
    }

    public GeoPoint getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(GeoPoint centerPoint) {
        this.centerPoint = centerPoint;
    }

}
