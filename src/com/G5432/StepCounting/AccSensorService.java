package com.G5432.StepCounting;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.String.valueOf;

/**
 * Created with IntelliJ IDEA.
 * User: beyond
 * Date: 13-11-1
 * Time: 上午10:46
 * To change this template use File | Settings | File Templates.
 */
public class AccSensorService implements SensorEventListener {

    private SensorManager mySensorManager;
    private Sensor mySensor;

    public SCVector getOriginAcc() {
        if(originAcc==null){
            originAcc = new SCVector();
        }
        return originAcc;
    }

    private SCVector originAcc;

    public AccSensorService(Activity activity){
        mySensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
    }

    public void start(){
        mySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mySensorManager.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void end(){
        mySensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
        getOriginAcc().initWithSensorEvent(sensorEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
