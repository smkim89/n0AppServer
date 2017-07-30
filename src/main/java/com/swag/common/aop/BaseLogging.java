package com.swag.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BaseLogging {
	private static final Logger logger = LoggerFactory.getLogger(BaseLogging.class);

	@Around("execution(* com.swag..*Controller.*(..))")
	public Object baseLogging(ProceedingJoinPoint joinPoint) throws Throwable {
		String target = joinPoint.getTarget().toString();
		String methodName = joinPoint.getSignature().toShortString();
		String classPath = target.substring(0, target.lastIndexOf("@"));
		Object ret = null;
		try {
			logger.info(classPath + "." + methodName + " called.");
			ret = joinPoint.proceed();
		} catch (Throwable ex) {
			logger.error(ex.getMessage(), ex);
			return "exception/500";
		}
		return ret;
	}

}
