package com.G5432.StepCounting;

/**
 * Created with IntelliJ IDEA.
 * User: beyond
 * Date: 13-10-29
 * Time: 下午4:13
 * To change this template use File | Settings | File Templates.
 */
public class SCCommon {
    public static double M_E=2.71828182845904523536028747135266250;

    public static double delta_T=0.05;

    public static double MIN_STEP_TIME=0.2;

    public static double THRESHOLD_GACC=-1;

    public static double earth_R=6378137.0;

    public static double earth_Rn(double L){
        return earth_R*(1 - 2*M_E + 3*M_E*Math.sin(L));
    }

    public static double earth_Re(double L){
        return earth_R*(1+M_E*Math.sin(L)*Math.sin(L));
    }

    public static double earth_G=9.81;

    public static double earth_RAV=7.292115E-5;

    public static double MIN_An=1;

    public static double MIN_Rn=35;

    public static double MIN_AnVar=0.1;

    public static double calculateVectorMod(float value[]){
        double r = 0;
        for (int i=0; i<value.length;i++)
            r+= value[i]*value[i];
        return Math.sqrt(r);
    }
}
