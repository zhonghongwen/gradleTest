package com.gavin.asmdemo;

import com.gavin.asmdemo.anno.Cost;

/**
 * Time:2019/11/3
 * Author:zhw
 * Description:
 */
public class AnnonteTest {
    private int a;
public AnnonteTest(String a){}
    @Cost
    public void say12345(){

        System.out.println("hello");
    }

}
