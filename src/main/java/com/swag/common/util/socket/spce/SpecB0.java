/******************************************************************************
 * 
 * T-monet Online Service Project
 *
 *	SpecE1 - KT-Android ����� ������ ��ȸ ��û (E1)
 *
 *		Brandon (yckim@t-monet.com)
 *
 *****************************************************************************/

package com.swag.common.util.socket.spce;


public class SpecB0 extends SpecSupport {
	
	public static final int B0_LENGTH = 200;

	public SpecB0() { this("B0"); }
	
	public SpecB0(String type) {
		super(type, B0_LENGTH);
		
		fields = new Field[] {
				// HEADER
				new Field(true, FTYPE_AN, 	 2,  "TYPE", FCLASS_HEADER),	// 업무구분코드
				new Field(true, FTYPE_BN, 	 2,  "LENGTH", FCLASS_HEADER),	// 전문 길이(690)

				// DATA
				new Field(true, FTYPE_AA, 	  8,  "MTEL_CO"),	// 이통사
				new Field(true, FTYPE_AA, 	 11,  "MOBILE_NO"),	// 휴대폰번호
				new Field(true, FTYPE_AA, 	 16,  "CARD_ID"),	// 카드번호
				new Field(true, FTYPE_AA, 	  2,  "PLATFORM"),	// 플랫폼타입
				new Field(true, FTYPE_AA, 	  3,  "APP_VER"),	// APP 버전
				new Field(true, FTYPE_AA, 	 10,  "OS_VER"),	// OS Version
				new Field(true, FTYPE_AA, 	 30,  "MODEL_ID"),	// 단말기모델명
				new Field(true, FTYPE_AA,    40,  "UUID"),		// 단말기시리얼
				new Field(true, FTYPE_AA, 	 76,  "FILLER"),	// 채우기

		};		
		super.initialize();
	}

}
