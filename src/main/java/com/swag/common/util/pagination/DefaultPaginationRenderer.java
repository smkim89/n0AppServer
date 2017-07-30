package com.swag.common.util.pagination;

/**
 * DefaultPaginationRenderer.java
 * <p>
 * <b>NOTE:</b>
 * 
 * <pre>
 * 페이징 리스트의 기본적인 포맷을 제공한다.  
 * 이를 위해 AbstractPaginationRenderer의 클래스 변수들의 값을 세팅한다.  
 * 화면에서 아래와 같이 display 된다.  
 * 
 * [처음][이전] 1 2 3 4 5 6 7 8 [다음][마지막]
 * 
 * 클래스 변수들이 각 element와 다음과 같이 매핑이 된다.
 * firstPageLabel = [처음]
 * previousPageLabel = [이전]
 * currentPageLabel = 현재 페이지 번호
 * otherPageLabel = 현재 페이지를 제외한 페이지 번호
 * nextPageLabel = [다음]
 * lastPageLabel = [마지막]
 * </pre>
 * 
 * @author
 * @since 2015.06.09
 * @version 1.0
 * @see
 *
 */
public class DefaultPaginationRenderer extends AbstractPaginationRenderer {

	public DefaultPaginationRenderer() {
		firstPageLabel = "<li><a href=\"#\" page=\"{1}\"><i class=\"fa fa-angle-double-left\"></i></a></li>";
		previousPageLabel = "<li><a href=\"#\" page=\"{1}\"><i class=\"fa fa-angle-left\"></i></a></li>";
		currentPageLabel = "<li class=\"active\"><a>{0}</a></li>";
		otherPageLabel = "<li><a href=\"#\" page=\"{1}\">{2}</a></li>";
		nextPageLabel = "<li><a href=\"#\" page=\"{1}\"><i class=\"fa fa-angle-right\"></i></a></a></li>";
		lastPageLabel = "<li><a href=\"#\" page=\"{1}\"><i class=\"fa fa-angle-double-right\"></i></a></li>";

	}

	@Override
	public String renderPagination(PaginationInfo paginationInfo,
			String jsFunction) {

		return super.renderPagination(paginationInfo, jsFunction);
	}

}