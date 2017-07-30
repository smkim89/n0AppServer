package com.swag.common.util.socket;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

/**
 * Created by user on 2015-07-30.
 */
public class SpecMaker {
	ByteOutputStream stream;

	public SpecMaker() {
		super();
	}

	public SpecMaker(int spec_length) {
		super();
		stream	= new ByteOutputStream(spec_length);
	}

	public void set(String data) {
		stream.write(data.getBytes());
	}

	public void set(int data) {
		short n = (short) data;
		byte[] bytes = new byte[2];
		for (int i=0; i<2; ++i) {
			bytes[1-i] = (byte) (n & 0xff);
			n >>= 8;
		}
		stream.write(bytes);
	}

	public byte[] toByte() {
		return stream.toString().getBytes();
	}
}
