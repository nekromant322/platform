package com.override.aop;

import com.override.annotation.MaxExecutionTime;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Slf4j
@Component
public class MaxExecutionTimer {

    @Pointcut("@annotation(com.override.annotation.MaxExecutionTime)")
    public void annotatedMethod() {
    }

    @Around("execution(* *(..)) && (annotatedMethod())")
    public Object measure(ProceedingJoinPoint thisJoinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
        Method method = methodSignature.getMethod();

        Object proceed = null;

        if (method.isAnnotationPresent(Timed.class)) {

            long start = System.currentTimeMillis();

            proceed = thisJoinPoint.proceed();

            long executionTime = System.currentTimeMillis() - start;

            long declaredTime = method.getAnnotation(MaxExecutionTime.class).millis();

            if (executionTime > declaredTime) {
                throw new RuntimeException("Execution of " + thisJoinPoint.getSignature() + " takes too much time: " + executionTime + " millis");
            }
        } else {
            proceed = thisJoinPoint.proceed();
        }
        return proceed;
    }
}
