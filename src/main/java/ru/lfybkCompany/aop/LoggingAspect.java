package ru.lfybkCompany.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
@Slf4j
public class LoggingAspect {

    @Around("ru.lfybkCompany.aop.PointcutAspect.isServiceLayer() ||" +
            "ru.lfybkCompany.aop.PointcutAspect.isUploadFileServiceLayer() ||" +
            "ru.lfybkCompany.aop.PointcutAspect.isUploadExpensesServiceLayer() ||" +
//            "ru.lfybkCompany.aop.PointcutAspect.isMapperLayer() ||" +
            "ru.lfybkCompany.aop.PointcutAspect.isFileMapperLayer() ||" +
            "ru.lfybkCompany.aop.PointcutAspect.isControllerLayer()")
    public Object addLogging(ProceedingJoinPoint joinPoint) throws Throwable {
//        Object id = Arrays.toString(joinPoint.getArgs());
        Object id = joinPoint.getArgs().length;
        Object method = joinPoint.getSignature().getName();
        Object service = joinPoint.getTarget();

        log.info("Before invoke method: {} in class {}, with args {}", method, service, id);
        long startTime = System.currentTimeMillis();

        try {

            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis()-startTime;
            log.info("AfterReturning invoked method: {} in class: {}, with result: {} [execution time: {}ms]",
                    method, service, "Not given", executionTime);
            return result;

        } catch (Exception ex) {

            long executionTime = System.currentTimeMillis()-startTime;
            log.error("Exception: {}, invoked method: {} in class: {} [execution time: {}ms] - {}",
                    ex, method, service, executionTime, ex.getMessage(), ex);
            throw ex;

        } finally {

            long executionTime = System.currentTimeMillis()-startTime;
            log.info("After invoked method: {} in class: {} [execution time: {}ms]", method, service, executionTime);

        }

    }

}
