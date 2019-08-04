package com.nieat.common.cache.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/13 0013 下午 20:52
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheString {
    String key() default "";

    String param() default "";

    int expires() default 24 * 3;

    TimeUnit timeUnit() default TimeUnit.HOURS;
}
