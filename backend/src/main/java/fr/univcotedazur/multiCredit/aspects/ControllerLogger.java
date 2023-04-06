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
        LOG.info("\033[1;34m" + PREFIX + joinPoint.getThis() + "\033[0m:Called \033[1;32m{}\033[0m \033[1;35m{}\033[0m"+"\n", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "allControllerMethods()", returning = "resultVal")
    public void logMethodReturningProperly(JoinPoint joinPoint, Object resultVal) {
        LOG.info("\033[1;34m" + PREFIX + joinPoint.getThis() + "\033[0m:Returned \033[1;32m{}\033[0m with value \033[1;32m{}\033[0m"+"\n", joinPoint.getSignature().getName(), resultVal);
    }

    @AfterThrowing(pointcut = "allControllerMethods()", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Exception exception) {
        LOG.warn("\033[1;31m" + PREFIX + joinPoint.getThis() + "\033[0m:Exception from \033[1;32m{}\033[0m with exception \033[1;31m{}\033[0m"+"\n", joinPoint.getSignature().getName(), exception);
    }

}
