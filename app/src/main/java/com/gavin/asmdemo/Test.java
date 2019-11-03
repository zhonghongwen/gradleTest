package com.gavin.asmdemo;
/**
 * Time:2019/10/9
 * Author:zhw
 * Description:
 */
public class Test {
    private String getUseCaseCmd() {
        if(BBB.isTrue()) {
            return "Result.FAIL";
        } else {
            if(BBB.check()){//变为  if (BBB.check() && BBB.add())
                System.out.println("hahahahahh");
            }
            BBB.doSomething();
            return "Result.SUCCESS";
        }
    }
}
