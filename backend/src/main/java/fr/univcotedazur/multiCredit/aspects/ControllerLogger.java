package fr.univcotedazur.multiCredit.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLogger {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerLogger.class);
    private static final String PREFIX = "\033[1;36mTCFS\033[0m\033[1;31m:Rest-Controller\033[0m\033[1;33m:";

    @Pointcut("execution(public * fr.univcotedazur.multiCredit.controllers..*(..))")
    private void allControllerMethods() {
    } // This enables to attach the pointcut to a method name we can reuse below

    @Before("allControllerMethods()")
    public void logMethodNameAndParametersAtEntry(JoinPoint joinPoint) {
        LOG.info("\u001B[1;34m%s%s\u001B[0m:Called \u001B[1;32m{}\u001B[0m \u001B[1;35m{}\u001B[0m%n".formatted(PREFIX, joinPoint.getThis()),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "allControllerMethods()", returning = "resultVal")
    public void logMethodReturningProperly(JoinPoint joinPoint, Object resultVal) {
        LOG.info("\u001B[1;34m%s%s\u001B[0m:Returned \u001B[1;32m{}\u001B[0m with value \u001B[1;32m{}\u001B[0m%n".formatted(PREFIX, joinPoint.getThis()),
                joinPoint.getSignature().getName(),
                resultVal);
    }

    @AfterThrowing(pointcut = "allControllerMethods()", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Exception exception) {
        LOG.warn("\u001B[1;31m%s%s\u001B[0m:Exception from \u001B[1;32m{}\u001B[0m with exception \u001B[1;31m{}\u001B[0m%n".formatted(PREFIX, joinPoint.getThis()),
                joinPoint.getSignature().getName(),
                exception);
    }
}
