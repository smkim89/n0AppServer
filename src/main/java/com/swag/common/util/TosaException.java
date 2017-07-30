/******************************************************************************
 * T-monet Online Service Architecture
 *
 *	TosaException
 *
 *		KIM Hyuntak (kimhyuntak@gmail.com)
 *
 *****************************************************************************/
package com.swag.common.util;

/**
 * TosaException
 * 
 * @author KIM Hyuntak
 */
public class TosaException extends Exception {

	private static final long serialVersionUID = 6744175454560290389L;

	private String errCode = "99";
	private String errMsg = "����";

	public String getErrCode() { return errCode; }
	public String getMsg()	{ return errMsg; }
	
	public TosaException(String errCode, String errMsg, String errDesc, Throwable t) { 
		super(errCode + ":" + errMsg + ":" + errDesc, t);
		this.errCode  = errCode;
		this.errMsg = errMsg; 
	}
	
	public TosaException(String errCode, String errMsg, Throwable t) {
		super(errCode + ":" + errMsg, t);
		this.errCode  = errCode;
		this.errMsg = errMsg; 
	}
	
	public TosaException(String errCode, String errMsg, String errDesc) { 
		super(errCode + ":" + errMsg + ":" + errDesc);
		this.errCode  = errCode;
		this.errMsg = errMsg; 
	}
	
	public TosaException(String errCode, String errMsg) {
		super(errCode + ":" + errMsg);
		this.errCode  = errCode;
		this.errMsg = errMsg; 
	}
	
	public TosaException() {
	}

    public TosaException(String string) {
        super(string);
    }

    public TosaException(String message, Throwable cause) {
        super(message, cause);
    }

    public TosaException(Throwable cause) {
        super(cause);
    }
}
