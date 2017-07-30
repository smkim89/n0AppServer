package com.swag.common.util;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
public class BinaryUtil {
	public BinaryUtil()
    {
    }

    public static byte[] parseBinary(Object value)
    {
        byte ret[] = (byte[])null;
        if(value instanceof String)
            ret = parseBinary((String)value);
        else
        if(value instanceof Byte)
        {
            ret = new byte[1];
            ret[0] = ((Byte)value).byteValue();
        } else
        if(value instanceof Integer)
            ret = parseBinary(((Integer)value).intValue());
        else
        if(value instanceof Short)
            ret = parseBinary(((Short)value).shortValue());
        else
        if(value instanceof byte[])
        {
            int len = ((byte[])value).length;
            ret = new byte[len];
            System.arraycopy(value, 0, ret, 0, len);
        }
        return ret;
    }

    public static byte[] parseBinary(Object value, int order)
    {
        byte ret[] = (byte[])null;
        if(value instanceof String)
            ret = parseBinary((String)value);
        else
        if(value instanceof Byte)
        {
            ret = new byte[1];
            ret[0] = ((Byte)value).byteValue();
        } else
        if(value instanceof Integer)
            ret = parseBinary(((Integer)value).intValue(), order);
        else
        if(value instanceof Short)
            ret = parseBinary(((Short)value).shortValue(), order);
        else
        if(value instanceof byte[])
        {
            int len = ((byte[])value).length;
            ret = new byte[len];
            System.arraycopy(value, 0, ret, 0, len);
        }
        return ret;
    }

    public static byte[] parseBinary(int value)
    {
        int SIZE = 4;
        byte b[] = new byte[SIZE];
        for(int i = 0; i < SIZE; i++)
            b[i] = (byte)(value >>> 8 * (SIZE - 1 - i));

        return b;
    }

    public static byte[] parseBinary(int value, int order)
    {
        byte res[] = parseBinary(value);
        if(order == 0)
            ByteOrder.swap(res);
        return res;
    }

    public static byte[] parseBinary(short value)
    {
        int SIZE = 2;
        byte b[] = new byte[SIZE];
        for(int i = 0; i < SIZE; i++)
            b[i] = (byte)(value >>> 8 * (SIZE - 1 - i));

        return b;
    }

    public static byte[] parseBinary(short value, int order)
    {
        byte res[] = parseBinary(value);
        if(order == 0)
            ByteOrder.swap(res);
        return res;
    }

    public static byte[] parseBinary(String value)
    {
        return value.getBytes();
    }

    public static byte[] parseBinary(String value, String charset)
    {
        byte ret[] = (byte[])null;
        try
        {
            ret = value.getBytes(charset);
        }
        catch(UnsupportedEncodingException unsupportedencodingexception) { }
        return ret;
    }

    public static Object toObject(byte value[], Class clazz)
    {
        Object ret = null;
        if(value == null)
            return null;
        if(clazz == java.lang.String.class)
            ret = (new String(value)).trim();
        else
        if(clazz == java.lang.Integer.class)
            ret = new Integer(NumberUtil.parseInt(value));
        else
        if(clazz == java.lang.Byte.class)
            ret = new Byte(value[0]);
        else
        if(clazz == java.lang.Short.class)
            ret = new Short(NumberUtil.parseShort(value));
        else
        if(clazz == byte[].class)
        {
            int len = value.length;
            ret = new byte[len];
            System.arraycopy(value, 0, ret, 0, len);
        }
        return ret;
    }

    public static Object toObject(byte value[], Class clazz, int order)
    {
        Object ret = null;
        if(value == null)
            return null;
        if(clazz == java.lang.String.class)
            ret = (new String(value)).trim();
        else
        if(clazz == java.lang.Integer.class)
            ret = new Integer(NumberUtil.parseInt(value, order));
        else
        if(clazz == java.lang.Byte.class)
            ret = new Byte(value[0]);
        else
        if(clazz == java.lang.Short.class)
            ret = new Short(NumberUtil.parseShort(value, order));
        else
        if(clazz == byte[].class)
        {
            int len = value.length;
            ret = new byte[len];
            System.arraycopy(value, 0, ret, 0, len);
        }
        return ret;
    }

    public static String toHexString(byte value)
    {
        byte in[] = new byte[1];
        in[0] = value;
        return toHexString(in);
    }

    public static String toHexString(byte value[])
    {
        if(value == null)
            return null;
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < value.length; i++)
        {
            int j = value[i] >> 4 & 15;
            if(j <= 9)
                sb.append(j);
            else
                sb.append((char)((j + 65) - 10));
            j = value[i] & 15;
            if(j <= 9)
                sb.append(j);
            else
                sb.append((char)((j + 65) - 10));
        }

