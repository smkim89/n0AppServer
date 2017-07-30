package com.swag.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.ibatis.type.Alias;

@Alias("dataBox")
public class DataBox extends HashMap<Object, Object> {

	private static final long serialVersionUID = 1568324898795L;

	public DataBox() {

	}

	public DataBox(Map<String, Object> map) {
		if (map != null) {
			Iterator<String> iter = map.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				this.set(key, map.get(key));
			}
		}
	}

	public String getSafeString(String key) {
		return getString(key);
	}

	public String getSafeString(String key, int byteLength) {
		return getString(key, byteLength);
	}

	public String getString(String key) {
		return getString(key, "");
	}

	public String getString(String key, int byteLength) {
		return getString(key, byteLength, "");
	}

	public String getString(String key, int byteLength, String nullValue) {
		return byteCut(getString(key, nullValue), byteLength);
	}

	public int getInt(String key) {
		Integer returnValue = getIntObject(key);

		if (returnValue == null)
			return 0;
		else
			return returnValue.intValue();
	}

	public long getLong(String key) {
		Long returnValue = getLongObject(key);

		if (returnValue == null)
			return 0;
		else
			return returnValue.longValue();
	}

	public float getFloat(String key) {
		Float returnValue = getFloatObject(key);

		if (returnValue == null)
			return 0;
		else
			return returnValue.floatValue();
	}

	public double getDouble(String key) {
		Double returnValue = getDoubleObject(key);

		if (returnValue == null)
			return 0;
		else
			return returnValue.doubleValue();
	}

	public String getString(String key, String nullValue) {
		Object value = (Object) this.get(key);

		try {
			return value.toString();
		} catch (Exception e) {
			return nullValue;
		}
	}

	public Integer getIntObject(String key) {
		Object value = (Object) this.get(key);

		try {
			return Integer.valueOf(value.toString(), 10);
		} catch (Exception e) {
			return null;
		}
	}

	public Long getLongObject(String key) {
		Object value = (Object) this.get(key);

		try {
			return Long.valueOf(value.toString(), 10);
		} catch (Exception e) {
			return null;
		}
	}

	public Float getFloatObject(String key) {
		Object value = (Object) this.get(key);

		try {
			return Float.valueOf(value.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public Double getDoubleObject(String key) {
		Object value = (Object) this.get(key);

		try {
			return Double.valueOf(value.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public static String byteCut(String str, int bytelen) {
		int i, lenCount = 0;
		int strLen = str.length();

		for (i = 0; i < strLen && lenCount <= bytelen; i++) {
			try {
				lenCount += String.valueOf(str.charAt(i)).getBytes("UTF-8").length;
			} catch (UnsupportedEncodingException e) {
				return str.substring(0, bytelen);
			}
		}

		if (lenCount > bytelen)
			return str.substring(0, i - 1);
		else
			return str;
	}

	public byte[] getBytes(String name) {
		byte[] result = null;
		Object obj = super.get(name);
		if (obj != null) {
			if (obj instanceof byte[])
				result = (byte[]) obj;
		} // if
		return result;
	}

	public <E> void set(String name, E obj) {
		super.put(name, obj);
	} // set

	public void pushMap(Map<String, Object> map) {
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			set(key.toLowerCase(), map.get(key));
		} // while
	} // pushMap()

	public String[] getNames() throws Exception {
		Iterator<Object> iter = this.keySet().iterator();
		String[] names = new String[this.keySet().size()];
		for (int i = 0; iter.hasNext(); i++) {
			names[i] = (String) iter.next();
		} // for
		return names;
	} // getNames()

	public String queryString() {
		StringBuffer buffer = new StringBuffer();
		try {
			String[] names = this.getNames();
			int index = 0;
			for (String name : names) {
				if (index != 0)
					buffer.append("&");
				String value = this.getString(name);
				buffer.append(URLEncoder.encode(name, "EUC-KR"));
				buffer.append("=");
				buffer.append(URLEncoder.encode(value, "EUC-KR"));
				index++;
			} // for
		} catch (Exception e) {
		} // if

		return buffer.toString();
	}

}
