package com.G5432.StepCounting;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;


/**
 * Created with IntelliJ IDEA.
 * User: beyond
 * Date: 13-11-1
 * Time: 上午11:10
 * To change this template use File | Settings | File Templates.
 */
public class OrientationSensorService implements SensorEventListener {

    public OrientationSensorService(Activity activity){
        mySensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
    }

    private SensorManager mySensorManager;
    private Sensor mySensor;

    public SCVector getOriginGeomagnetic() {
        if(originGeomagnetic ==null){
            originGeomagnetic = new SCVector();
        }
        return originGeomagnetic;
    }

    private SCVector originGeomagnetic;

    public void start(){
        mySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mySensorManager.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void end(){
        mySensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
        getOriginGeomagnetic().initWithSensorEvent(sensorEvent);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}