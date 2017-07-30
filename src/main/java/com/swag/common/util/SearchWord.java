 package com.swag.common.util;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 검색어를 받기위한 공통 도메인 클래스입니다.
 *
 * Created by user on 2015-07-13.
 */
public class SearchWord {
	private String searchWord;  // 검색어
	private String searchArea;  // 검색 종류

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getSearchArea() {
		return searchArea;
	}

	public void setSearchArea(String searchArea) {
		this.searchArea = searchArea;
	}
}