        return sb.toString();
    }
    
    public static byte[] getByte(byte[] b, int offset, int len){
    	byte[] rb = new byte[len];
    	System.arraycopy(b, offset, rb, 0, len);
    	
    	return rb;
    }

    public static byte[] parseHexString(String value)
    {
        if(value == null)
            return null;
        value = value.toUpperCase();
        int len = value.length();
        byte out[] = new byte[len / 2];
        for(int i = 0; i < len / 2; i++)
        {
            int j = i * 2;
            int high = 0;
            int low = 0;
            if(value.charAt(j) >= '0' && value.charAt(j) <= '9')
                high = value.charAt(j) - 48;
            else
            if(value.charAt(j) >= 'A' && value.charAt(j) <= 'F')
                high = 10 + (value.charAt(j) - 65);
            if(value.charAt(j) >= '0' && value.charAt(j + 1) <= '9')
                low = value.charAt(j + 1) - 48;
            else
            if(value.charAt(j + 1) >= 'A' && value.charAt(j + 1) <= 'F')
                low = 10 + (value.charAt(j + 1) - 65);
            out[i] = (byte)(high * 16 + low);
        }

        return out;
    }

    

	
//	static public String BytesToString(byte[] bValue) {
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < bValue.length; i++) {
//			sb.append(ByteToString(bValue[i]));	
//		}
//		return sb.toString();
//	}
//	
//	static public String ByteToString(byte bValue) {
//		return Integer.toHexString(0xFF & bValue);
//	}
	
    public static String toBinaryString(byte value)
    {
//        int in = value & 255;
//        String str = Integer.toBinaryString(in);
//        str = StringUtil.format(str, 8, '0', false);
//        return str;
    	return Integer.toHexString(0xFF & value);
    }

    public static String toBinaryString(byte value[])
    {
        if(value == null)
            return null;
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < value.length; i++)
        {
            String str = toBinaryString(value[i]);
            
            if (str.length() == 1) {
            	sb.append("0");
			}
            
            sb.append(str);
        }

        return sb.toString();
    }

    public static int parseBinaryString(String value)
    {
        int result = 0;
        try
        {
            result = Integer.parseInt(value, 2);
        }
        catch(Exception exception) { }
        return result;
    }

    public static int parseBinaryString(String value, int start, int end)
    {
        int result = 0;
        try
        {
            String str = value.substring(start, end);
            result = parseBinaryString(str);
        }
        catch(Exception exception) { }
        return result;
    }
    
    /**
	 * 16진수값으로된 String을 int형으로 출력합니다.
	 * 
	 * @param str	String Hex 문자열.
	 * @return	10진수 형식으로 포맷팅된 int.
	 */
	public static int hexStringToInt(String str){
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
	
	public static String hexStringToBinaryString(String str){
		String binaryString = "";
		String temp = "";
		int off = 0;
		for(int i=0;i<str.length();i++){
			int value = hexStringToInt(str.substring(off, off+1));
			binaryString = Integer.toBinaryString(value);
			while(binaryString.length() % 4 != 0) {
				binaryString = "0" + binaryString;
			}
			
			temp = temp+binaryString;
			off++;
		}
		return temp;
	}

	/**
	 * MAP Version에 따라 DATETIME 필드 포맷이 변경됨(환승정보내역파일(교통칩)만 사용)
	 * 
	 * @param str	bit형 문자열.
	 * @return	yyyymmddhhnnss로 포맷팅한 문자열.
	 */
	public static String binaryToDateTime(String str){
		int len = 0;
		
		String temp = hexStringToBinaryString(str);
		
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
	
	public static int parseInt(byte value[])
    {
        int SIZE = 4;
        int n = 0;
        for(int i = 0; i < SIZE; i++)
            n = (n << 8) + (value[i] & 255);

        return n;
    }
	
	/**
	 * byte to int
	 * @param value 값
	 * @param s 크기
	 * @return
	 */
	public static int parseInt(byte value[], int s)
    {
        int SIZE = s;
        int n = 0;
        for(int i = 0; i < SIZE; i++)
            n = (n << 8) + (value[i] & 255);

        return n;
    }
	
	public static byte[] doubleToByte(double doubleVar) {
		long temp;
		byte[] littleDouble = new byte[8];
		temp = Double.doubleToLongBits(doubleVar);
		littleDouble = longToByte(temp);
		return littleDouble;
	}
	
	public static byte[] longToByte(long longVar) {
		ByteBuffer buf = ByteBuffer.allocate(8);
		buf.putLong(longVar);
		return buf.array();
	}
	
	public static long byteToLong(byte[] b) {
		ByteBuffer buf = ByteBuffer.wrap(b);
		return buf.getLong();
	}
	
}
