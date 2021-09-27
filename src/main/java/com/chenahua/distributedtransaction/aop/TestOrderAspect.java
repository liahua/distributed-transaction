package com.chenahua.distributedtransaction.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@Aspect
public class TestOrderAspect implements Ordered {
    @Pointcut(value = "@annotation(com.chenahua.distributedtransaction.annotation.TestOrder)")
    public void orderPointCut() {
    }
    @Around(value = "orderPointCut()")
    public void aspect(JoinPoint joinPoint) {
        System.out.println("TestOrderAspect before");
        MethodInvocationProceedingJoinPoint methodInvocationProceedingJoinPoint = (MethodInvocationProceedingJoinPoint) joinPoint;
        try {
            methodInvocationProceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("TestOrderAspect finally");
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
