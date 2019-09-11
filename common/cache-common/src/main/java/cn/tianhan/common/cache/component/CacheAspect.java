package cn.tianhan.common.cache.component;

import cn.tianhan.common.cache.annotation.CacheString;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @Author NieAt
 * @Description
 * @create 2019/7/13 0013 下午 20:51
 */
@Component
@Aspect
public class CacheAspect {
    @Resource
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(cn.tianhan.common.cache.annotation.CacheString)")
    public void cacheString() {
    }

    @Around("cacheString()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object result = null;
        CacheString cacheString = getAnnotation(joinPoint);
        String param = cacheString.param();
        String key = cacheString.key();
        int expires = cacheString.expires();
        try {
            if (!"".equals(param)) {
                String k = (String) joinPoint.getArgs()[Integer.valueOf(param)];
                String reS = "{#" + param + "}";
                if (key.contains(reS)) {
                    key = key.replace(reS, k);
                }
                BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(key);
                result = boundValueOperations.get();
                if (null == result) {
                    result = joinPoint.proceed();
                    boundValueOperations.set(result, cacheString.expires(), TimeUnit.HOURS);
                }
            } else {
                result = joinPoint.proceed();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }

    private CacheString getAnnotation(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        return targetMethod.getAnnotation(CacheString.class);
    }
}
