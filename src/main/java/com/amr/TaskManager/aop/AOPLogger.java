package com.amr.TaskManager.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class AOPLogger {

    public static final Logger logger = Logger.getLogger(AOPLogger.class.getName());

    @Pointcut(value = "execution(* com.amr.TaskManager.*.*.*(..))")
    public void pointcut() {}

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        if(!joinPoint.getSignature().getDeclaringTypeName().contains("com.amr.TaskManager"))
            return;
        logger.info("Method "+joinPoint.getSignature().getName() + " class " + joinPoint.getSignature().getDeclaringTypeName()+" started");
    }

    @After("pointcut()")
    public void After(JoinPoint joinPoint) {
        if(!joinPoint.getSignature().getDeclaringTypeName().contains("com.amr.TaskManager"))
            return;
        logger.info("Method "+joinPoint.getSignature().getName() + " class " + joinPoint.getSignature().getDeclaringTypeName()+" finished");
    }
}
