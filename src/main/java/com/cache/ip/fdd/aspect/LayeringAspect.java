package com.cache.ip.fdd.aspect;

import com.cache.ip.fdd.annotation.Cacheable;
import com.cache.ip.fdd.cache.expression.CacheOperationExpressionEvaluator;
import com.cache.ip.fdd.cache.manager.CacheManager;
import com.cache.ip.fdd.cache.setting.LayeringCacheSetting;
import com.cache.ip.fdd.cache.support.Cache;
import com.cache.ip.fdd.cache.support.CacheOperationInvoker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 缓存拦截，用于注册方法信息
 */
@Aspect
public class LayeringAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String CACHE_KEY_ERROR_MESSAGE = "缓存Key %s 不能为NULL";
    private static final String CACHE_NAME_ERROR_MESSAGE = "缓存名称不能为NULL";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * SpEL表达式计算器
     */
    private final CacheOperationExpressionEvaluator evaluator = new CacheOperationExpressionEvaluator();

    @Pointcut("@annotation(com.cache.ip.fdd.annotation.Cacheable)")
    public void cacheablePointcut() {
    }


    @Around("cacheablePointcut()")
    public Object cacheablePointcut(ProceedingJoinPoint joinPoint) throws Throwable {
        CacheOperationInvoker aopAllianceInvoker = getCacheOperationInvoker(joinPoint);

        // 获取method
        Method method = this.getSpecificmethod(joinPoint);
        // 获取注解
        Cacheable cacheable = AnnotationUtils.findAnnotation(method, Cacheable.class);
        try {
            // 执行查询缓存方法
            return executeCacheable(aopAllianceInvoker, cacheable, method, joinPoint.getArgs(), joinPoint.getTarget());
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 执行Cacheable切面
     *
     * @param invoker   缓存注解的回调方法
     * @param cacheable {@link Cacheable}
     * @param method    {@link Method}
     * @param args      注解方法参数
     * @param target    target
     * @return {@link Object}
     */
    private Object executeCacheable(CacheOperationInvoker invoker, Cacheable cacheable,
                                    Method method, Object[] args, Object target) {
        String[] cacheNames = cacheable.cacheNames();
        Assert.notEmpty(cacheable.cacheNames(), CACHE_NAME_ERROR_MESSAGE);
        String cacheName = cacheNames[0];
        Object key = generateKey(cacheable.key(), method, args, target);
        Assert.notNull(key, String.format(CACHE_KEY_ERROR_MESSAGE, cacheable.key()));

        LayeringCacheSetting layeringCacheSetting = new LayeringCacheSetting(cacheable.expireTime(),cacheable.timeUnit(),cacheable.depict());

        //获取cache
        Cache cache = cacheManager.getCache(cacheName, layeringCacheSetting);

        return cache.get(key, () -> invoker.invoke());
    }


    private CacheOperationInvoker getCacheOperationInvoker(ProceedingJoinPoint joinPoint) {
        return () -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable ex) {
                throw new CacheOperationInvoker.ThrowableWrapperException(ex);
            }
        };
    }

    /**
     * 解析SpEL表达式，获取注解上的key属性值
     *
     * @return Object
     */
    private Object generateKey(String keySpEl, Method method, Object[] args, Object target) {

        // 获取注解上的key属性值
        Class<?> targetClass = getTargetClass(target);
        if (StringUtils.hasText(keySpEl)) {
            EvaluationContext evaluationContext = evaluator.createEvaluationContext(method, args, target,
                    targetClass, CacheOperationExpressionEvaluator.NO_RESULT);

            AnnotatedElementKey methodCacheKey = new AnnotatedElementKey(method, targetClass);
            // 兼容传null值得情况
            Object keyValue = evaluator.key(keySpEl, methodCacheKey, evaluationContext);
            return Objects.isNull(keyValue) ? "null" : keyValue;
        }
        return null;
    }


    /**
     * 获取类信息
     *
     * @param target Object
     * @return targetClass
     */
    private Class<?> getTargetClass(Object target) {
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(target);
        if (targetClass == null) {
            targetClass = target.getClass();
        }
        return targetClass;
    }


    /**
     * 获取Method
     *
     * @param pjp ProceedingJoinPoint
     * @return {@link Method}
     */
    private Method getSpecificmethod(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        // The method may be on an interface, but we need attributes from the
        // target class. If the target class is null, the method will be
        // unchanged.
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(pjp.getTarget());
        if (targetClass == null && pjp.getTarget() != null) {
            targetClass = pjp.getTarget().getClass();
        }
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        // If we are dealing with method with generic parameters, find the
        // original method.
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
        return specificMethod;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}