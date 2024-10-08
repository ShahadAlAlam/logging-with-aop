package com.saa.loggingWithAOP.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import org.aspectj.lang.JoinPoint;

import java.time.LocalDateTime;

//@Slf4j /* commented it because we do not want to use SLf4j we want to use AOP with custom message*/
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
        logger.error("Exception thrown in method: {} with arguments: {} and exception message: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs(),
                exception.getMessage(),
                exception, exception.getCause().toString());
    }

    @Around("execution(* com.saa.loggingWithAOP..*(..))")
    public void logMethodAroundCall(ProceedingJoinPoint joinPoint) throws Throwable  {
        LocalDateTime localTime = LocalDateTime.now();
        String datetimeString = localTime.toString();
        System.out.println("Around before "+datetimeString);
        logger.info("Entering With Logger {} parameters {} entering time {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs(),
                datetimeString);
        Object result;
        try {
            result = joinPoint.proceed(); // Proceed with method execution
        } catch (Exception e) {
            // Log exception
            System.err.println("Exception in method: " + joinPoint.getSignature().toShortString() + " with message: " + e.getMessage());
            throw e;
        }
        datetimeString = localTime.toString();
        logger.info("Exiting With Logger {} parameters {} exiting time {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs(),
                datetimeString);
        System.out.println("Around after "+datetimeString);

    }
}