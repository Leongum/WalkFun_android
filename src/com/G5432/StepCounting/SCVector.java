package com.G5432.StepCounting;

import android.hardware.SensorEvent;

/**
 * Created with IntelliJ IDEA.
 * User: beyond
 * Date: 13-10-31
 * Time: 下午4:57
 * To change this template use File | Settings | File Templates.
 */
public class SCVector {
    public float v[] = new float[3];

    public double realMod(){
        return Math.sqrt(v[0]*v[0] + v[1]*v[1] + v[2]*v[2]);
    }

    public double horizontalMod(){
        return Math.sqrt(v[0]*v[0] + v[1]*v[1]);
    }

    public void initWithSensorEvent(SensorEvent sensorEvent){
        for (int i=0; i<3; i++){
            v[i] = sensorEvent.values[i];
        }
    }
}
