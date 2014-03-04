package com.G5432.StepCounting;

/**
 * Created with IntelliJ IDEA.
 * User: beyond
 * Date: 13-11-1
 * Time: 上午12:16
 * To change this template use File | Settings | File Templates.
 */
public class SCMatrix {
    public float []matrix = new float[9];
    private int demi = 3;

    public SCVector leftMultiplyVector(SCVector scVector){
        SCVector result = new SCVector();
        for (int i=0; i<demi; i++){
            result.v[i]=0;
            for (int j=0; j<demi;j++){
                result.v[i]+=scVector.v[j]*matrix[i*3+j];
            }
        }
        return result;
    }
}
