package com.swag.common.util;
public class ByteOrder
{

    public ByteOrder()
    {
    }

    public static void swap(byte abyte0[])
    {
        int i = abyte0.length;
        for(int j = 0; j < i / 2; j++)
        {
            byte byte0 = abyte0[j];
            abyte0[j] = abyte0[i - j - 1];
            abyte0[i - j - 1] = byte0;
        }

    }

    public static void swap(byte abyte0[], byte abyte1[])
    {
        int i = abyte0.length;
        for(int j = 0; j < i; j++)
            abyte1[j] = abyte0[i - j - 1];

    }

    public static final int LITTLE_ENDIAN = 0;
    public static final int BIG_ENDIAN = 1;
}