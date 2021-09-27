package com.chenahua.distributedtransaction.aop;


import com.chenahua.distributedtransaction.annotation.DataSource;
import com.chenahua.distributedtransaction.conf.DataSourceEnum;
import com.chenahua.distributedtransaction.conf.DynamicRoutingDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DataSourceAspect implements Ordered {


    @Pointcut(value = "@annotation(com.chenahua.distributedtransaction.annotation.DataSource)")
    public void dataSourcePointCut() {
    }

    @Around(value = "dataSourcePointCut()")
    public void aspect(JoinPoint joinPoint)  {
        Object aThis = joinPoint.getThis();
        Object target = joinPoint.getTarget();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        DataSourceEnum datasource = signature.getMethod().getAnnotation(DataSource.class).datasource();
        DynamicRoutingDataSource.setDataSource(datasource);
        MethodInvocationProceedingJoinPoint methodInvocationProceedingJoinPoint = (MethodInvocationProceedingJoinPoint) joinPoint;
        try {
            methodInvocationProceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            DynamicRoutingDataSource.remove();
        }
//        throw new Exception();
    }

    @Override
    public int getOrder() {
        return 1;
    }


}
