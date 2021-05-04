package com.revature.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.revature.excpetion.ERSException;

@Aspect
@Component
public class LoggingAspect {
	
	@AfterThrowing(pointcut = "execution(* com.revature.service.*.*(..))", throwing = "e")
	public void logServiceException(ERSException e) throws ERSException{
		if(e.getMessage().contains("Service")) {
			log(e);			
		}
	}
	
	@AfterThrowing(pointcut = "execution(* com.revature.repository.*.*(..))", throwing = "e")
	public void logRepositoryException(ERSException e) throws ERSException{
		log(e);
	}
	
	private void log(Exception e) {
		Logger log = LoggerFactory.getLogger(this.getClass());
		log.error(e.getMessage(), e);
	}

}
