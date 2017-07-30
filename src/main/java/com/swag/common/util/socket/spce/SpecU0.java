package com.swag.common.util.socket.spce;

public class SpecU0 extends SpecSupport {
	
	public static final int U0_LENGTH = 200;

	public SpecU0() { this("U0"); }

	public SpecU0(String type) {
		super(type, U0_LENGTH);

		fields = new Field[] {
				// HEADER
				new Field(true, FTYPE_AN, 2,  "TYPE", FCLASS_HEADER),	// 업무구분코드
				new Field(true, FTYPE_BN, 2,  "LENGTH", FCLASS_HEADER),	// 전문 길이(500)

				// DATA
				new Field(false, FTYPE_AA,    8, "MTEL_CO"),       // [ 8] 이통사코드
				new Field(true , FTYPE_AN,  11, "MOBILE_NO"),	// [11] 고객 전화번호
				new Field(true, FTYPE_AN,   16, "CARD_ID"),		// [ 8] 카드번호
				new Field(true, FTYPE_AN,    2, "PLATFORM"),		// [ 8] 이동통신사
				new Field(true , FTYPE_AN,   3, "APP_VER"),		// [ 3] 어플 버젼
				new Field(true , FTYPE_AN,  10, "OS_VER"),			// [10] OS 버젼
				new Field(false , FTYPE_AN,  30, "MODEL_ID"),	// [30] 모델명
				new Field(false , FTYPE_AN,  40, "UUID"),			// [40] UICCID

				new Field(false , FTYPE_AN,   1, "EVENT_FLAG"),	// [1] EVENT_FLAG
				new Field(true , FTYPE_AN,    1, "EC_AGREE"),		//[1]전자거래 약관동의 여부

				new Field(true , FTYPE_AN,    6, "APP_BUIDL_DATE"),		//[6]앱 빌드일(YYMMDD)
				new Field(true , FTYPE_AN,    1, "APP_MARKET"),			//[1]앱 링크 마켓 정보

				new Field(false, FTYPE_AA, 67, "RESERVED")		// [75] padding						
		};
		super.initialize();
	}
}
