package com.gavin.asmdemo;

import android.util.Log;
import android.view.View;

/**
 * Time:2019/10/14
 * Author:zhw
 * Description:
 */
abstract class  TestAAA extends BBB implements View.OnClickListener {

    @Override
    public void onClick(View view) {

    }
    public static void test1() {
        Log.e("$zhw======$","1111111111111111111111111111测试");
    }
    private  void test2() {
        Log.e("$zhw======$","1111111111111111111111111111测试");
    }
    private static  void test3() {
        Log.e("$zhw======$","1111111111111111111111111111测试");
    }

    public static  void test4() {
        Log.e("$zhw======$","1111111111111111111111111111测试");
    }
    public abstract void haha();
}
