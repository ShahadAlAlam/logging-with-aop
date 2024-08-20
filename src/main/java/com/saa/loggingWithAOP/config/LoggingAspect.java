package com.saa.loggingWithAOP.config;

import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.time.LocalDateTime;

//@Slf4j
@Aspect
@Component
public class LoggingAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Before("execution(* com.saa.loggingWithAOP..*(..))") // Pointcut expression to match methods in your package
    public void logBefore(JoinPoint joinPoint) {
        LocalDateTime localTime = LocalDateTime.now();
        String datetimeString = localTime.toString();
        logger.info("Entering With Logger {} parameters {} entering time {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs(),
                datetimeString);
//        log.info("Entering method: {} with arguments: {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    @After("execution(* com.saa.loggingWithAOP..*(..))") // Pointcut expression to match methods in your package
    public void logAfter(JoinPoint joinPoint) {
        LocalDateTime localTime = LocalDateTime.now();
        String datetimeString = localTime.toString();
        logger.info("Exiting With Logger {} parameters {} exiting time {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs(),
                datetimeString);
//        log.info("Exiting method: {}", joinPoint.getSignature().toShortString());
    }

    @AfterThrowing(pointcut = "execution(* com.saa.loggingWithAOP..*(..))",throwing = "exception")
    public void logMethodCallAfterException(JoinPoint joinPoint, Exception exception) {
        logger.info("AfterThrowing Aspect - {} has thrown an exception {}"
                ,  joinPoint, exception.getCause().toString());
    }
}