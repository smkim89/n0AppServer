package com.swag.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utility {
	
	static Logger logger = LoggerFactory.getLogger(Utility.class);
	
	public enum ALIGN {
		LEFT, RIGHT
	};

	private static final byte[] convertMap = { 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0,
			0,
			0,
			0,
			0,
			0,
			0, // 0~15
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0,
			0,
			0,
			0,
			0,
			0, // 16~31
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0,
			0,
			0,
			0,
			0,
			0, // 32~47
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
			0,
			0,
			0,
			0,
			0,
			0, // 48~63
			0, 10, 11, 12, 13, 14, 15, 0, 0, 0,
			0,
			0,
			0,
			0,
			0,
			0, // 64~79
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0,
			0,
			0,
			0,
			0,
			0, // 80~95
			0, 10, 11, 12, 13, 14, 15, 0, 0, 0,
			0,
			0,
			0,
			0,
			0,
			0, // 96~107
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };

	private static final char[] convertMap2 = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	// ////////////////////////////////////////////////////////////////////////
	// Converters -- generic ?
	public static short bytesToShort(byte[] bytes) {
		if (bytes == null) {
			return 0;
		}
		short ret = 0;
		for (int i = 0; i < bytes.length; ++i) {
			ret <<= 8;
			ret += bytes[i] & 0xFF;
		}
		return ret;
	}

    public static int bytesToInt4(byte[] bytes) {
        if (bytes == null) {
            return 0;
        }
        int ret = 0;
        for (int i = 0; i < 4; ++i) {
            ret <<= 8;
            ret += bytes[i] & 0xFF;
        }
        return ret;
    }
	
	public static int bytesToInt(byte[] bytes) {
		if (bytes == null) {
			return 0;
		}
		int ret = 0;
		for (int i = 0; i < bytes.length; ++i) {
			ret <<= 8;
			ret += bytes[i] & 0xFF;
		}
		return ret;
	}

	public static long bytesToLong(byte[] bytes) {
		if (bytes == null) {
			return 0;
		}
		long ret = 0;
		for (int i = 0; i < bytes.length; ++i) {
			ret <<= 8;
			ret += bytes[i] & 0xFF;
		}
		return ret;
	}

	public static byte[] shortToBytes(short n) {
		byte[] bytes = new byte[2];
		for (int i = 0; i < 2; ++i) {
			bytes[1 - i] = (byte) (n & 0xff);
			n >>= 8;
		}
		return bytes;
	}

	public static byte[] intToBytes(int n) {
		byte[] bytes = new byte[4];
		for (int i = 0; i < 4; ++i) {
			bytes[3 - i] = (byte) (n & 0xff);
			n >>= 8;
		}
		return bytes;
	}

	public static byte[] longToBytes(long n) {
		byte[] bytes = new byte[8];
		for (int i = 0; i < 8; ++i) {
			bytes[7 - i] = (byte) (n & 0xff);
			n >>= 8;
		}
		return bytes;
	}

	public static byte[] hexToBytes(String str) {
		byte[] b = null;
		if (str == null || str.length() < 2) {
			return null;
		}
		int len = str.length() / 2;
		b = new byte[len];
		for (int i = 0; i < len; ++i) {
			b[i] = (byte) ((convertMap[(byte) str.charAt(i * 2)] << 4) | (convertMap[(byte) str
					.charAt(i * 2 + 1)]));
		}
		return b;
	}

	public static String bytesToHex(byte[] bytes) {
		if (bytes == null) {
			return "";
		}
		int len = bytes.length;
		StringBuffer sb = new StringBuffer(len * 2);

		for (int i = 0; i < len; ++i) {
			sb.append(convertMap2[(bytes[i] & 0xf0) >> 4]);
			sb.append(convertMap2[bytes[i] & 0xf]);
		}
		return sb.toString();
	}

	public static int stringHexToInt(String str) {
		byte[] b2 = new byte[str.length()];

		int temp = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.substring(i, i + 1).equals("A")
					|| str.substring(i, i + 1).equals("a"))
				b2[i] = (byte) 0x0A;
			else if (str.substring(i, i + 1).equals("B")
					|| str.substring(i, i + 1).equals("b"))
				b2[i] = (byte) 0x0B;
			else if (str.substring(i, i + 1).equals("C")
					|| str.substring(i, i + 1).equals("c"))
				b2[i] = (byte) 0x0C;
			else if (str.substring(i, i + 1).equals("D")
					|| str.substring(i, i + 1).equals("d"))
				b2[i] = (byte) 0x0D;
			else if (str.substring(i, i + 1).equals("E")
					|| str.substring(i, i + 1).equals("e"))
				b2[i] = (byte) 0x0E;
			else if (str.substring(i, i + 1).equals("F")
					|| str.substring(i, i + 1).equals("f"))
				b2[i] = (byte) 0x0F;
			else
				b2[i] = (byte) (Integer.parseInt(str.substring(i, i + 1)) & 0x0F);

			temp = temp + (b2[i] << 4 * (str.length() - (i + 1)));
		}
		return temp;
	}

	public static int computeShortValue(byte one, byte two) {
		return computeShortValue((int) one, (int) two);
	}

	public static int computeShortValue(int one, int two) {
		return ((one & 0xff) << 8) | (two & 0xff);
	}

	// ////////////////////////////////////////////////////////////////////////
	// NULL
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

	public static String NVLStr(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	public static int NVLInt(Object obj) {
		if (obj == null) {
			return 0;
		} else if (obj instanceof Integer) {
			return (Integer) obj;
		} else if (obj instanceof Byte) {
			return (Byte) obj;
		} else if (obj instanceof Short) {
			return (Short) obj;
		} else if (obj instanceof Long) {
			return ((Long) obj).intValue();
		} else if (obj instanceof String) {
			try {
				return Integer.parseInt((String) obj);
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}

	// ////////////////////////////////////////////////////////////////////////
	// Generated String
	public static String genChars(char c, int repeat) {
		StringBuffer sb = new StringBuffer(repeat);
		for (int i = 0; i < repeat; ++i) {
			sb.append(c);
		}
		return sb.toString();
	}

	// ////////////////////////////////////////////////////////////////////////
	// Formating
	public static String formatInt(int num, int len) {
		return formatStr(Integer.toString(num), len, ALIGN.RIGHT, (byte) '0');
	}

	public static String formatStr(String str, int len, ALIGN align,	byte padding) {
		int curLen = str.length();
		if (curLen == len) {
			return str;
		}
		if (curLen >= len) {
			return (align == ALIGN.LEFT) ? str.substring(0, len) : str
					.substring(curLen - len);
		}
		StringBuffer add = new StringBuffer(len - curLen);
		for (; curLen < len; ++curLen) {
			add.append((char) padding);
		}
		return (ALIGN.LEFT == align) ? str + add : add + str;
	}

	public static byte[] formatBytes(byte[] bytes, int len, ALIGN align,
			byte padding) {
		byte[] newBytes;
		int curLen = bytes.length;
		if (curLen >= len) {
			int startIdx = (align == ALIGN.LEFT) ? 0 : curLen - len;
			newBytes = new byte[len];
			System.arraycopy(bytes, startIdx, newBytes, 0, len);
		} else {
			newBytes = new byte[len];
			int startIdx = (align == ALIGN.LEFT) ? 0 : len - curLen;
			System.arraycopy(bytes, 0, newBytes, startIdx, curLen);
			for (int i = 0; i < startIdx; ++i) {
				newBytes[i] = padding;
			}
			for (int i = startIdx + curLen; i < len; ++i) {
				newBytes[i] = padding;
			}
		}
		return newBytes;
	}

	/**
	 * 숫자형식이면 앞의 0 을 없애고 숫자형식이 아니면 "" 를 리턴한다.
	 * 
	 * @param in
	 * @return
	 */
	static public String toIntOrEmpty(String in) {
		if (in == null || in.isEmpty()) {
			return "";
		}

		try {
			return Integer.toString(Integer.parseInt(in));
		} catch (Exception e) {
			logger.error("ERROR:" + e.getMessage());
			return "";
		}
	}

	// ////////////////////////////////////////////////////////////////////////
	// Calendar
	public static long now() {
		return Calendar.getInstance().getTimeInMillis();
	}

	public static String nowDate() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(date);
	}

	public static String getDate(long d) {
		Date date = new Date(d);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(date);
	}

	// 2010.08.31 Brandon 실명인증 관련 생성
	public static String getMmdd(long d) {
		Date date = new Date(d);
		SimpleDateFormat df = new SimpleDateFormat("MMdd");
		return df.format(date);
	}

	public static String calDate(int d) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, d);
		Date date = calendar.getTime();

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(date);
	}

	public static String nowTime() {
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("HHmmss");
		return df.format(date);
	}

	// 2010.05.31 Brandon
	public static String miliSecond() {
		int militimeI = Calendar.getInstance().get(Calendar.MILLISECOND);
		String militimeS = "0000" + String.valueOf(militimeI);
		String militime = militimeS.substring(militimeS.length() - 4);
		return militime;
	}

	public static String getTime(long t) {
		Date date = new Date(t);
		SimpleDateFormat df = new SimpleDateFormat("HHmmss");
		return df.format(date);
	}

	public static String nowDateTime() {
		return getDateTime(now());
	}

	public static String getDateTime(long l) {
		return getDate(l) + getTime(l);
	}

	public static String getDateAdd(String dateStr, int field, int amount) {
		int year = 1900;
		int month = 0;
		int day = 1;

		Calendar cal = Calendar.getInstance();
		if (dateStr.length() >= 4) {
			year = Integer.parseInt(dateStr.substring(0, 4));
		}
		if (dateStr.length() >= 6) {
			month = Integer.parseInt(dateStr.substring(4, 6)) - 1;
		}
		if (dateStr.length() >= 8) {
			day = Integer.parseInt(dateStr.substring(6, 8));
		}
		cal.set(year, month, day);
		cal.add(field, amount);

		return getDate(cal.getTimeInMillis());
	}

	// ////////////////////////////////////////////////////////////////////////
	// Socket

	/**
	 * 전문 송 수신 함수 <li>buffer 만큼의 데이터를 송수신한다. 3000ms 의 타임아웃이 적용된다.
	 * 
	 * @param ip
	 * @param port
	 * @param buffer
	 * @return
	 */
	public static byte[] exchangeData(String ip, int port, byte[] buffer) {
		return exchangeData(ip, port, buffer, 0, buffer.length);
	}

	// 2012.06.21 By Brandon
	// exchangeDataOneWay
	public static void exchangeDataOneWay(String ip, int port, byte[] buffer) {
		exchangeDataOneWay(ip, port, buffer, 0, buffer.length);
	}
	
	/**
	 * 재시도 처리 추가 전문 송 수신 함수 <li>buffer 만큼의 데이터를 송수신한다. 3000ms 의 타임아웃이 적용된다. 
	 * 
	 * @param ip
	 * @param port
	 * @param buffer
	 * @param retryCnt
	 * @return
	 */
	public static byte[] retryExchangeData(String ip, int port, byte[] buffer,int retryCnt) {
		byte[]  rsp = null;
		
		for(int cnt=0; cnt < retryCnt; cnt++) { 
			rsp = exchangeData(ip, port, buffer, 0, buffer.length);
			if(rsp!=null) {
				break;
			}
		}
		return rsp;
	}
	
	/**
	 * 전문 송 수신 함수 <li>buffer 만큼의 전문을 송수신한다. 4000ms 의 타임아웃이 적용된다.
	 * 4000ms -> 7000ms로 변경 - yscheon 2013.09.26
	 * 7000ms -> 8000ms로 변경 - yscheon 2014.03.13
	 * 
	 * @param ip
	 * @param port
	 * @param buffer
	 * @param offset
	 * @param length
	 * @return
	 */
	public static byte[] exchangeData(String ip, int port, byte[] buffer,
			int offset, int length) {
		return exchangeData(ip, port, buffer, offset, length, 8000);
	}

	/**
	 * 전문 송 수신 함수 <li>buffer 의 offset ~ offset + length 만큼의 전문을 송수신한다. timout_ms
	 * (ms) 만큼의 타임아웃이 적용된다.
	 * 
	 * @param ip
	 * @param port
	 * @param buffer
	 * @param offset
	 * @param length
	 * @param timeout_ms
	 * @return
	 */
	

	
	public static byte[] exchangeData(String ip, int port, byte[] buffer,
			int offset, int length, int timeout_ms) {
		if (buffer == null || ip == null || offset < 0 || length <= 0) {
			return null;
		}

		Socket sock = null;
		InputStream is = null;
		OutputStream os = null;
		byte[] rsp = null;

		try {
			sock = new Socket();
			sock.setSoTimeout(timeout_ms);
			sock.connect(new InetSocketAddress(ip, port), timeout_ms);
			is = sock.getInputStream();
			os = sock.getOutputStream();

			os.write(buffer, offset, length);
			os.flush();

			rsp = new byte[length];
			int nread = is.read(rsp);
			if (nread <= 0) {
				return null;
			}
			// TODO received length 만큼 buffer size 조절하기

		} catch (IOException e) {
			logger.error("IO exception!", e);
			rsp = null;
		} finally {
			if (sock != null) {
				try {
					sock.close();
				} catch (IOException e1) {
					logger.error("IO exception!", e1);
				}
			}
		}
		return rsp;
	}
	
	// 2012.06.21 By Brandon
	// exchangeDataOneWay(ip, port, buffer, 0, buffer.length);
	public static void exchangeDataOneWay(String ip, int port, byte[] buffer,
			int offset, int length) {
		if (buffer == null || ip == null || offset < 0 || length <= 0) {
			return;
		}

		Socket sock = null;
		InputStream is = null;
		OutputStream os = null;
		
		try {
			sock = new Socket();
			sock.connect(new InetSocketAddress(ip, port) );
			is = sock.getInputStream();
			os = sock.getOutputStream();

			os.write(buffer, offset, length);
			os.flush();

	
			// TODO received length 만큼 buffer size 조절하기

		} catch (IOException e) {
			logger.error("IO exception!", e);
		} finally {
			if (sock != null) {
				try {
					sock.close();
				} catch (IOException e1) {
					logger.error("IO exception!", e1);
				}
			}
		}
		
	}

	/**
	 * 전문 송 수신 함수 <li>buffer 의 offset ~ offset + sLnegth 만큼의 전문을 송신한다. rLength
	 * 만큼의 전문을 수신 한다. timout_ms (ms) 만큼의 타임아웃이 적용된다.
	 * 
	 * @param ip
	 * @param port
	 * @param buffer
	 * @param offset
	 * @param sLnegth
	 * @param rLength
	 * @param timeout_ms
	 * @return
	 */
	public static byte[] exchangeData(String ip, int port, byte[] buffer,
			int offset, int sLnegth, int rLength, int timeout_ms) {
		if (buffer == null || ip == null || offset < 0 || sLnegth <= 0) {
			return null;
		}

		Socket sock = null;
		InputStream is = null;
		OutputStream os = null;
		byte[] rsp = null;

		try {
			sock = new Socket();
			sock.setSoTimeout(timeout_ms);
			sock.connect(new InetSocketAddress(ip, port), timeout_ms);
			is = sock.getInputStream();
			os = sock.getOutputStream();

			os.write(buffer, offset, sLnegth);
			os.flush();

			rsp = new byte[rLength];
			int nread = is.read(rsp);
			if (nread <= 0) {
				return null;
			}
			// TODO received length 만큼 buffer size 조절하기

		} catch (IOException e) {
			logger.error("IO exception!", e);
			rsp = null;
		} finally {
			if (sock != null) {
				try {
					sock.close();
				} catch (IOException e1) {
					logger.error("IO exception!", e1);
				}
			}
		}
		return rsp;
	}

	
	public static byte[] exchangeDataLenFree(String ip, int port, byte[] buffer) {
		return exchangeDataLenFree(ip, port, buffer, 10000);
	}
	
	public static byte[] exchangeDataLenFree(String ip, int port,
			byte[] buffer, int timeout_ms) {
		if (buffer == null || ip == null) {
			return null;
		}

		Socket sock = null;
		InputStream is = null;
		OutputStream os = null;
		byte[] data = null;
		byte[] header = new byte[4];
		byte[] rsp = null;
		try {
			sock = new Socket();
			sock.setSoTimeout(timeout_ms);
			sock.connect(new InetSocketAddress(ip, port), timeout_ms);
			is = sock.getInputStream();
			os = sock.getOutputStream();

			os.write(buffer, 0, buffer.length);
			os.flush();

			int nread = is.read(header);
			
			if(new String(new byte[] { header[0], header[1]}).equals("ER")){
				return null;
			}
			byte[] length = new byte[] { header[2], header[3] };
			int intLength = Utility.bytesToShort(length);
			data = new byte[intLength];
			is.read(data);
			rsp = new byte[4 + intLength];

			System.arraycopy(header, 0, rsp, 0, header.length);
			System.arraycopy(data, 0, rsp, header.length, data.length);
			if (nread <= 0) {
				return null;
			}
			// TODO received length 만큼 buffer size 조절하기

		} catch (Exception e) {
			logger.error("exception!", e);
			rsp = null;
		} finally {
			if (sock != null) {
				try {
					sock.close();
				} catch (Exception e1) {
					logger.error("exception!", e1);
				}
			}
		}
		return rsp;
	}

	// ////////////////////////////////////////////////////////////////////////
	// System
	public static List<String> getLocalIpList() {
		List<String> ips = new ArrayList<String>();
		try {
			Enumeration<?> ne = NetworkInterface.getNetworkInterfaces();
			while (ne.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) ne.nextElement();
				// System.out.println("Name : " + ni.getName());
				Enumeration<?> inets = ni.getInetAddresses();
				while (inets.hasMoreElements()) {
					InetAddress iaddr = (InetAddress) inets.nextElement();
					// System.out.println("INet --> isSiteLocalAddress() -->" +
					// iaddr.isSiteLocalAddress());
					System.out.println("IP -->" + iaddr.getHostAddress());

					if ((iaddr.isSiteLocalAddress() || iaddr
							.isLinkLocalAddress())
							&& !iaddr.getHostAddress().equals("127.0.0.1")) {
						ips.add(iaddr.getHostAddress());
					}
				}
			}
		} catch (SocketException e) {
			logger.error("SocketException:", e);
		}
		return ips;
	}

	public static String stringToBinary(String str) {
		String binaryString = "";
		String temp = "";
		int off = 0;
		for (int i = 0; i < str.length(); i++) {
			int value = stringHexToInt(str.substring(off, off + 1));
			binaryString = Integer.toBinaryString(value);
			while (binaryString.length() % 4 != 0) {
				binaryString = "0" + binaryString;
			}

			temp = temp + binaryString;
			off++;
		}
		return temp;
	}

	/**
	 * MAP Version에 따라 DATETIME 필드 포맷이 변경됨(환승정보내역파일(교통칩)만 사용)
	 * 
	 * @param str
	 *            bit형 문자열.
	 * @return yyyymmddhhnnss로 포맷팅한 문자열.
	 */
	public static String binaryToDateTime(String str) {
		int len = 0;

		String temp = stringToBinary(str);

		String yy = 2000 + Integer.parseInt(temp.substring(len, len + 7), 2)
				+ "";
		len = len + 7;

		String mm = Integer.parseInt(temp.substring(len, len + 4), 2) + "";
		while (mm.length() % 2 != 0) {
			mm = "0" + mm;
		}
		len = len + 4;

		String dd = Integer.parseInt(temp.substring(len, len + 5), 2) + "";
		while (dd.length() % 2 != 0) {
			dd = "0" + dd;
		}
		len = len + 5;

		String hh = Integer.parseInt(temp.substring(len, len + 5), 2) + "";
		while (hh.length() % 2 != 0) {
			hh = "0" + hh;
		}
		len = len + 5;

		String nn = Integer.parseInt(temp.substring(len, len + 6), 2) + "";
		while (nn.length() % 2 != 0) {
			nn = "0" + nn;
		}
		len = len + 6;

		String ss = Integer.parseInt(temp.substring(len, len + 6), 2) + "";
		while (ss.length() % 2 != 0) {
			ss = "0" + ss;
		}
		len = len + 6;

		return yy + mm + dd + hh + nn + ss;
	}

	/**
	 * 전문 송 수신 함수 <li>buffer 만큼의 데이터를 송수신한다. 7000ms 의 타임아웃이 적용된다.
	 * 
	 * yscheon 2012.11.19
	 * 
	 * @param ip
	 * @param port
	 * @param buffer
	 * @return
	 */
	public static byte[] exchangeDataPg(String ip, int port, byte[] buffer) {
		return exchangeDataPg(ip, port, buffer, 0, buffer.length);
	}
	
	/**
	 * 전문 송 수신 함수 <li>buffer 만큼의 전문을 송수신한다. 7000ms 의 타임아웃이 적용된다.
	 * 
	 *  yscheon 2012.11.19
	 *  
	 * @param ip
	 * @param port
	 * @param buffer
	 * @param offset
	 * @param length
	 * @return
	 */
	public static byte[] exchangeDataPg(String ip, int port, byte[] buffer,
			int offset, int length) {
		return exchangeData(ip, port, buffer, offset, length, 7000);
	}
	
