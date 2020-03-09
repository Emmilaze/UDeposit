package com.app.udeposits.core;

public class UtilPercents {
    public static String getSimplePercents(int money, float percent, int days){
        float result = (money * percent * days)/(365 * 100);
        return String.format("%.2f", result);
    }

    public static String getDifficultPercents(int money, float percent, int days){
        int numberOfOperation = days/30;
        int result = (int) (money * Math.pow((1 + (percent * days)/(100*365)), numberOfOperation));
        return String.format("%.2f", result);
    }
}
