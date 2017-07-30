package com.swag.common.util;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Box extends HashMap<String, Object> {

	/**
	 * <pre>
	 * 생성자
	 * 기본적인 Map을 생성한다.
	 * </pre>
	 */
	public Box() {
	}

	/**
	 * <pre>
	 * 생성자
	 * Map을 인자로 받아 초기 데이타로 사용한다.
	 * clone()을 사용하여 복사하는것이 아니기 때문에, 기존의 Map에 어떤 영향을 줄지 확신이 불가능하다.
	 * 만약 기존의 Map에 영향을 주면 안된다면, Map의 값을 set()메소드를 통해 하나씩 복사하여 넣어줄 것을 추천한다.
	 * </pre>
	 * 
	 * @param map
	 */
	public Box(Map<String, Object> map) {
		if (map != null) {
			Iterator iter = map.keySet().iterator();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				this.set(key, map.get(key));
			} // while
		}
	} // CanBox()

	/**
	 * <pre>
	 * 객체를 삽입
	 * </pre>
	 * 
	 * @param String
	 *            :name
	 * @param E
	 *            :obj
	 */
	public <E> void set(String name, E obj) {
		super.put(name, obj);
	} // set

	/**
	 * <pre>
	 * 2009.8.21 박상헌
	 * 맵의 내용을 현재의 CanBox에 주입한다.
	 * </pre>
	 * 
	 * @param map
	 */
	public void pushMap(Map map) {
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			set(key.toLowerCase(), map.get(key));
		} // while
	} // pushMap()

	/**
	 * <pre>
	 * 객체를 반환
	 * 할당받을 타입에 따라 자동으로 형변환을 수행
	 * 타입 불일치에 대한 오류는 처리하지 않는다. (책임지지 않음)
	 * </pre>
	 * 
	 * @param <E>
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> E get(String name) {
		Object obj = super.get(name);
		if (obj == null) {
			obj = "";
		}
		return (E) obj; // super.get(name);
	} // get()

//	public Object get(String name) {
//		Object obj = super.get(name);
//		if (obj == null) {
//			obj = "";
//		}
//		return obj; // super.get(name);
//	} // get()	

	
	/**
	 * <pre>
	 * 2009.8.3 박상헌
	 * 값을 Long으로 반환. 형식이 다르다면 0을 반환.
	 * </pre>
	 * 
	 * @param name
	 * @return
	 */
	public Long getLong(String name) {
		long result = 0L;
		Object obj = super.get(name);
		try {
			if (obj != null) {
				if (obj instanceof String)
					result = Long.parseLong((String) obj);
				else if (obj instanceof Number)
					result = ((Number) obj).longValue();
			} // if
		} catch (Exception e) {
			result = 0L;
		} // try
		return result;
	} // getLong()

	/**
	 * <pre>
	 * 2009.8.3 박상헌
	 * 값을 Integer로 반환. 형식이 다르면 0을 반환.
	 * </pre>
	 * 
	 * @param name
	 * @return
	 */
	public Integer getInt(String name) {
		Integer result = 0;
		Object obj = super.get(name);
		try {
			if (obj != null) {
				if (obj instanceof String)
					result = Integer.parseInt((String) obj);
				else if (obj instanceof Number)
					result = ((Number) obj).intValue();
			}
		} catch (Exception e) {
			result = 0;
		} // try
		return result;
	} // getInt()

	/**
	 * <pre>
	 * 2009.8.3 박상헌
	 * 값을 Short로 반환. 형식이 다르면 0을 반환.
	 * </pre>
	 * 
	 * @param name
	 * @return
	 */
	public Short getShort(String name) {
		Short result = 0;
		Object obj = super.get(name);
		try {
			if (obj != null) {
				if (obj instanceof String)
					result = Short.parseShort((String) obj);
				else if (obj instanceof Number)
					result = ((Number) obj).shortValue();
			} // if
		} catch (Exception e) {
			result = 0;
		} // try
		return result;
	} // getShort()

	/**
	 * <pre>
	 * 2009.8.3 박상헌
	 * 값을 Double로 반환. 형식이 다르면 0.0D를 반환
	 * </pre>
	 * 
	 * @param name
	 * @return
	 */
	public Double getDouble(String name) {
		Double result = 0.0D;
		Object obj = super.get(name);
		try {
			if (obj != null) {
				if (obj instanceof String)
					result = Double.parseDouble((String) obj);
				else if (obj instanceof Number)
					result = ((Number) obj).doubleValue();
			} // if
		} catch (Exception e) {
			result = 0.0D;
		} // try
		return result;
	} // getDouble()

	/**
	 * <pre>
	 * 2009.8.3 박상헌
	 * 값을 Float로 반환. 형식이 다르면 0.0f를 반환
	 * </pre>
	 * 
	 * @param name
	 * @return
	 */
	public Float getFloat(String name) {
		Float result = 0.0f;
		Object obj = super.get(name);
		try {
			if (obj != null) {
				if (obj instanceof String)
					result = Float.parseFloat((String) obj);
				else if (obj instanceof Number)
					result = ((Number) obj).floatValue();
			} // if
		} catch (Exception e) {
			result = 0.0f;
		} // try
		return result;
	} // getFloat()

	/**
	 * <pre>
	 * 2009.8.3 박상헌
	 * 값을 Boolean으로 반환. 형식이 다르면 false를 반환
	 * </pre>
	 * 
	 * @param name
	 * @return
	 */
	public Boolean getBoolean(String name) {
		Boolean result = false;
		Object obj = super.get(name);
		try {
			if (obj != null) {
				if (obj instanceof String)
					result = Boolean.parseBoolean((String) obj);
				else if (obj instanceof Boolean)
					result = (Boolean) obj;
			} // if
		} catch (Exception e) {
			result = false;
		} // try
		return result;
	} // getBoolean()

	/**
	 * <pre>
	 * 2009.8.3 박상헌
	 * 값을 Byte[]으로 반환. 형식이 다르면 null를 반환
	 * </pre>
	 * 
	 * @param name
	 * @return
	 */
	public byte[] getBytes(String name) {
		byte[] result = null;
		Object obj = super.get(name);
		if (obj != null) {
			if (obj instanceof byte[])
				result = (byte[]) obj;
		} // if
		return result;
	} // getBoolean()

	/**
	 * <pre>
	 * 2009.8.3 박상헌
	 * 값을 String으로 반환. 형식이 다르면 빈 문자열을 반환
	 * </pre>
	 * 
	 * @param name
	 * @return
	 */
	public String getString(String name) {
		String result = "";
		Object obj = super.get(name);
		try {
			if (obj != null) {
				if (obj instanceof String) {
					result = (String) obj;
				} else if (obj instanceof Double) {
					result = String.valueOf((Double) obj);
				} else if (obj instanceof Float) {
					result = String.valueOf((Float) obj);
				} else if (obj instanceof Long) {
					result = String.valueOf((Long) obj);
				} else if (obj instanceof Integer) {
					result = String.valueOf((Integer) obj);
				} else if (obj instanceof Short) {
					result = String.valueOf((Short) obj);
				} else if (obj instanceof Character) {
					result = String.valueOf((Character) obj);
				} else if (obj instanceof Boolean) {
					result = String.valueOf((Boolean) obj);
				} else if (obj instanceof BigDecimal) {
					result = ((BigDecimal) obj).toString();
				} else {
					result = obj.toString();
				} // if
			} // if
		} catch (Exception e) {
			result = "";
		} // try
		return result;
	} // getString()

	/**
	 * <pre>
	 * CanBox의 객체명들을 배열로 반환한다.
	 * </pre>
	 * 
	 * @return
	 */
	public String[] getNames() throws Exception {
		Iterator<String> iter = this.keySet().iterator();
		String[] names = new String[this.keySet().size()];
		for (int i = 0; iter.hasNext(); i++) {
			names[i] = iter.next();
		} // for
		return names;
	} // getNames()

	public Box clone() {
		Box box = new Box();
		box.putAll(this);
		return box;
	} // clone()
	
	/**
	 * <pre>
	 * 2009.7.8 : 박상헌
	 * CanBox에 들어있는 객체 리스트를 반환한다
	 * </pre>
	 */
	public String toString() {
		StringBuffer str = new StringBuffer();
		Iterator<String> iter = super.keySet().iterator();

		while (iter.hasNext()) {
			try {
				String name = iter.next();
				Object value = super.get(name);
				if (value == null) {
					str.append("[()" + name + " : " + super.get(name) + "]\n");
				} else {
					str.append("[(" + super.get(name).getClass().getName()
							+ ")" + name + " : " + super.get(name) + "]\n");
				} // if
			} catch (Exception e) {
				e.printStackTrace();
			} //
		} // while
		return str.toString();
	} // toString()

	/**
	 * <pre>
	 * 2009.7.14 박상헌
	 * CanBox의 내용을 JSON 형식으로 변환하여 반환한다.
	 * </pre>
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getJSON(int depth) throws Exception {
		StringBuffer body = new StringBuffer();
		String[] keys;
		try {
			appendTab("{\n", body, depth);

			keys = this.getNames();
			for (int i = 0; i < keys.length; i++) {
				Object obj = this.get(keys[i]);
				if (obj instanceof Box) {
					String key = keys[i];
					appendTab("\"" + key.trim() + "\" : [\n", body, depth);
					appendTab(((Box) obj).getJSON(depth + 1), body, depth);
					appendTab("]", body, depth);
				} else {
					String key = keys[i];
					Object value = this.get(key);
					appendTab("\"" + key.trim() + "\" : \"" + value.toString()
							+ "\"", body, depth);
				} // if
				if (i < (keys.length - 1))
					body.append(",");
				body.append("\n");
			} // for
			appendTab("}", body, depth);
		} catch (Exception e) {
			throw e;
		} // try

		return body.toString();
	} // getJSON()

	/**
	 * <pre>
	 * 2009.7.14 박상헌
	 * CanBox의 내용을 JSON 형식으로 변환하여 반환한다.
	 * </pre>
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getJSON() throws Exception {
		return getJSON(0);
	} // getJSON()

	/**
	 * <pre>
	 * 2009.7.14 박상헌
	 * StringBuffer에 문자열을 붙일때 depth만큼 tab을 앞에 붙인다.
	 * </pre>
	 * 
	 * @param str
	 * @param buf
	 * @param depth
	 */
	protected void appendTab(String str, StringBuffer buf, int depth) {
		for (int i = 0; i < depth; i++)
			buf.append("\t");
		buf.append(str);
	} // appendTab

	/**
	 * <pre>
	 * 2009.8.13 박상헌
	 * CanBox내의 데이타를 GET방식 전송을 위한 queryString형태로 변환하여 반환한다.
	 * </pre>
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryString() throws Exception {
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
			throw e;
		} // if

		return buffer.toString();
	} // queryString()
	
} // class Box