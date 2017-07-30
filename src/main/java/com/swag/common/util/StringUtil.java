package com.swag.common.util;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StringUtil {
	
	static Logger logger = LoggerFactory.getLogger(StringUtil.class);
	/**
	 * 
	 * @param src
	 * @param width
	 * @return
	 */
	public static String format(short src, int width)
	{
		return format(src, width, '0', false);
	}
	
	/**
	 * 
	 * @param src
	 * @param width
	 * @param fillChar
	 * @param leftJustify
	 * @return
	 */
	public static String format(short src, int width, char fillChar, boolean leftJustify)
	{
		byte[] b = Short.toString(src).getBytes();
		return format(b, width, fillChar, leftJustify);
	}
	
	/**
	 * 
	 * @param src
	 * @param width
	 * @return
	 */
	public static String format(int src, int width)
	{
		return format(src, width, '0', false);
	}
	
	/**
	 * 
	 * @param src
	 * @param width
	 * @param fillChar
	 * @param leftJustify
	 * @return
	 */
	public static String format(int src, int width, char fillChar, boolean leftJustify)
	{
		byte[] b = Integer.toString(src).getBytes();
		return format(b, width, fillChar, leftJustify);
	}
	
	/**
	 * 
	 * @param src
	 * @param width
	 * @return
	 */
	public static String format(long src, int width)
	{
		return format(src, width, '0', false);
	}
	
	/**
	 * 
	 * @param src
	 * @param width
	 * @param fillChar
	 * @param leftJustify
	 * @return
	 */
	public static String format(long src, int width, char fillChar, boolean leftJustify)
	{
		byte[] b = Long.toString(src).getBytes();
		return format(b, width, fillChar, leftJustify);
	}
	
	/**
	 * 
	 * @param src
	 * @param width
	 * @return
	 */
	public static String format(float src, int width)
	{
		return format(src, width, '0', false);
	}
	
	/**
	 * 
	 * @param src
	 * @param width
	 * @param fillChar
	 * @param leftJustify
	 * @return
	 */
	public static String format(float src, int width, char fillChar, boolean leftJustify)
	{
		byte[] b = Float.toString(src).getBytes();
		return format(b, width, fillChar, leftJustify);
	}
	
	/**
	 * 
	 * @param src
	 * @param width
	 * @return
	 */
	public static String format(double src, int width)
	{
		return format(src, width, '0', false);
	}
	
	/**
	 * 
	 * @param src
	 * @param width
	 * @param fillChar
	 * @param leftJustify
	 * @return
	 */
	public static String format(double src, int width, char fillChar, boolean leftJustify)
	{
		byte[] b = Double.toString(src).getBytes();
		return format(b, width, fillChar, leftJustify);
	}
	
	/**
	 * 
	 * @param src
	 * @param width
	 * @return
	 */
	public static String format(String src, int width)
	{
		return format(src, width, ' ', true);
	}
	
	/**
	 * 
	 * @param src
	 * @param width
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String format(String src, int width, String charset) throws UnsupportedEncodingException
	{
		return format(src, width, ' ', true, charset);
	}
	
	/**
	 * 
	 * @param src
	 * @param width
	 * @param fillChar
	 * @param leftJustify
	 * @return
	 */
	public static String format(String src, int width, char fillChar, boolean leftJustify)
	{
		byte[] b = null;
		if (src!=null)
			b = src.getBytes();
		return format(b, width, fillChar, leftJustify);
	}
	
	/**
	 * 
	 * @param src
	 * @param width
	 * @param fillChar
	 * @param leftJustify
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String format(String src, int width, char fillChar, boolean leftJustify, String charset) throws UnsupportedEncodingException
	{
		byte[] b = null;
		if (src!=null)
			b = src.getBytes(charset);
		return format(b, width, fillChar, leftJustify, charset);
	}
		
	/**
	 * 주어진 문자열을 포맷팅하여 반환합니다.
	 * 
	 * @param src	원본 문자열의 바이트 배열.
	 * @param width	포맷팅하고자 하는 결과 문자열의 길이.
	 * @param fillChar	공백을 채울 문자.
	 * @param leftJustify	왼쪽으로 맞출 것인지.
	 * @return	결과 문자열.
	 */
	public static String format(byte[] src, int width, char fillChar, boolean leftJustify)
	{
		int i, j;
		byte[] b = new byte[width];
		if (src == null)
		{
			for (i = 0; i < width; i++)
			{
				b[i] = (byte)fillChar;
			}
		}
		else
		{
			if (leftJustify == true)
			{
				for (i = 0, j = 0; i < width; i++)
				{
					if (i < src.length)
					{
						b[i] = src[j++];
					}
					else
					{
						b[i] = (byte)fillChar;
					}
				}
			}
			else
			{
				for (i = 0, j = 0; i < width; i++)
				{
					if (i < width - src.length)
					{
						b[i] = (byte)fillChar;
					}
					else
					{
						b[i] = src[j++];
					}
				}
			}
		}
		return new String(b);
	}
	
	/**
	 * 주어진 문자열을 포맷팅하여 반환합니다.
	 * 
	 * @param src	원본 문자열의 바이트 배열.
	 * @param width	포맷팅하고자 하는 결과 문자열의 길이.
	 * @param fillChar	공백을 채울 문자.
	 * @param leftJustify	왼쪽으로 맞출 것인지.
	 * @param charset	인코딩하고자 하는 문자셋.
	 * @return	결과 문자열.
	 * @throws UnsupportedEncodingException 문자셋이 지원되지 않을 경우.
	 */
	public static String format(byte[] src, int width, char fillChar, boolean leftJustify, String charset) throws UnsupportedEncodingException
	{
		int i, j;
		byte[] b = new byte[width];
		if (src == null)
		{
			for (i = 0; i < width; i++)
			{
				b[i] = (byte)fillChar;
			}
		}
		else
		{
			if (leftJustify == true)
			{
				for (i = 0, j = 0; i < width; i++)
				{
					if (i < src.length)
					{
						b[i] = src[j++];
					}
					else
					{
						b[i] = (byte)fillChar;
					}
				}
			}
			else
			{
				for (i = 0, j = 0; i < width; i++)
				{
					if (i < width - src.length)
					{
						b[i] = (byte)fillChar;
					}
					else
					{
						b[i] = src[j++];
					}
				}
			}
		}
		return new String(b, 0, b.length, charset);
	}
	
	/**
	 * String에서 주어진 길이 이상은 잘라낸다. 
	 * 한글이 중간에 잘린경우 해당 한글도 잘라낸다.
	 *  
	 * @param src
	 * @param size
	 * @return
	 */
	public static String trimHan(String src, int size)
	{
		if (src==null || size <=0 ) return "";
		
		byte[] srcBytes = src.getBytes();
		int len = srcBytes.length;
		
		if (size>=len) return src;
		
		byte[] dstBytes = new byte[size];
		
		int hanCnt=0;
		for (int i=0; i < size; i++) {
			dstBytes[i] = srcBytes[i];
			if ((dstBytes[i]&0xFF) > 0x7F) hanCnt++;
		}
		
		if (((hanCnt%2)!=0) && ((dstBytes[size-1]&0xFF)> 0x7F)) size--;
		
		return new String(dstBytes, 0, size);
	}
	
	/**
	 * 
	 * @param b
	 * @param off
	 * @param len
	 * @return
	 */
	public static String dump(byte[] b, int off, int len) {
		
		StringBuffer sb = new StringBuffer();
		int line = len / 16 + 1;
		String s;
        for (int i = 0; i < line; i++) {
           	sb.append(format(Integer.toHexString(i*16), 8, '0', false));
        	sb.append(' ');
        	for (int j = 0; j < 8; j++) {
        		int k = i*16 + j*2;
        		if (k < len) {
        			s = Integer.toHexString((int)b[off+k+0]&0xff);
        			sb.append(format(s, 2, '0', false));
        		}
        		else {
        			sb.append("00");
        		}
        		if (k+1 < len) {
        			s = Integer.toHexString((int)b[off+k+1]&0xff);
        			sb.append(format(s, 2, '0', false));
        		}
        		else {
        			sb.append("00");
        		}
        		sb.append(' ');
        	}
        	sb.append(' ');
        	StringBuffer sb2 = new StringBuffer();
        	for (int j = 0; j < 16; j++) {
        		int k = i*16 + j;
        		if (k < len) {
        			char c = (char)((int)b[off+k]&0xff);
        			if (c < 0x20) {
        				sb2.append('.');
        			}
        			else {
        				sb2.append(c);
        			}
                }
                else {
                	sb2.append('.');
                }
        	}
        	s = sb2.toString();
        	sb.append(s);
        	sb.append('\n');
        }
        sb.append(len);
        sb.append('\n');
        return sb.toString();
	}
	
	/**
	 * 16진수값으로 바이트 배열을 출력합니다.
	 * 
	 * @param b	바이트 배열.
	 * @param off	바이트 배열의 시작옵셋.
	 * @param len	시작옵셋으로부터의 길이.
	 * @param charset	인코딩할 문자셋.
	 * @return	16진수 형식으로 포맷팅된 문자열.
	 * @throws UnsupportedEncodingException 지원되지 않는 문자셋을 사용할 경우.
	 */
	public static String dump(byte[] b, int off, int len, String charset) throws UnsupportedEncodingException
	{
		StringBuffer sb = new StringBuffer();
		int line = len / 16 + 1;
		String s;
		for (int i = 0; i < line; i++)
		{
			sb.append(format(Integer.toHexString(i*16), 8, '0', false));
			sb.append(' ');
			for (int j = 0; j < 8; j++)
			{
				int k = i*16 + j*2;
				if (k < len)
				{
					s = Integer.toHexString((int)b[off+k+0]&0xff);
					sb.append(format(s, 2, '0', false));
				}
				else
				{
					sb.append("00");
				}
				if (k+1 < len)
				{
					s = Integer.toHexString((int)b[off+k+1]&0xff);
					sb.append(format(s, 2, '0', false));
				}
				else
				{
					sb.append("00");
				}
				sb.append(' ');
			}
			sb.append(' ');
			StringBuffer sb2 = new StringBuffer();
			for (int j = 0; j < 16; j++)
			{
				int k = i*16 + j;
				if (k < len)
				{
					char c = (char)((int)b[off+k]&0xff);
					if (c < 0x20)
					{
						sb2.append('.');
					}
					else
					{
						sb2.append(c);
					}
				}
				else
				{
					sb2.append('.');
				}
			}
			s = new String(sb2.toString().getBytes("8859_1"), charset);
			sb.append(s);
			sb.append('\n');
		}
		sb.append(len);
		sb.append('\n');
		return sb.toString();
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	public static int parseInt(String value) {
		if (value==null || value.length()==0) return 0;
        String v = "";
        int radix = 10; /* default 10진수 */
        if (value.startsWith("0x") || value.startsWith("0X")) { /* 16진수 */
            radix = 16;
            v = value.substring(2);
        } else {
            radix = 10;
            v = value;
        }
        return Integer.parseInt(v, radix);
	}
	
	/**
	 * 16진수값으로된 String을 int형으로 출력합니다.
	 * 
	 * @param str	String Hex 문자열.
	 * @return	10진수 형식으로 포맷팅된 int.
	 */
	public static int stringHexToInt(String str){
		byte[] b2 = new byte[str.length()];

		int temp = 0;
		for(int i=0;i<str.length();i++) {
			if(str.substring(i,i+1).equals("A") || str.substring(i,i+1).equals("a"))
				b2[i]=(byte)0x0A; 
			else if(str.substring(i,i+1).equals("B") || str.substring(i,i+1).equals("b"))
				b2[i]=(byte)0x0B; 
			else if(str.substring(i,i+1).equals("C") || str.substring(i,i+1).equals("c"))
				b2[i]=(byte)0x0C;
			else if(str.substring(i,i+1).equals("D") || str.substring(i,i+1).equals("d"))
				b2[i]=(byte)0x0D;
			else if(str.substring(i,i+1).equals("E") || str.substring(i,i+1).equals("e"))
				b2[i]=(byte)0x0E;
			else if(str.substring(i,i+1).equals("F") || str.substring(i,i+1).equals("f"))
				b2[i]=(byte)0x0F;
			else
				b2[i]=(byte)(Integer.parseInt(str.substring(i,i+1)) & 0x0F);

			temp = temp+(b2[i] << 4*(str.length()-(i+1)));
		}
		return temp;
	}
	
	public static String stringToBinary(String str){
		String binaryString = "";
		String temp = "";
		int off = 0;
		for(int i=0;i<str.length();i++){
			int value = StringUtil.stringHexToInt(str.substring(off, off+1));
			binaryString = Integer.toBinaryString(value);
			while(binaryString.length() % 4 != 0) {
				binaryString = "0" + binaryString;
			}
			
			temp = temp+binaryString;
			off++;
		}
		return temp;
	}
	
	public static int binaryToDecimal(String binary) {
		int decimal = 0;
		int exponential = 1;
		int digit = 0;
		
		for(int i=0;i<binary.length(); i++ ){
			digit = Integer.parseInt(binary.substring(binary.length()-1-i, binary.length()-i)); //여기서 문자0,1을 숫자로바꿈
			decimal = decimal + exponential * digit; // 10진수로 변환
			exponential = exponential * 2;
		}
		return decimal;
	}
	
	//압축을 하는 메소드입니다. 문장을 입력받아서 압축된 문장을 리턴합니다.
	public static String stringPack(String str) {
		StringWriter sw = new StringWriter();
		int i = 0;
		int num = 0;

		for(i = 0; i < str.length();) {
			num = 1;
			for(int j = (i + 1); j < str.length(); j++) {
				if(str.charAt(i) == str.charAt(j)) {
					num++;
				}else {					
					break;
				}
			}
			sw.write(str.charAt(i) + "(" + (num) + ")");	
			i += (num);
		}
		return sw.toString();
	}
	
	/**
	 * MAP Version에 따라 DATETIME 필드 포맷이 변경됨(환승정보내역파일(교통칩)만 사용)
	 * 
	 * @param str	bit형 문자열.
	 * @return	yyyymmddhhnnss로 포맷팅한 문자열.
	 */
	public static String binaryToDateTime(String str){
		int len = 0;
		
		String temp = stringToBinary(str);
		
		String yy = 2000 + Integer.parseInt(temp.substring(len, len+7), 2)+"";
		len = len + 7;
		
		String mm = Integer.parseInt(temp.substring(len, len+4), 2) + "";
		while(mm.length() % 2 != 0) {
			mm = "0" + mm;
		}
		len = len + 4;

		String dd = Integer.parseInt(temp.substring(len, len+5), 2) + "";
		while(dd.length() % 2 != 0) {
			dd = "0" + dd;
		}
		len = len + 5;

		String hh = Integer.parseInt(temp.substring(len, len+5), 2) + "";
		while(hh.length() % 2 != 0) {
			hh = "0" + hh;
		}
		len = len + 5;

		String nn = Integer.parseInt(temp.substring(len, len+6), 2) + "";
		while(nn.length() % 2 != 0) {
			nn = "0" + nn;
		}
		len = len + 6;

		String ss = Integer.parseInt(temp.substring(len, len+6), 2) + "";
		while(ss.length() % 2 != 0) {
			ss = "0" + ss;
		}
		len = len + 6;
		
		return yy+mm+dd+hh+nn+ss;
	}
	 
	/**
	 * 마이비에 따라 DATETIME 필드 포맷이 변경됨(환승정보내역파일(교통칩)만 사용)
	 * 
	 * @param str	bit형 문자열.
	 * @return	yyyymmddhhnnss로 포맷팅한 문자열.
	 */
	public static String binaryToDateTimeM(String str){
		int len = 0;
		
		String temp = stringToBinary(str);
		
		String yy = 2000 + Integer.parseInt(temp.substring(len, len+4), 2)+"";
		len = len + 4;
		
		String mm = Integer.parseInt(temp.substring(len, len+4), 2) + "";
		while(mm.length() % 2 != 0) {
			mm = "0" + mm;
		}
		len = len + 4;

		String dd = Integer.parseInt(temp.substring(len, len+5), 2) + "";
		while(dd.length() % 2 != 0) {
			dd = "0" + dd;
		}
		len = len + 5;

		String hh = Integer.parseInt(temp.substring(len, len+5), 2) + "";
		while(hh.length() % 2 != 0) {
			hh = "0" + hh;
		}
		len = len + 5;

		String nn = Integer.parseInt(temp.substring(len, len+6), 2) + "";
		while(nn.length() % 2 != 0) {
			nn = "0" + nn;
		}
		len = len + 6;
				
		return yy+mm+dd+hh+nn;
	}
	
	/** 
	 * 입력 String 데이타를 주어진 class에 해당하는 Object를 생성하여
	 * 반환한다.
	 * @param value 입력값
	 * @param clazz 변환 Class
	 * @return object
	 */
	public static Object toObject(String value, Class clazz) {
		Object ret = null;
		
		if (value==null) return null;
		
		if (clazz==String.class) {
			ret = (new String(value)).trim();
		} else if (clazz==Short.class) {
			ret = new Short(value);
		} else if (clazz==Integer.class) {
			ret = new Integer(value);
		} else if (clazz==Byte.class) {
			ret = Byte.decode(value);
		}
		return ret;
	}
	
	public static String convertMDN(String mdn){
		String convertedMdn;
		
		if (mdn.length()==10)
			convertedMdn = mdn.substring(0,3) + "0" + mdn.substring(3);
		else
			convertedMdn = mdn;
		
		return convertedMdn;
	}
	
	public static String nvl(String src) {
		if (src==null) return "";
		return src;
	}
	
	/*
	 * 주민 등록 번호 체크 
	 */
	public static boolean chkJuminNum(String num) {
		if (num == null ) {
			return false;
		} 
		try {
			int a = Integer.parseInt(num.substring(0,6));
			
			if (num.trim().length() == 7) { // 뒷번호
				if (num.charAt(0) != '1' && num.charAt(0) != '2')
					return false;
			} else if (num.trim().length() == 13) { //전체
				int b = Integer.parseInt(num.substring(6,13));
				
				if (num.charAt(6) != '1' && num.charAt(6) != '2')
					return false;
				int yy = Integer.parseInt(num.substring(0,2));
				int mm = Integer.parseInt(num.substring(2,4));
				if (mm < 1 || mm > 12)
					return false;
				int dd = Integer.parseInt(num.substring(4,6));
				int[] mmDD = {31,29,31,30,31,30,31,31,30,31,30,31};
				if (dd < 1 || dd > mmDD[mm-1])
					return false;
			} else 
				return false;
		} catch (Exception e) {
			logger.error("ERROR==>"+e.toString());
			return false;
		}
		return true;
	}
	
	/**
     * 넘어온 문자열의 CharSet을 변경한다.
     * KSC5601 -> 8859_1
     * @param ko
     * @return
     */
    public static String ksc2Uni(String ko) {
        String en = null;
        if (ko==null) return null;
        try {
            en = new String(ko.getBytes("KSC5601"), "8859_1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return en;
    }
    
    /**
     * 넘어온 문자열의 CharSet을 변경한다.
     * 8859_1 -> KSC5601
     * @param en
     * @return
     */
    public static String uni2Ksc(String en) {
        String ko = null;
        if (en==null) return null;
        try {
            ko = new String(en.getBytes("8859_1"), "KSC5601");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ko;
    }
    

    /**
      * 입력 byte array값을 Hex String으로 변환한다.
      * 
      * @param value 입력 byte array
      * @return Hex String
      */
     public static String toHexString(byte[] value) {
      if (value==null) return null;
      
      StringBuffer sb = new StringBuffer();
      
      for (int i=0; i<value.length; i++) {
             int j = (value[i] >> 4) & 0xf;
             if (j <= 9)
                 sb.append(j);
             else
                 sb.append((char)(j+'A'-10));
             j = value[i]&0xf;
             if (j <= 9)
                 sb.append(j);
             else
                 sb.append((char)(j+'A'-10));
         };
      
      return sb.toString();
     }
     
     /**
      * 입력 byte array값을 Hex String으로 변환한다.
      * 
      * @param value 입력 byte array
      * @return Hex String
      */
     public static String toHexStringCase(byte[] value) {
      if (value==null) return null;
      
      StringBuffer sb = new StringBuffer();
      
      for (int i=0; i<value.length; i++) {
             int j = (value[i] >> 4) & 0xf;
             if (j <= 9)
                 sb.append(j);
             else
                 sb.append((char)(j+'a'-10));
             j = value[i]&0xf;
             if (j <= 9)
                 sb.append(j);
             else
                 sb.append((char)(j+'a'-10));
         };
      
      return sb.toString();
     }
     
     /**
      * 입력 byte array값을 Hex String으로 변환한다.
      * 
      * @param value 입력 byte array
      * @return Hex String
      */
     public static byte[] parseHexString(String value) {
      
      if (value==null) return null;
      
      value = value.toUpperCase();
      int len = value.length();
            byte out[] = new byte[len/2];
            
            for(int i = 0; i < len/2; i++)
            {
                int j = i * 2;
                
                int high = 0;
                int low = 0;
                
                if (value.charAt(j) >= '0' && value.charAt(j)<='9')
                 high = value.charAt(j) - '0';
                else if (value.charAt(j) >= 'A' && value.charAt(j)<='F')
                 high = 10 + (value.charAt(j) - 'A');
                
                if (value.charAt(j) >= '0' && value.charAt(j+1)<='9')
                 low = value.charAt(j+1) - '0';
                else if (value.charAt(j+1) >= 'A' && value.charAt(j+1)<='F')
                 low = 10 + (value.charAt(j+1) - 'A');
                
                out[i] = (byte)(high * 16 + low);
            }

            return out;
     }
     
     public static int cutNextString(byte[] body, int sp, int ep) {
 		int endIndex = sp + ep - 1;
 		int cnt = 0;
 	//System.out.println("--"+new String(body));
 		while (endIndex >=0 && ((body[endIndex] & 0xff) > 128 && endIndex >= sp)) {
 			cnt++;
 			endIndex--;
 	//System.out.println("cnt = "+cnt+", endIndex = "+endIndex);
 		}
 		// 한글 바이트 수가 홀수이면 짤렸다고 인식
 		if (cnt%2 == 0)
 			return ep;
 		else 
 			return ep - 1;
 	}
     
     
     public static long getDateDiff(String begin, String end, String format) throws Exception
     {
//logger.debug("s-"+begin+",e-"+end);
       long   datedif;

       try{

    	   SimpleDateFormat formatter = new SimpleDateFormat(format);

    	   

    	    Date beginDate = formatter.parse(begin);
    	    Date endDate = formatter.parse(end);

    	 

    	    long diff = endDate.getTime() - beginDate.getTime();
    	    
    	    long diffMin = diff / (60 * 1000);
    	    if (diffMin < 0)
    	    	diffMin = diffMin * -1;
    	    return diffMin;

       } catch(Exception e){
    	   logger.error("날짜 데이터 처리 중 오류 발생:",e);
    	   throw new Exception(e);
       }

     }
     
     private static String addDelim(String date, String delim) {
 		return date.substring(0,4)+delim+date.substring(4,6)+delim+date.substring(6,8);
 	}

 	private static String delDelim(String date) {
 		return date.substring(0,4)+date.substring(5,7)+date.substring(8,10);
 	}
 	
 	/**
	 * 날짜포맷팅. 
	 *
	 * @param	date	yyyyMMdd
	 */
	public static String getDate(String date, String delim) {
		if (date == null || date.length() < 8)
			return "";

		return addDelim(date, delim);
	}
	

	public static String getCurrentDate()
	{
	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	     return format.format(new Date());
	}
	
	public static String getCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat("HHmmss");
		return format.format(new Date());
	}
	
	public static String getCurrentDateTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date());
	}
	
	/**
	 * 시간 포맷팅
	 * @param time
	 * @param delim
	 * @return
	 */
	public static String getTime(String time) {
		if (time == null || time.length() < 4)
			return "";
		if (time.length() < 6)
			return time.substring(0,2)+":"+time.substring(2,4);
		else
			return time.substring(0,2)+":"+time.substring(2,4)+":"+time.substring(4,6);
	}
	
	public static String getNumberCircle(int num) {
		if (num < 0 || num > 9)
			return "▶";
			
		String[] numIdx = {"①","②","③","④","⑤","⑥","⑦","⑧","⑨"};
		return numIdx[(num-1)];              
	}
	
	/**
	 * 휴대폰번호 포맷
	 *
	 * @param	mdn		휴대전화번호
	 */
	public static String getMdnFmt(String mdn)
	{
		if (mdn == null)
			return "";

		if(!mdn.startsWith("0",0)) {
			mdn = "0"+mdn;
		}

		if (mdn.length() == 11)
			return mdn.substring(0,3)+"-"+mdn.substring(3,7)+"-"+mdn.substring(7);
		else 
			return mdn.substring(0,3)+"-"+mdn.substring(3,6)+"-"+mdn.substring(6);
	}
	
	/**
	 * 빈문자 체크
	 * @param val
	 * @return
	 */
	public static boolean isEmpty(String val) {
		if (val == null || val.trim().length() == 0)
			return true;
		else
			return false;
	}
	
	public static String getAmtFmt(int amt){
		DecimalFormat df = new DecimalFormat("#,###");
		return new String(df.format(amt));
	}
	
	public static String getAmtFmt(double amt){
		DecimalFormat df = new DecimalFormat("#,###.##");
		return new String(df.format(amt));
	}
	
	public static String[] stringToArray(String cmp, String str) {
		if (cmp == null || str == null)
			return null;
		java.util.StringTokenizer st = new java.util.StringTokenizer(str, cmp);
		String[] result = new String[st.countTokens()];

		for (int i = 0; i < result.length; i++) {
			result[i] = st.nextToken().trim();
		}

		return result;
	}
     
    public static String getCardNumFmt(String cardNum) {
    	if(cardNum == null || cardNum.length() != 16)
    		return cardNum;
    	return cardNum.substring(0,4) + "-" + cardNum.substring(4,8) + "-" + cardNum.substring(8,12) + "-" + cardNum.substring(12,16);
    }
    
    /**
     * @param str
     * @param delim
     * @return
     */
    public static String[] toTokens(String str, String delim)
	{
		String[] tokenArr = null;

		if (str == null) return new String[0];
		if (str.indexOf(delim) == -1)
		{
			tokenArr = new String[1];
			tokenArr[0] = str;
			
			return tokenArr;
		}
		
		LinkedList linkedlist = new LinkedList();
		
		int pos = 0, startPos = 0;
		int srcLength    = str.length();
		int tockenLength = delim.length();
		while(startPos < srcLength)
		{
			if (-1 == (pos = str.indexOf(delim, startPos))) break;
			
			linkedlist.add(str.substring(startPos, pos));
			startPos = pos + tockenLength;
		}
		if (startPos <= srcLength) linkedlist.add(str.substring(startPos));
		
		return (String[])linkedlist.toArray(new String[0]);
	}
    
    /**
	 * Base64 Char 중 Web에서 특수문자로 인식하는 부분을 변경한다.
	 * @param input
	 * @return
	 */
	public static String encodeWebBase64(String in) {
		if (in==null) return in;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == '+') 		c = '-';
			else if (c=='=') 	c = '_';
			else if (c=='/')	c = '*';
			sb.append(c);
		}
		return sb.toString();
	}
	
	public static String decodeWebBase64(String in) {
		if (in==null) return in;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < in.length(); i++) {
			char c = in.charAt(i);
			if (c == '-') 		c = '+';
			else if (c=='_') 	c = '=';
			else if (c=='*')	c = '/';
			sb.append(c);
		}
		return sb.toString();
	}
	
	/**
	 * 국번에 0 이 들어있는 경우 제거
	 * @param phoneNo
	 * @return
	 */
	public static String removeZeroOfMdn(String mdn) {
		if (mdn==null || mdn.length()!=11 || mdn.charAt(3)!='0')
			return mdn;
		return (mdn.substring(0,3) + mdn.substring(4));
	}
	
	public static String getJuminNumMasking(String num) {
		if (num==null || num.length() < 6) return num;
		else if (num.length()==6) return "******";
		else if (num.length()==7) return "*******";
		
		return (num.substring(0,6) + "*******");
	}
	
    public static String getCardNumMasking(String cardNum) {
    	if(cardNum == null || cardNum.length() != 16)
    		return cardNum;
    	return cardNum.substring(0,4) + "-" + "****" + "-" + "****" + "-" + cardNum.substring(12,16);
    }
	
    /**
     * SMS / LMS 발송 용
	 * EUK-K로 encoding
	 * yscheon 2013.10.28
     * @throws UnsupportedEncodingException 
	 */
	public static  String encodeKR(String value) throws UnsupportedEncodingException{
		
		if(value == null ){
			value = "";			
		} else {
			value = URLEncoder.encode(value,"EUC-KR");
		}				
		return value;
	}
	
	/**
	 * SMS / LMS 발송 용
	 * MSG체크
	 * @param String
     * @return String ErroCode
	 */
	public static String isMsgCkeck(String msg, String sendType){
		String chkResultCode = "20";
		
		if(msg != null){
			// 전송타입 SMS일때
			if(sendType.equals("5")){
				if(msg.getBytes().length <= 80){
					logger.info("MSG Check - OK " +msg.getBytes().length + "byte");
					return chkResultCode = "00";
				} else {
					logger.info("MSG Check - 80 byte OVER " + msg.getBytes().length + "byte");
					return chkResultCode = "21";				
				}
			// 전송타입 LMS일때
			} else if(sendType.equals("7")){
				if(msg.getBytes().length <= 2000){
					logger.info("MSG Check - OK " +msg.getBytes().length + "byte");
					return chkResultCode = "00";
				} else {
					logger.info("MSG Check - 2000 byte OVER " + msg.getBytes().length + "byte");
					return chkResultCode = "21";				
				}
			// 그외
			}else {
				return chkResultCode= "15";
			}
		} else {
			logger.info("Msg IS NULL");
			return chkResultCode = "20";
		}
	}
	
	/**
	 *  생년월일 + 구분코드
	 * 
	 *  구분코드 성별코드 정보
	 *    - 1: 남(2천년대 이전 생)
	 *    - 2: 여(2천년대 이전 생)
	 *    - 3: 남(2천년대 생)
	 *    - 4: 여(2년년대 생)
	 *    - 5: 외국인 남(2천년대 이전 생)
	 *    - 6: 외국인 여(2천년대 이전 생)
	 *    - 7: 외국인 남(2천년대 생)
	 *    - 8: 외국인 여(2년년대 생)
	 * @param gubun
	 * @return isSex : 남자 :1 여자 :2 
	 *            isForeigner : 1 : 내국인 2 : 외국인   
	 */
	public static HashMap isSexForeigner(String jumin){
		HashMap resultMap = new HashMap();
		
		String isSex = "";			// 성별
		String isForeigner = "";		// 외국인 구분
		
		if( jumin == null || jumin.length() < 7 ){
			return null;
		}
		
		// 생년월일
		String birth = jumin.substring(0, 6);
		if( "".equals(birth.trim()) || birth.length() < 6 ){
			return null;
		}
		
		// 마지막 구분자
		String gubunCode = jumin.substring(6,7);
		
		// 내국인 남자인 경우
		if( "1".equals(gubunCode) || "3".equals(gubunCode) ){
			isSex = "1";
			isForeigner = "1";
			// 내국인 여자
		} else if( "2".equals(gubunCode) || "4".equals(gubunCode) ){
			isSex = "2";
			isForeigner = "1";
			
			// 외국인 남자
		} else if( "5".equals(gubunCode) || "7".equals(gubunCode) ){
			isSex = "1";
			isForeigner = "2";
			
			// 외국인 여자
		} else if( "6".equals(gubunCode) || "8".equals(gubunCode) ){
			isSex = "2";
			isForeigner = "2";
		} else {
			return null;
		}

		if( "1".equals(gubunCode) || "2".equals(gubunCode) 
				|| "5".equals(gubunCode) || "6".equals(gubunCode) ){
			birth = "19"+birth;
		} else {
			birth = "20"+birth;
		}
		
		resultMap.put("birth", birth);
		resultMap.put("isSex", isSex);
		resultMap.put("isForeigner", isForeigner);
		
		return resultMap;
	}
}

