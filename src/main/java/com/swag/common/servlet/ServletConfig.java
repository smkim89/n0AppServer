package com.swag.common.servlet;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.swag.common.interceptor.UrlInterceptor;
import com.swag.common.interceptor.XssInterceptor;

@EnableAspectJAutoProxy
@EnableScheduling
@EnableWebMvc
@ComponentScan("com.swag")
@Configuration
public class ServletConfig extends WebMvcConfigurerAdapter {
	@Bean
	public PropertiesFactoryBean config(){
		PropertiesFactoryBean bean = new PropertiesFactoryBean();
		bean.setLocation(new ClassPathResource("/config/config.properties"));
		return bean;
	}

	@Bean
	public PropertiesFactoryBean jdbc(){
		PropertiesFactoryBean bean = new PropertiesFactoryBean();
		bean.setLocation(new ClassPathResource("/config/jdbc.properties"));
		return bean;
	}


	@Bean
	public UrlInterceptor urlInterceptor() {
		return new UrlInterceptor();
	}
	
	

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(urlInterceptor());
		registry.addInterceptor(new XssInterceptor());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(86400);
	}

	@Bean
	public BeanNameViewResolver getBeanNameViewResolver() {
		BeanNameViewResolver resolver = new BeanNameViewResolver();
		resolver.setOrder(0);
		return resolver;
	}

	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setOrder(1);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public CommonsMultipartResolver getMultipartResolver() {
		CommonsMultipartResolver resolve = new CommonsMultipartResolver();
		resolve.setMaxUploadSize(229305);
		resolve.setDefaultEncoding("utf-8");
		return resolve;
	}

	/**
	 * '@ResponseBody' 사용 시 한글이 깨지는 것을 잡기위해 추가한다.
	 */
	@Override
	public void configureMessageConverters(java.util.List<org.springframework.http.converter.HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", Charset.forName("UTF-8"))));
		converters.add(stringConverter);
	}

}