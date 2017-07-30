package com.swag.common.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;


//프로퍼티스 불러오는 유틸
public class PropertiesUtil {
	static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	ConfigurableApplicationContext context=  new AnnotationConfigApplicationContext();
	ConfigurableEnvironment environment = context.getEnvironment();
	
	private static PropertiesUtil instance = new PropertiesUtil();
	public static PropertiesUtil getInstance(){
		return instance;
	}
	
	public ConfigurableEnvironment getConfProperties(){
		MutablePropertySources propertySources = environment.getPropertySources();
		try {
			propertySources.addLast(new ResourcePropertySource("classpath:/config/config.properties"));
		} catch (IOException e) {
			logger.error("config.properties 없음.");
		}
		
		return environment;
	}
}
