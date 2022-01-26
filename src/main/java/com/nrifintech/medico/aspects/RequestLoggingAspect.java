package com.nrifintech.medico.aspects;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Configuration
public class RequestLoggingAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(RequestLoggingAspect.class);
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postMethods() {}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public void getMethods() {}
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void requestMethods() {}

    @Around("postMethods() || getMethods() || requestMethods()")
    public Object processParams(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        
        String requestID = UUID.randomUUID().toString();
        MDC.put("RequestID", requestID);
        MDC.put("TransactionURL", request.getMethod() + " " + request.getRequestURI());

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
     
        if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Execution time of {}.{}() : {}ms", className, methodName, elapsedTime);
        }
        
        return result;
    }

}