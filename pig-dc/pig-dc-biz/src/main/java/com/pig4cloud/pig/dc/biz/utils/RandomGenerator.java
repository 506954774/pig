package com.pig4cloud.pig.dc.biz.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * RandomGenerator
 * 责任人:  ChenLei
 * 修改人： ChenLei
 * 创建/修改时间: 2021/6/21 10:48
 * Copyright :  版权所有
 **/
public class RandomGenerator {
    public static final Random RANDOM=new Random();
    public static final SimpleDateFormat FORMAT=new SimpleDateFormat("yyyyMMdd");

    public static String generateRandomByDate(int random){
        if(random<=0){
            throw new IllegalArgumentException("illegal argument : random");
        }
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(FORMAT.format(new Date()));
        int size=String.valueOf(random).length();
        stringBuilder.append(  String.format("%0"+size+"d",RANDOM.nextInt(random)) );
        return stringBuilder.toString();
    }
}
