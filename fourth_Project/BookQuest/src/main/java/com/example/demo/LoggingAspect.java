package com.example.demo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	private Logger logger = Logger.getLogger(getClass().getName());

	@Pointcut("execution(* com.example.demo.controller.*.*(..))")
	private void forContrtrollerPackage() {
	}

	@Pointcut("execution(* com.example.demo.service.*.*(..))")
	private void forServicePackage() {
	}

	@Before("forContrtrollerPackage()")
	public void beforeContrtroller(JoinPoint theJoinPoint) {
		String theMethod = theJoinPoint.getSignature().toShortString();
		logger.info("==> " + theMethod);
        logger.trace("trace log={}"+ theMethod);
        logger.debug("debug log={}"+ theMethod);
	}
	
	@Before("forServicePackage()")
	public void beforeService(JoinPoint theJoinPoint) {
		String theMethod = theJoinPoint.getSignature().toShortString();
		logger.info("===========> " + theMethod);
	}

	@AfterReturning(pointcut = "forServicePackage()", returning = "theResult")
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
		String theMethod = theJoinPoint.getSignature().toShortString();
//		logger.info("=> " + theMethod + theResult);
//		logger.trace("trace log={}" + theMethod + theResult);
//		logger.debug("debug log={}"+ theMethod + theResult);
	}
}