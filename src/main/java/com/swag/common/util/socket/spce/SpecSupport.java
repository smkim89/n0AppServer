package com.swag.common.util.socket.spce;
/******************************************************************************
 * T-monet Online Service Architecture
 *
 *	SpecSupport 
 *
 *		KIM Hyuntak (kimhyuntak@gmail.com)
 *
 *****************************************************************************/

import java.util.HashMap;

/**
 * 공통 SPEC
 *
 * 	@author 김현탁
 *
 */
public class SpecSupport {

	public static final int TYPE_SIZE = 2;

	public static final int FCLASS_HEADER = 1;
	public static final int FCLASS_DATA = 0;

	public static final int ALIGN_LEFT = 1;
	public static final int ALIGN_RIGHT = 2;
	public static final char PADDING_SPACE = ' ';
	public static final char PADDING_ZERO = '0';

	public static final int FTYPE_AA = 0;		// 문자열				'ABC'
	public static final int FTYPE_AN = 1;		// 숫자+문자열			'ABC123'
	public static final int FTYPE_BI = 2;		// (Binary Data)		'...'
	public static final int FTYPE_BN = 3;		// Binary Number		'AB' ==> 0x4142 ==> ... (int)
	public static final int FTYPE_ND = 4;		// BCD Decimal Value	'0000123456' ==> 123456 (int)
	public static final int FTYPE_NH = 5;		// Hex value			'2710' ==> 0x2710 ==> 10000 (int)

	/**
	 * 전문 필드에 대한 스펙 정의와 값(value)을 저장한다.
	 * @author 김현탁 (kimhyuntak@gmail.com)
	 *
	 */
	public class Field
	{
		public String name;				// 항목이름
		public int cd;					// 항목종류 (header or data)
		public int type;				// 항목 타입 (FTYPE_XX)
		public int len;				    // 길이
		public boolean mandatory;		// 필수여부
		public int align;				// 정렬 (ALIGN_LEFT , ALIGN_RIGHT)
		public char padding;			// 패딩문자
		public Object value = null;
		public int offset;				// SPEC 의 rawdata 에서의 index


		public Field(boolean mandatory, int type, int len, String name, int cd) {
			setField(mandatory, type, len, name, cd,
					(type == FTYPE_ND || type == FTYPE_NH)?ALIGN_RIGHT:ALIGN_LEFT,
					(type == FTYPE_ND || type == FTYPE_NH)?PADDING_ZERO:PADDING_SPACE);
		}

		public Field(boolean mandatory, int type, int len, String name)
		{
			setField(mandatory, type, len, name, FCLASS_DATA,
					(type == FTYPE_ND || type == FTYPE_NH)?ALIGN_RIGHT:ALIGN_LEFT,
					(type == FTYPE_ND || type == FTYPE_NH)?PADDING_ZERO:PADDING_SPACE);
		}
		public Field(boolean mandatory, int type, int len, String name, int cd, int align, char padding) {
			setField(mandatory, type, len, name, cd, align, padding);
		}

		private void setField(boolean mandatory, int type, int len, String name, int cd, int align, char padding) {
			this.type = type;
			this.cd = cd;
			this.name = name.toUpperCase();
			this.len = len;
			this.align = align;
			this.padding = padding;
			this.mandatory = mandatory;

			if (type == FTYPE_AA || type == FTYPE_AN) {
				this.value = new String("");
			} else /* if (type == FTYPE_ND || type == FTYPE_BN || type == FTYPE_NH) */ {
				this.value = new Integer(0);
			}
		}
	} // class Field

	protected Field[] fields;						// 필드정의
	protected HashMap<String, Field> dataMap = new HashMap<String, Field>();		// 데이터 해시맵 <String, Field> 조합으로 저장한다.
	private String type = "  ";					// 전문타입
	// private String msgData;

	private byte[] rawdata = null;				// 전문 raw data
	private int hdrLen = 0;						// 헤더길이
	private int dataLen = 0;						// 헤더를 뺀 전체전문길이
	private int specLen = 0;						// 헤더를 포함한 전체전문길이 ( initialize 에서 초기화된다 )
	private int reqLen = 0;						// 필수전문길이	

