package it.wct.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Pointcut("execution(* it.wct.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* it.wct.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* it.wct.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	
	
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		String method = joinPoint.getSignature().toShortString();
		logger.info("========> in @Before: calling method: " + method);
		
		Object[] args = joinPoint.getArgs();
		
		for (Object arg : args) {
			logger.info("=====> argument: " + arg);
		}
	}
	
	
	@AfterReturning(pointcut="forAppFlow()", returning="result")
	public Object afterReturning(JoinPoint joinPoint, Object result) {
		String method = joinPoint.getSignature().toShortString();
		logger.info("========> in @AfterReturning: from method: " + method);
		
		logger.info("=======> result: " + result);
		
		return result;
	}
	
}