/*
	public static byte[] encryptData(byte[] plainData) {
		return encryptData(plainData, "SecretKey.ser");
	}
	
    public static byte[] encryptData(byte[] plainData, String fileNm) {
		InputStream is = null;
		ObjectInputStream ois = null;
		Key key = null;
		try {
			is = 	Utility.class.getClassLoader().getResourceAsStream("config/key/" + fileNm);
			ois = new ObjectInputStream(is);
			key = (Key)ois.readObject();
		} catch (Exception e) {
			logger.error("[encryptData] 암호화 KEY 파일 읽을때 오류발생:" + e);
			return null;
		} finally {
			if (ois!=null) {
				try {
					ois.close();
				} catch (Exception e) {}
			}
			
			if (is!=null) {
				try {
					is.close();
				} catch (Exception e) {}
			}
		}
		
		byte[] encryptedData = null;
		try {
			encryptedData = CryptoDES.encrypt(plainData, key);
		} catch (Exception e) {
			logger.error("[encryptData] 암호화시 오류 발생:" + e);
			return null;
		}
		
//		String encodedData = CryptoBase64.encode(encryptedData);
		String encodedData = new String(Base64.encodeBase64(encryptedData));
		
		return encodedData.getBytes();
	}
    
    public static byte[]  decryptData(byte[] encryptData) { 
    	return decryptData(encryptData,  "SecretKey.ser");
    }
    
    public static byte[]  decryptData(byte[] encryptData , String fileNm) {
		InputStream is = null;
		ObjectInputStream ois = null;
		Key key = null;
		try {
			is = 	Utility.class.getClassLoader().getResourceAsStream("config/key/" + fileNm);
			ois = new ObjectInputStream(is);
			key = (Key)ois.readObject();
		} catch (Exception e) {
			logger.error("[decryptData] 암호화 KEY 파일 읽을때 오류발생:" + e);
			return null;
		} finally {
			if (ois!=null) {
				try {
					ois.close();
				} catch (Exception e) {}
			}
			
			if (is!=null) {
				try {
					is.close();
				} catch (Exception e) {}
			}
		}
		
		//byte[] decodedData = CryptoBase64.decode((new String(encryptData)).trim());
		byte[] decodedData = Base64.decodeBase64(encryptData);
		byte[] plainData = null;
		try {
			plainData = CryptoDES.decrypt(decodedData, key);
		} catch (Exception e) {
			logger.error("[decryptData] 복호화시 오류 발생:" + e);
			return null;
		}
		
		return plainData;
	}
*/
	/**
	 * 
	 * dklee 2013.11.06
	 * 
	 * @param ip
	 * @param port
	 * @param buffer
	 * @return
	 * @throws TosaException 
	 */
	public static byte[] exchangeDataSocket(String ip, int port, byte[] buffer) throws TosaException {
		return exchangeDataSocket(ip, port, buffer, 0, buffer.length);
	}
	
	/**
	 * 전문 송 수신 함수 <li>buffer 만큼의 전문을 송수신한다. 10000ms 의 타임아웃이 적용된다.
	 * 
	 *  dklee 2013.11.06
	 *  
	 * @param ip
	 * @param port
	 * @param buffer
	 * @param offset
	 * @param length
	 * @return
	 * @throws TosaException 
	 */
	public static byte[] exchangeDataSocket(String ip, int port, byte[] buffer,
			int offset, int length) throws TosaException {
		return exchangeDataSocket(ip, port, buffer, offset, length, 10000);
	}
	
	// 소켓 타임아웃 Exception 처리를 해준다. -add dklee 2013.11.07
	public static byte[] exchangeDataSocket(String ip, int port, byte[] buffer,
			int offset, int length, int timeout_ms) throws TosaException {
		if (buffer == null || ip == null || offset < 0 || length <= 0) {
			return null;
		}

		Socket sock = null;
		InputStream is = null;
		OutputStream os = null;
		byte[] rsp = null;
		long startTime = System.currentTimeMillis();

		try {
			sock = new Socket();
			sock.setSoTimeout(timeout_ms);
			sock.connect(new InetSocketAddress(ip, port), timeout_ms);
			is = sock.getInputStream();
			os = sock.getOutputStream();
			
			os.write(buffer, offset, length);
			os.flush();

			rsp = new byte[length];
			int nread = is.read(rsp);
			if (nread <= 0) {
				return null;
			}
			// TODO received length 만큼 buffer size 조절하기
			
		// 소켓 타임아웃 Exception 발생	
		}catch(SocketTimeoutException e){
			e.printStackTrace();
			long term = System.currentTimeMillis()-startTime;
			
			throw new TosaException("03", "전문 수신 중 타임아웃 발생 ["+ e+"],Wait Time:"+(term/1000.0)+"초");
        	
//			try {
//				throw new TimeoutException("전문 수신 중 타임아웃 발생 ["+ e+"],Wait Time:"+(term/1000.0)+"초");
//			} catch (TimeoutException ex) {
//				// TODO Auto-generated catch block
//				ex.printStackTrace();
//			}
			
		} catch (IOException e) {
			logger.error("IO exception!", e);
			e.printStackTrace();
			
			rsp = null;
		} finally {
			if (sock != null) {
				try {
					sock.close();
				} catch (IOException e1) {
					logger.error("IO exception!", e1);
				}
			}
		}
		return rsp;
	}
	
	/**
	 * 전문 통신 중 받는 길이가 요청길이 보다 길때 받는 길이를 늘려준다
	 * dklee 2014.03.31
	 * 
	 * @param ip
	 * @param port
	 * @param buffer
	 * @return
	 * @throws TosaException 
	 */
	public static byte[] exchangeDataSocketCancel(String ip, int port, byte[] buffer) throws TosaException {
		return exchangeDataSocketCancel(ip, port, buffer, 0, buffer.length);
	}
	
	public static byte[] exchangeDataSocketCancel(String ip, int port, byte[] buffer,
			int offset, int length) throws TosaException {
		return exchangeDataSocketCancel(ip, port, buffer, offset, length, 10000);
	}
	
	// 소켓 타임아웃 Exception 처리를 해준다. -add dklee 2013.11.07
		public static byte[] exchangeDataSocketCancel(String ip, int port, byte[] buffer,
				int offset, int length, int timeout_ms) throws TosaException {
			if (buffer == null || ip == null || offset < 0 || length <= 0) {
				return null;
			}

			Socket sock = null;
			InputStream is = null;
			OutputStream os = null;
			byte[] rsp = null;
			long startTime = System.currentTimeMillis();

			try {
				sock = new Socket();
				sock.setSoTimeout(timeout_ms);
				sock.connect(new InetSocketAddress(ip, port), timeout_ms);
				is = sock.getInputStream();
				os = sock.getOutputStream();
				
				os.write(buffer, offset, length);
				os.flush();

				rsp = new byte[length+102];
				int nread = is.read(rsp);
				if (nread <= 0) {
					return null;
				}
				// TODO received length 만큼 buffer size 조절하기
				
			// 소켓 타임아웃 Exception 발생	
			}catch(SocketTimeoutException e){
				e.printStackTrace();
				long term = System.currentTimeMillis()-startTime;
				
				throw new TosaException("03", "전문 수신 중 타임아웃 발생 ["+ e+"],Wait Time:"+(term/1000.0)+"초");
	        	
//				try {
//					throw new TimeoutException("전문 수신 중 타임아웃 발생 ["+ e+"],Wait Time:"+(term/1000.0)+"초");
//				} catch (TimeoutException ex) {
//					// TODO Auto-generated catch block
//					ex.printStackTrace();
//				}
				
			} catch (IOException e) {
				logger.error("IO exception!", e);
				e.printStackTrace();
				
				rsp = null;
			} finally {
				if (sock != null) {
					try {
						sock.close();
					} catch (IOException e1) {
						logger.error("IO exception!", e1);
					}
				}
			}
			return rsp;
		}
	
		/**
		 * 기준시간에 설정하는 초만큼 더한다. <br>
		 * 현재 시간이 원하는 초만큼의 시간전이면 TRUE <br>
		 * 현재 시간이 원하는 초만큼의 시간후이면 FALSE <br>
		 * <br>
		 * yscheon 2014.08.25 <br>
		 * <br>
		 * @param strDate		기준시간
		 * @param iSec			증가 초단위
		 * @return
		 * @throws Exception
		 */
		public static boolean compareAddSec(String strDate, int iSec) throws Exception {

			boolean checkSecond = false;

			Calendar cal = Calendar.getInstance();

			// 현재시각
			Date currentDate = cal.getTime();

			// 마지막시간 설정
			if (strDate.length() == 14) {

				cal.set(Integer.parseInt(strDate.substring(0, 4))
						, Integer.parseInt(strDate.substring(4, 6)) - 1
						, Integer.parseInt(strDate.substring(6,8))
						, Integer.parseInt(strDate.substring(8,10))
						, Integer.parseInt(strDate.substring(10,12))
						, Integer.parseInt(strDate.substring(12,14)));

				// + 45초 설정
				cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) +  iSec);
			} else {
				throw new TosaException("99", "직전거래 45초 미만 잠시후 다시 요망");
			}

			// Format 설정 yyyyMMddhhmmss 
			SimpleDateFormat dateForm = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);

			// 45초 후의 시간 취득
			Date beforeDate = cal.getTime();

			System.out.println(" 마지막 거래 시간 ["+strDate+"] 45초후 시간 ["+dateForm.format(beforeDate.getTime()) 
					+"] 현재시간 ["+dateForm.format(currentDate.getTime())+"]"); 

			// 현재시간이 45전의 시간보다 이전의 시간이다. 
			if ( currentDate.before(beforeDate)) {
				checkSecond = true;
			} else {
				checkSecond = false;
			}

			return checkSecond;
		}
		
		
		/**
		 * 현재 연도 취득 - YYYY
		 * @return
		 */
		public static String nowYear() {
			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy");
			return df.format(date);
		}

		/**
		 * 숫자를 입력받은 3자리 단위로 콤마를 넣어준다. <br> 
		 * 입력(int형) : 90100 <br>
		 * 출력(String형) : 90,100 <br>
		 * @param number
		 * @return
		 */
		public static String convertIntComma(int number) {
			String comma = "##,####";
			comma = comma += ",";

			for (int i = 0; i < 3; i++) {
				comma += "#";
			}

			DecimalFormat df = new DecimalFormat(comma);
			String dfVal = df.format(number);

			return dfVal;
		}
		
		/**
		 * 기준시간에 설정하는 일만큼 더한다. <br>
		 * 현재 시간이 원하는 일수만큼의 시간전이면 TRUE <br>
		 * 현재 시간이 원하는 일수만큼의 시간후이면 FALSE <br>
		 * <br>
		 * yscheon 2014.11.05 <br>
		 * <br>
		 * @param strDate		기준시간
		 * @param monthCnt	증가 일수
		 * @return
		 * @throws Exception
		 */
		public static boolean compareAddDay(String strDate, int dayCnt) throws Exception {

			boolean checkSecond = false;

			Calendar cal = Calendar.getInstance();

			// 현재시각
			Date currentDate = cal.getTime();

			// 마지막시간 설정
			if (strDate.length() == 14) {

				cal.set(Integer.parseInt(strDate.substring(0, 4))
						, Integer.parseInt(strDate.substring(4, 6)) - 1
						, Integer.parseInt(strDate.substring(6,8))
						, Integer.parseInt(strDate.substring(8,10))
						, Integer.parseInt(strDate.substring(10,12))
						, Integer.parseInt(strDate.substring(12,14)));

				// + 개월수 설정
				cal.set(Calendar.DAY_OF_MONTH , cal.get(Calendar.DAY_OF_MONTH) +  dayCnt);
			} else {
				throw new TosaException("99", "전일 거래 체크시 에러 -  일자 오류");
			}

			// Format 설정 yyyyMMddhhmmss 
			SimpleDateFormat dateForm = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);

			// 1개월 후의 시간 취득
			Date beforeDate = cal.getTime();

			System.out.println(" 마지막 거래 시간 ["+strDate+"] 설정 "+dayCnt+"일후 시간 ["+dateForm.format(beforeDate.getTime()) 
					+"] 현재시간 ["+dateForm.format(currentDate.getTime())+"]"); 

			// 현재시간이 설정개월 전의 시간보다 이전의 시간이다. 
			if ( currentDate.before(beforeDate) ) {
				checkSecond = true;
			} else {
				checkSecond = false;
			}

			return checkSecond;
		}

		/**
		 * LA 전문 송신시 지역코드를 추가한다.  <br>
		 * KSCC에 맞는 지역코드를 송신한다. <br> 
		 * 변경 필요 <br>
		 * yscheon 2014.11.10 <br>
		 * @param dbArea
		 * @return
		 */
		public static String convertLaArea(String dbArea){
			String areaLaConvert = "";

			/* DB상의 지역코드
			 *  서울 02   ==> 11	서울
				부산 051  ==> 26	부산
				대구 053  ==> 27	대구
				인천 032  ==> 28	인천
				광주 062  ==> 29	광주
				대전 042  ==> 30	대전
				울산 052  ==> 31	울산
				세종 044  ==> 60	세종
				경기 031  ==> 41	경기
				강원 033  ==> 42	강원
				충북 043  ==> 43	충북
				충남 041  ==> 44	충남
				전북 063  ==> 45	전북
				전남 061  ==> 46	전남
				경북 054  ==> 47	경북
				경남 055  ==> 48	경남
				제주 064  ==> 49	제주
			 */

			// 서울 02   ==> 11	서울
			if( "02".equals(dbArea) ){
				areaLaConvert = "11";
				// 부산 051  ==> 26	부산
			} else if( "051".equals(dbArea) ){
				areaLaConvert = "26";
				// 대구 053  ==> 27	대구
			} else if( "053".equals(dbArea) ){
				areaLaConvert = "27";
				// 인천 032  ==> 28	인천
			} else if( "032".equals(dbArea) ){
				areaLaConvert = "28";
				// 광주 062  ==> 29	광주
			} else if( "062".equals(dbArea) ){
				areaLaConvert = "29";
				// 대전 042  ==> 30	대전
			} else if( "042".equals(dbArea) ){
				areaLaConvert = "30";
				// 울산 052  ==> 31	울산
			} else if( "052".equals(dbArea) ){
				areaLaConvert = "31";
				// 세종 044  ==> 60	세종
			} else if( "044".equals(dbArea) ){
				areaLaConvert = "60";
				// 경기 031  ==> 41	경기
			} else if( "031".equals(dbArea) ){
				areaLaConvert = "41";
				// 강원 033  ==> 42	강원
			} else if( "033".equals(dbArea) ){
				areaLaConvert = "42";
				// 충북 043  ==> 43	충북
			} else if( "043".equals(dbArea) ){
				areaLaConvert = "43";
				// 충남 041  ==> 44	충남
			} else if( "041".equals(dbArea) ){
				areaLaConvert = "44";
				// 전북 063  ==> 45	전북
			} else if( "063".equals(dbArea) ){
				areaLaConvert = "45";
				// 전남 061  ==> 46	전남
			} else if( "061".equals(dbArea) ){
				areaLaConvert = "46";
				// 경북 054  ==> 47	경북
			} else if( "054".equals(dbArea) ){
				areaLaConvert = "47";
				// 경남 055  ==> 48	경남
			} else if( "055".equals(dbArea) ){
				areaLaConvert = "48";
				// 제주 064  ==> 49	제주
			} else if( "064".equals(dbArea) ){
				areaLaConvert = "49";
			}
			
			return areaLaConvert;
		}
		
		/**
		 * 기준시간에 설정하는 초만큼 더한다. <br>
		 * <br>
		 * yscheon 2015.01.26 <br>
		 * <br>
		 * @param strDate		기준시간
		 * @param iSec			증가 초단위
		 * @return
		 * @throws Exception
		 */
		public static String currentAddSec(String strDate, int iSec) {

			String addSecondDate = "";

			Calendar cal = Calendar.getInstance();

			// 현재시각
			Date currentDate = cal.getTime();

			// 마지막시간 설정
			if (strDate.length() == 14) {

				cal.set(Integer.parseInt(strDate.substring(0, 4))
						, Integer.parseInt(strDate.substring(4, 6)) - 1
						, Integer.parseInt(strDate.substring(6,8))
						, Integer.parseInt(strDate.substring(8,10))
						, Integer.parseInt(strDate.substring(10,12))
						, Integer.parseInt(strDate.substring(12,14)));

				// + 45초 설정
				cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) +  iSec);
			} else {
				return strDate;
			}

			// Format 설정 yyyyMMddhhmmss 
			SimpleDateFormat dateForm = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);

			// 45초 후의 시간 취득
			Date beforeDate = cal.getTime();