	/**
	 * Constructors
	 */
	protected SpecSupport(String type, int length) { this.type = type; this.reqLen = length; }

	/**
	 * Getters/Setters
	 */
	public int getLength() { return reqLen; }
	public int getSpecLength() { return specLen; }


	/**
	 * Initialize Spec. Format ( Derived Class 는 초기화시 반드시 호출해야 함 )
	 * 		+ Field 값에 대해서 Type 과 Length 를 설정한다.
	 */
	public final void initialize() {
		dataMap.clear();
		int fLen = fields.length;	// Field 개수
		hdrLen = 0;
		dataLen = 0;

		for (int i=0; i<fLen; i++)	{
			specLen += fields[i].len;
			if (fields[i].cd == FCLASS_HEADER) {
				hdrLen += fields[i].len;
			} else {
				dataLen += fields[i].len;
			}
			dataMap.put(fields[i].name, fields[i]);
		}
		specLen = hdrLen + dataLen;

		try {
			setStr("TYPE", type);
			setInt("LENGTH", dataLen);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Parsing spec data stream (byte[])
	 *
	 * @param data
	 * @throws Exception
	 */
	public void fromStream(final byte[] data) throws Exception {
		if (specLen != reqLen) {
			throw new Exception("Invalid spec length (" + specLen + "!=" + reqLen + ")");
		}
		if (data == null) {
			throw new Exception("Input data is null");
		}
		if (data.length < this.specLen ) {
			throw new Exception("The length of the data is less than the required (" + data.length + "<" + reqLen + ")");
		}

		this.rawdata = new byte[data.length];
		System.arraycopy(data, 0, this.rawdata, 0, data.length);

		// 각 Field 에 대해 Parsing 을 수행한다.
		for (int i=0, offset=0; i<fields.length; offset += fields[i++].len)
		{
			Field f = this.fields[i];
			f.offset = offset;
			byte[] fdata = new byte[fields[i].len];
			System.arraycopy(this.rawdata, offset, fdata, 0, fields[i].len);

			parseFrom(fields[i], fdata);
		}
	}

	public void fromStrStream(String str) throws Exception {
		fromStream(str.getBytes());
	}

	/**
	 * Make bytes stream data from spec data 
	 *
	 * @return
	 * @throws Exception
	 */
	public byte[] toStream() throws Exception {
		if (specLen != reqLen) {
			throw new Exception("Invalid spec length (" + specLen + "!=" + reqLen + ")");
		}

		this.rawdata = new byte[this.specLen];
		int fLen = fields.length;
		for (int i=0, offset=0; i<fLen; offset += fields[i++].len)
		{
			fields[i].offset = offset;	// modified 2009-03-24 offset 을 설정해준다

			byte[] bytes = parseTo(fields[i]);
			System.arraycopy(bytes, 0, this.rawdata, offset, fields[i].len);
		}
		return this.rawdata;
	}

	public String toStrStream() throws Exception {
		return new String(toStream());
	}

	/**
	 * 한 필드항목에 대해 해당하는 값의 byte array 값을 얻는다.
	 *
	 * @param field
	 * @return
	 * @throws Exception
	 */
	private static byte[] parseTo(Field field)  throws Exception {
		if (field == null || field.value == null) {
			throw new Exception("Field or value is null");
		}
		byte[] bytes = null;
		switch (field.type) {
			case FTYPE_AA:
			case FTYPE_AN:
				bytes = formatStr((String)field.value, field.len, PADDING_SPACE, field.align).getBytes();
				break;
			case FTYPE_BI:
				bytes = new byte[field.len];
				byte[] b = (byte[])field.value;
				if (bytes.length <= b.length) {
					System.arraycopy(b, 0, bytes, 0, field.len);
				} else {
					System.arraycopy(b, 0, bytes, 0, b.length);
					for (int i=b.length; i < field.len; ++i) {
						bytes[i] = 0;
					}
				}
				break;
			case FTYPE_BN:
				bytes = SpecSupport.shortToBytes(((Integer) field.value).shortValue());
				break;
			case FTYPE_ND:
				bytes = formatStr(((Integer)field.value).toString(),
						field.len, PADDING_ZERO, ALIGN_RIGHT).getBytes();
				break;
			case FTYPE_NH:
				bytes = formatStr(Integer.toHexString(((Integer)field.value).intValue()),
						field.len, PADDING_ZERO, ALIGN_RIGHT).getBytes();
				break;
			default:
				throw new Exception("Unknown type value(" + field.type + ")");
		}
		return bytes;
	}

	/**
	 * 한 필드항목에 대해 bytes array 로 부터 적당한 타입의 값을 설정한다.
	 *
	 * 	2009-03-27 modified 숫자 처리시 NumberFormatException 예외에 대해서는 0 으로 처리한다.
	 *
	 * @param field		Field 객체
	 * @param bytes		Data bytes stream
	 * @throws Exception
	 */
	private static void parseFrom(Field field, byte[] bytes) throws Exception {
		if (field == null) {
			throw new Exception("field is null");
		}
		if (bytes == null) {
			throw new Exception("data is null");
		}
		switch (field.type) {
			case FTYPE_AA:	// 문자열
			case FTYPE_AN:	// 숫자문자열
				field.value = new String(bytes).trim();
				break;
			case FTYPE_BI:	// 바이너리 데이터
				byte[] b = new byte[bytes.length];
				System.arraycopy(bytes, 0, b, 0, bytes.length);
				field.value = b;
				break;
			case FTYPE_BN:	// 바이너리 숫자
				field.value = new Integer(SpecSupport.bytesToShort(bytes));
				break;
			case FTYPE_ND:	// 10진수
				try {
					field.value = new Integer(Integer.parseInt(new String(bytes).trim()));
				} catch (NumberFormatException e) {
					field.value = 0;
				}
				break;
			case FTYPE_NH:	// 16진수
				try {
					field.value = new Integer(Integer.parseInt(new String(bytes).trim(), 16));
				} catch (NumberFormatException e) {
					field.value = 0;
				}
				break;
			default:
				throw new Exception("Unknown type value(" + field.type + ")");
		}
	}

	/**
	 * 해당하는 이름의 Field 객체를 해시맵으로부터 얻는다
	 * @param name
	 * @return
	 * @throws Exception
	 */
	private Field getField(String name) throws Exception {
		Field f = (Field)dataMap.get(name.toUpperCase());
		if (f == null) {
			throw new Exception("There is no field '" + name + "' in this spec object.");
		}
		return f;
	}

	public boolean hasParam(String name) {
		return 	dataMap.containsKey(name);
	}

	/**
	 * 전문의 특정 파라미터들을 dst 에 설정한다. parameter 는 "이름,이름,..." 처럼 , 로 구분한다.
	 * @param src
	 * @param params 복사할 파라미터 이름(키) 
	 */
	public void copyParamsFrom(SpecSupport src, String params) {
		src.copyParamsTo(this, params);
	}

	public void copyParamsTo(SpecSupport dest, String params) {
		copyParamsTo(dest, params.split(","));
	}

	public void copyParamsTo(SpecSupport dest, String[] params) {
		for (String p : params) {
			try {
				Field f = getField(p);
				switch (f.type) {
					case FTYPE_AA:
					case FTYPE_AN:
						dest.setStr(f.name, (String)f.value);
						break;
					case FTYPE_BI:
						dest.setBytes(f.name, (byte[])f.value);
						break;
					default:
						dest.setInt(f.name, ((Integer)f.value).intValue());
						break;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 전문의 모든 파라미터들을 dst 에 설정한다.
	 * 	(dst 에 없는 parameter 는 무시함) 
	 * @param dst 복사할 파라미터 이름(키)
	 */
	public void copyToAll(SpecSupport dst) {
		for (int i=0; i<fields.length; i++) {
			if (dst.hasParam(fields[i].name) && fields[i].cd != FCLASS_HEADER) {
				try {
					Field f = fields[i];
					switch (f.type) {
						case FTYPE_AA:
						case FTYPE_AN:
							dst.setStr(f.name, (String)f.value);
							break;
						case FTYPE_BI:
							dst.setBytes(f.name, (byte[])f.value);
							break;
						default:
							dst.setInt(f.name, ((Integer)f.value).intValue());
							break;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public void copyFromAll(SpecSupport src) {
		src.copyToAll(this);
	}

	/**
	 * 필수항목 파라미터가 빈값이 없는지 체크한다.
	 * @throws Exception
	 */
	public void verifySpecFormat() throws Exception {
		for (int i=0; i<fields.length; i++) {
			if (fields[i].mandatory && (new String(rawdata, fields[i].offset, fields[i].len).trim().equals(""))) {
				throw new Exception("필수항목데이터검증에러1 \"" + fields[i].name + "\"");
			}
		}
	}

	/**
	 * <pre>
	 * 필수항 파라미터가 빈값이 없는지 체크한다.
	 * 에러 발생시 해당 필드명도 반환한다.
	 * </pre>
	 * @param fieldName 에러가 발생하는 파라미터명
	 * @throws Exception
	 * @author 박상헌
	 */
	public void verifySpecFormat(String fieldName) throws Exception {
		for (int i=0; i<fields.length; i++) {
			if (fields[i].mandatory && (new String(rawdata, fields[i].offset, fields[i].len).trim().equals(""))) {
				fieldName = fields[i].name;
				throw new Exception("필수항목데이터검증에러2 \"" + fields[i].name + "\"");
			}
		}
	} // veriftSpecFormat


	/**
	 * Exception 을 throw 하지 않는 safe getter
	 * @param name
	 * @return
	 */
	public String getStrSafe(String name) {
		try {
			return getStr(name);
		} catch (Exception ex) {
			return "";
		}
	}

	// getter/setter methods
	public byte[] getBytes(String name) throws Exception {
		return (byte[])getField(name).value;
	}

	public String getStr(String name) throws Exception {
		Field f = getField(name.trim());
		if (f.type == FTYPE_NH) {
			return Integer.toHexString(((Integer)f.value).intValue());
		}
		if (f.type == FTYPE_ND) {
			return ((Integer)getField(name).value).toString();
		}
		return (String)f.value;
	}

	public int getInt(String name) throws Exception {
		return ((Integer)getField(name).value).intValue();
	}

	public void setBytes(String name, byte[] bytes) throws Exception {
		Field f = getField(name);
		if (f.type != FTYPE_BI && f.type != FTYPE_BN) {
			throw new Exception("setBytes - '" + name + "' parameter is not byte array type.");
		}
		f.value = bytes;
	}
	public void setStr(String name, String value) throws Exception {
		Field f = getField(name);
		if (f.type != FTYPE_AA && f.type != FTYPE_AN) {
			throw new Exception("setBytes - '" + name + "' parameter is not string type.");
		}
		f.value = value;
	}

	public void setInt(String name, int value) throws Exception {
		Field f = getField(name);
		if (f.type != FTYPE_BN && f.type != FTYPE_ND && f.type != FTYPE_NH) {
			throw new Exception("setBytes - '" + name + "' parameter is not number type.");
		}
		f.value = new Integer(value);
	}

	public void setObj(String name, Object obj) throws Exception {
		Field f = getField(name);
		f.value = obj;
	}

	/**
	 * String 처리 유틸리티 함수
	 */
	public static String NVL(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}

	public static Object NVL(Object obj) {
		if (obj == null) {
			return new String("");
		}
		return obj;
	}

	public static byte[] NVL(byte[] b) {
		if (b == null) {
			return new byte[0];
		}
		return b;
	}

	public static String formatStr(String str, int length, char padding, int alignDir) {
		String strTemp = NVL(str);
		int diffLen = length - strTemp.getBytes().length; //strTemp.length();
		if (diffLen > 0) {
			StringBuffer sbPadding = new StringBuffer();
			sbPadding.append(padding);
			String fillRight = ((alignDir == ALIGN_LEFT)?sbPadding.toString():"");
			String fillLeft = ((alignDir == ALIGN_RIGHT)?sbPadding.toString():"");
			for (int i=0; i < diffLen; i++) {
				strTemp = fillLeft + strTemp + fillRight;
			}
		}
		return strTemp;
	}

	public static String formatStr(String str, int length) {
		return formatStr(str, length, PADDING_SPACE, ALIGN_LEFT);
	}

	/**
	 * 전문을 문자열로 반환한다.
	 */
	public String getMsgString() {
		return new String(this.rawdata);
	}

	/**
	 * Debugging 전문내용를 반환한다.
	 */
	@Override
	public String toString() {
		if (rawdata == null) {
			return "(null) - call toStream() first.";
		}

		String str = "";

		for (int i=0; i<fields.length; i++) 	{
			str += "FIELD[" + i + "][" +
					fields[i].name +"] = [" +
					new String(NVL(rawdata), fields[i].offset, fields[i].len) + "] = " +
					NVL(fields[i].value).toString() + "\n";
		}
		return str;
	}

	/**
	 * binary 데이터 (byte[]) 를  16 진 코드로 출력한다.
	 * @return
	 */
	public String toHexString() {
		if (rawdata == null) {
			return "(null) - call toStream() first.";
		}

		String str = "";
		for (int i=0; i<rawdata.length; ++i) {
			String s = Integer.toString(getUnsignedByte(rawdata[i]),16);
			if (s.length() == 1) {
				s = "0" + s;
			}
			str += s;
		}
		return str;
	}

	/**
	 * 전문데이터를 HexString 으로 변환한다.
	 * ( byte 를 문자열로 출력한다. 단 32 보다 작은 값들은 '.' 으로 표시함 )
	 *
	 * @return
	 */
	public String toSpecString() {
		// 전문 생성시에는 rawdata == null 이므로 toStream 을 호출해서 rawdata 를 생성해준다
		if (rawdata == null) {
			try {
				toStream();
			} catch (Exception e) {
				return "{null-error}";
			}
		}
		if (rawdata == null) {
			return "{null}";
		}
		StringBuffer sb = new StringBuffer();

		for (int i=0; i<rawdata.length; ++i) {
			if (rawdata[i] >= 32) {
				sb.append((char)rawdata[i]);
			} else {
				sb.append('.');
			}
		}
		return sb.toString();
	}

	public String toSpecString2(String delim) {
		// 전문 생성시에는 rawdata == null 이므로 toStream 을 호출해서 rawdata 를 생성해준다

		if (rawdata == null) {
			return "(null) - call toStream() first.";
		}

		StringBuffer sb = new StringBuffer();
		for (int i=0; i<fields.length; i++) {
			String temp = new String(NVL(rawdata), fields[i].offset, fields[i].len).replace(' ', '_');
			sb.append(temp);
			if (i!=fields.length-1) {
				sb.append(delim);
			}
		}
		return sb.toString();
	}

	/**
	 * 바이트를 0~255 의 integer 값으로 변경한다.
	 * @param b
	 * @return
	 */
	public static int getUnsignedByte(byte b) {
		int n = (int)b;
		if (n < 0) {
			n += 256;
		}
		return n;
	}

	/**
	 * 입력으로 들어오는 int값을 byte array로 변환한다.
	 */
	public static byte[] int2Bin(int value) {
		int SIZE = 4;
		byte[] b = new byte[SIZE];
		for (int i = 0; i < SIZE; i++) {
			b[i] = (byte) (value >>> (8 * (SIZE - 1 - i)));
		}
		return b;
	}

	/**
	 * byte array 데이터를 Hex String 형태로 변환한다.
	 */
	public static String Bin2Hex(byte[] value) {
		if (value == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < value.length; i++) {
			int j = (value[i] >> 4) & 0xf;
			if (j <= 9) {
				sb.append(j);
			} else {
				sb.append((char) (j + 'a' - 10));
			}
			j = value[i] & 0xf;
			if (j <= 9) {
				sb.append(j);
			} else {
				sb.append((char) (j + 'a' - 10));
			}
		}
		return sb.toString();
	}


	////////////////////////////////
	public static byte[] shortToBytes(short n) {
		byte[] bytes = new byte[2];
		for (int i=0; i<2; ++i) {
			bytes[1-i] = (byte) (n & 0xff);
			n >>= 8;
		}
		return bytes;
	}

	public static short bytesToShort(byte[] bytes) {
		if (bytes == null) {
			return 0;
		}
		short ret = 0;
		for (int i=0; i<bytes.length; ++i) {
			ret <<= 8;
			ret += bytes[i] & 0xFF;
		}
		return ret;
	}
}