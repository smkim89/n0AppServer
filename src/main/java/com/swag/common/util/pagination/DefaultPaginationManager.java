package com.swag.common.util.pagination;

import java.util.Map;


/**
 * @Class Name : DefaultPaginationManager.java
 * <pre> 인터페이스 PaginationManager 기본 구현 클래스.
 * PaginationRenderer의 구현체를 빈설정 파일을 참조하여 반환한다. 
 * </pre>
 * @author 
 * @since 2015.06.09
 * @version 1.0
 * @see
 *
 */

public class DefaultPaginationManager implements PaginationManager{
	
	private Map<String, PaginationRenderer> rendererType;
	
	/**
	 * Set PaginationRenderer 구현 클래스
	 * @param rendererType - PaginationRenderer 구현 클래스 집합 Map.
	 */
	public void setRendererType(Map<String, PaginationRenderer> rendererType) {
		this.rendererType = rendererType;
	}
	
	/**
	 * 
	 * @param type - tag의 파라미터 값.
	 */
	public PaginationRenderer getRendererType(String type) {
		
		return (rendererType!=null && rendererType.containsKey(type)) ? (PaginationRenderer) rendererType.get(type):new DefaultPaginationRenderer();		
	}	
	
}