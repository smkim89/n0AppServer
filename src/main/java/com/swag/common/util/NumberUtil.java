package com.swag.common.util;

public class NumberUtil
{

 public NumberUtil()
 {
 }

 public static short byteToShort(byte data[], int offset)
 {
     int n = 0;
     for(int i = 0; i < 2; i++)
         n = (n << 8) + (data[offset + i] & 0xff);

     return (short)n;
 }

 public static byte[] shortToByte(short n)
 {
     byte b[] = new byte[2];
     for(int i = 0; i < 2; i++)
         b[i] = (byte)(n >>> 8 * (1 - i));

     return b;
 }

 public static int parseInt(Object value)
     throws NumberFormatException
 {
     int ret = 0;
     if(value instanceof String)
         ret = parseInt((String)value);
     else
     if(value instanceof Byte)
         ret = ((Byte)value).byteValue();
     else
     if(value instanceof Integer)
         ret = ((Integer)value).intValue();
     else
     if(value instanceof Short)
         ret = ((Short)value).shortValue();
     else
     if(value instanceof byte[])
         ret = parseInt((byte[])value);
     else
         throw new NumberFormatException("해당하는 Type이 없습니다.");
     return ret;
 }

 public static int parseInt(String value)
 {
     int ret = 0;
     if(value == null)
         return 0;
     try
     {
         ret = Integer.parseInt(value.trim());
     }
     catch(Exception exception) { }
     return ret;
 }

 public static int parseInt(byte value[])
 {
     int SIZE = 4;
     int n = 0;
     for(int i = 0; i < SIZE; i++)
         n = (n << 8) + (value[i] & 0xff);

     return n;
 }

 public static int parseInt(byte value[], int order)
 {
     int SIZE = 4;
     if(order == 0)
     {
         byte conv[] = new byte[SIZE];
         ByteOrder.swap(value, conv);
         return parseInt(conv);
     } else
     {
         return parseInt(value);
     }
 }

 public static short parseShort(byte value[])
 {
     int SIZE = 2;
     int n = 0;
     for(int i = 0; i < SIZE; i++)
         n = (n << 8) + (value[i] & 0xff);

     return (short)n;
 }

 public static short parseShort(byte value[], int order)
 {
     int SIZE = 2;
     if(order == 0)
     {
         byte conv[] = new byte[SIZE];
         ByteOrder.swap(value, conv);
         return parseShort(conv);
     } else
     {
         return parseShort(value);
     }
 }
}

