package com.G5432.StepCounting;

import android.hardware.SensorEvent;

/**
 * Created with IntelliJ IDEA.
 * User: beyond
 * Date: 13-10-30
 * Time: 上午11:24
 * To change this template use File | Settings | File Templates.
 */
public class DeviceStatus {
    public SCMatrix rotationMatrix;

    public SCVector getRealAcc() {
        if (realAcc==null){
            realAcc = new SCVector();
            doTransition();
        }
        return realAcc;
    }

    private SCVector realAcc=null;

    public SCVector getOriAcc() {
        if (oriAcc ==null){
            oriAcc = new SCVector();
        }
        return oriAcc;
    }

    public void setOriAcc(SCVector oriAcc) {
        this.oriAcc = oriAcc;
    }

    private SCVector oriAcc;

    public DeviceStatus(SCVector acc, float rm[]){
        rotationMatrix = new SCMatrix();
        setOriAcc(acc);
        for (int i=0; i<rm.length; i++) {
            rotationMatrix.matrix[i] = rm[i];
        }
    }

    private void doTransition(){
        realAcc = rotationMatrix.leftMultiplyVector(oriAcc);
    }
}
