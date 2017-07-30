/**
 * 
 */

$.PostUrl = {
	
		postUrl : function (url) {
			$('#postForm').attr('action', url);
			$('#postForm').submit();
		}
}