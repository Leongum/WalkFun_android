package com.G5432.StepCounting;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: beyond
 * Date: 13-10-29
 * Time: 下午4:12
 * To change this template use File | Settings | File Templates.
 */
public class StepCounting {
    private static int SC_WINDOW_SIZE=20;
    private static double MAX_SLOWRUN_SPEED=16/3.6, MAX_WALK_SPEED=6/3.6;
    private double []gWindow = new double[SC_WINDOW_SIZE];
    private double []lWindow = new double[SC_WINDOW_SIZE];
    private int head;
    private int tail;
    private int lastGPeak;
//    private int lPeak;
    private int totalPoints;

    public int getCounter() {
        return counter;
    }

    private int counter;
    private double    rtStepFrequency;
    private ArrayList levelAccList, gAccList;

    public StepCounting(){
        counter = 0;

        totalPoints = 0;
        head = 0;
        tail = 0;
    }

    private void pushNewLAcc(double lAcc, double gAcc, double v){

        //push new point into window
        gWindow[tail] = gAcc;
        lWindow[tail] = lAcc;
        tail = pointerMoveRight(tail);
        if (head == tail)
            head = pointerMoveRight(tail);
        totalPoints++;
        //deque one step if exist
        if (totalPoints>4)
            checkStep(v);
    }

    private void checkStep(double v){
        double maxFrequency = SCCommon.MIN_STEP_TIME / SCCommon.delta_T;
        //double slowrunFrequency = 0.3 / delta_T;

//    if (v < 16/3.6){ // 16km/h, as slow running or walk
//        if (totalPoints - lastGPeak < slowrunFrequency)
//            return;
//    } else {
        if (totalPoints - lastGPeak < maxFrequency)
            return;
//    }

        updateGAccPeak(v);
    }

    private void oneStepFound(){
        counter++;
        lastGPeak = totalPoints -1;
    }

    private void updateGAccPeak(double v){
        double now = gWindow[pointerMoveLeft(tail,3)];
        double pre = gWindow[pointerMoveLeft(tail,4)];
        double before = gWindow[pointerMoveLeft(tail,5)];
        double next = gWindow[pointerMoveLeft(tail,2)];
        double far = gWindow[pointerMoveLeft(tail,1)];

        if (now<pre && now<before && now<next && now<far){
            if (pre<before || next<far){
                double stepTime = (totalPoints -1 - lastGPeak) * SCCommon.delta_T;
                rtStepFrequency = 60/stepTime;
                double minGAcc = SCCommon.THRESHOLD_GACC;
                if (v<=6/3.6 ){//|| rtStepFrequency <= 100){
                    minGAcc = SCCommon.THRESHOLD_GACC;
                }
                else if ((v>MAX_WALK_SPEED && v <= MAX_SLOWRUN_SPEED) ){// || (rtStepFrequency >100 && rtStepFrequency <= 200)){
                    minGAcc = -2;
                }
                else if (v>16/3.6){// || rtStepFrequency > 200){
                    minGAcc = -5;
                }

                if (now < minGAcc)
                    oneStepFound();
            }
        }
    }

    private int pointerMoveLeft(int pointer, int num){
        return (pointer - (num%SC_WINDOW_SIZE) + SC_WINDOW_SIZE) % SC_WINDOW_SIZE;
    }

    private int pointerMoveLeft(int pointer){
        return pointerMoveLeft(pointer,1);
    }

    private int pointerMoveRight(int pointer, int num){
        return (pointer + num) % SC_WINDOW_SIZE;
    }

    private int pointerMoveRight(int pointer){
        return pointerMoveRight(pointer,1);
    }
}
