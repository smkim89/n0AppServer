/******************************************************************************
 * T-MONET
 * Copyright (c) 2008-2010 by BEAURIE, Inc.
 * All rights reserved.
 * Title : Email 발송을 위한 data model
 * Developer : 박상헌
 *  *******************************************************************
 * 작성일 : 2010/06/16
 * 개발자 : 문성학(hagi@ahope.co.kr)
 * 내용 : T-coin에 있는 Email 발송에 관한 것을 가져옴
 *******************************************************************/
package com.swag.common.util.mail.domain;

import java.util.List;

public class Email {
	// 수신자 주소
	private List<String> mailAddrList;
	// 수신자 명
	private List<String> nameList;
	// 메세지
	private String mailMessage;
	// 제목
	private String articleTitle;
	// 본문
	private String articleText;
	// 바코드
	private String barcode;
	// 배포 금액
	private String deplAmt;
	// 충전 수수료
	private String chargeFee;
	// 충전 금액
	private String chargeAmt;
	// 유효기간
	private String validTerm;
	// 발송 메일 건수
	private int numOfmail;

	// Setter

	public void setMailAddrList(List<String> mailAddrList) {
		this.mailAddrList = mailAddrList;
	}

	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}

	public void setMailMessage(String mailMessage) {
		this.mailMessage = mailMessage;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public void setArticleText(String articleText) {
		this.articleText = articleText;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public void setDeplAmt(String deplAmt) {
		this.deplAmt = deplAmt;
	}

	public void setChargeFee(String chargeFee) {
		this.chargeFee = chargeFee;
	}

	public void setChargeAmt(String chargeAmt) {
		this.chargeAmt = chargeAmt;
	}

	public void setValidTerm(String validTerm) {
		this.validTerm = validTerm;
	}
	public void setNumOfmail(int numOfmail) {
		this.numOfmail = numOfmail;
	}
	
	// Getter

	public List<String> getMailAddrList() {
		return mailAddrList;
	}
	
	public List<String> getNameList() {
		return nameList;
	}



	public String getMailMessage() {
		return this.mailMessage;
	}

	public String getArticleTitle() {
		return this.articleTitle;
	}

	public String getArticleText() {
		return this.articleText;
	}

	public String getBarcode() {
		return this.barcode;
	}

	public String getDeplAmt() {
		return this.deplAmt;
	}

	public String getChargeFee() {
		return this.chargeFee;
	}

	public String getChargeAmt() {
		return this.chargeAmt;
	}

	public String getValidTerm() {
		return this.validTerm;
	}
	public int getNumOfmail() {
		return numOfmail;
	}
}
