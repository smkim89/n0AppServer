package com.swag.common.util.pagination;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PagenationConfig {
	
	@Bean
	public DefaultPaginationManager paginationManager() {
		DefaultPaginationManager defaultPaginationmmanager = new DefaultPaginationManager();
		Map<String, PaginationRenderer> rendererType = new  HashMap<String, PaginationRenderer>();
		rendererType.put("image", new ImagePaginationRenderer());
		rendererType.put("text", new DefaultPaginationRenderer());
		
		defaultPaginationmmanager.setRendererType(rendererType);
		return defaultPaginationmmanager;
	}
}