//			System.out.println(" 현재시간 ["+dateForm.format(currentDate.getTime())+"] "+iSec+" 초후 시간 ["+dateForm.format(beforeDate.getTime()) 
//					+"] "); 
			
			addSecondDate = dateForm.format(beforeDate.getTime()) ;
			
			return addSecondDate;
		}
		
		/**
		 * TMONET -> KSCC
		 * 후불 카드사 구분
		 *  
		 * @param card_cmpl_cd	카드사 구분코드
		 * @return
		 * 	1	국민카드	<br>
			2	삼성카드 <br>	
			3	LG카드 <br>	
			4	BC카드 <br>
			5	신한카드 <br>
			6	외환카드 <br>	
			7	한국씨티은행 <br>
			8	하나카드 <br>
			9	현대카드 <br> 
			A	롯데카드 <br>	
			B	수협카드 <br>
			C	농협카드 <br>	
			D	제주은행 <br>
			E   우리BC카드 <br>
			X	아멕스카드 <br>
		 */
		public static String getAfCardCmplCd(String card_cmpl_cd){

			String returnCardCmplCd = "";
			
			/*
			1	국민카드
			2	삼성카드	3	LG카드	
			4	BC카드
			5	신한카드
			6	외환카드	7	한국씨티은행
			8	하나카드
			9	현대카드	
			A	롯데카드	B	수협카드	C	농협카드	D	제주은행
			E   우리BC카드
			X	아멕스카드
			 */
			// 우리BC카드 01
			if( "01".equals(card_cmpl_cd)){
				returnCardCmplCd = "E";
				// 국민카드 06
			} else if( "06".equals(card_cmpl_cd)){
				returnCardCmplCd = "1";
				// 신한카드 05
			} else if( "05".equals(card_cmpl_cd)){
				returnCardCmplCd = "5";
				// 하나SK카드 24
			} else if( "02".equals(card_cmpl_cd)){
				returnCardCmplCd = "8";
				// 외환카드 
			} else if( "03".equals(card_cmpl_cd)){
				returnCardCmplCd = "6";
				// 농협
			} else if( "15".equals(card_cmpl_cd)){
				returnCardCmplCd = "C";
			}
			
			return returnCardCmplCd;
		}
		
		public static byte[] exchangeDataNoHeader(String ip, int port,
				byte[] buffer, int timeout_ms,int sendLen,int readLen) {
			if (buffer == null || ip == null) {
				return null;
			}

			Socket sock = null;
			InputStream is = null;
			OutputStream os = null;
			byte[] data = null;
			byte[] sendBuffer = null;
			byte[] header = null;
			byte[] rsp = null;
			try {
				sock = new Socket();
				sock.setSoTimeout(timeout_ms);
				sock.connect(new InetSocketAddress(ip, port), timeout_ms);
				is = sock.getInputStream();
				os = sock.getOutputStream();
				
				header = new byte[]{buffer[0],buffer[1],buffer[2],buffer[3]};
				sendBuffer = new byte[sendLen];
				System.arraycopy(buffer, header.length, sendBuffer, 0, sendLen);

				//TLogger.xinfo("보내는 데이터 확인:" + new String(sendBuffer));
						
				os.write(sendBuffer, 0, sendBuffer.length);
				os.flush();
				rsp = new byte[readLen + 4];
				data = new byte[readLen];
				int nread = is.read(data);

				System.arraycopy(header, 0, rsp, 0, header.length);
				System.arraycopy(data, 0, rsp, header.length, data.length);
				if (nread <= 0) {
					return null;
				}
				// TODO received length 만큼 buffer size 조절하기

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("exception!", e);
				rsp = null;
			} finally {
				if (sock != null) {
					try {
						sock.close();
					} catch (Exception e1) {
						logger.error("exception!", e1);
					}
				}
			}
			return rsp;
		}
		
		public static String getPhoneChargePlusFeeString(String amt , int feePer){
			
			if( amt == null || "".equals(amt)){
				amt = "0";
			}
			
			int convertAmt = Integer.parseInt(amt);
			int fee = convertAmt * feePer / 100 ;
			
			int totalAmt = convertAmt + fee;
			
			return String.valueOf(totalAmt);
		}
		
		// / TEST
		public static void main(String[] args) {

			try{
				System.out.println( " boolean " + compareAddDay("20141031010101", 7) ); 
			} catch(Exception e){
				
			}

		}
}