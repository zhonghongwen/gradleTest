package com.gavin.asmdemo.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Time:2019/11/3
 * Author:zhw
 * Description: 自定义注解统计耗时
 */
@Target(ElementType.METHOD)
public @interface Cost {
}
