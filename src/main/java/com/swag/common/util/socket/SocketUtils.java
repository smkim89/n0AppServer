package com.swag.common.util.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 *
 * Socket 통신을 하기위한 클래스입니다.
 * @author gupark
 * @updated 2015-07-08
 */
public class SocketUtils {

	private static final Logger logger = LoggerFactory.getLogger(SocketUtils.class);
	private static String DEFAULT_ENCODING = "EUC-KR";
	private static int DEFAULT_TIMEOUT = 10000;

	public static String send(String ip, int port, byte[] message) throws Exception {
		return SocketUtils.send(ip, port, message, DEFAULT_ENCODING);
	}

	public static String send(String ip, int port, byte[] message, String encoding) throws Exception {
		return SocketUtils.send(ip, port, message, encoding, DEFAULT_TIMEOUT);
	}

	public static String send(String ip, int port, byte[] message, String encoding, int timeout_ms) throws Exception {

		if (message == null || ip == null || port < 1000)
			return null;

		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		String result = null;

		try {
			socket = new Socket();
			socket.setSoTimeout(timeout_ms);
			socket.connect(new InetSocketAddress(ip, port), timeout_ms);

			os = socket.getOutputStream();
			is = socket.getInputStream();

			os.write(message);
			os.flush();

			// byte 배열의 크기를 명시하지 않기위해 BufferReader 를 사용합니다.
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, encoding));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append("\r");
			}
			rd.close();

			result = new String(response);

		} catch (IOException e) {
			logger.error("Socket IO Exception", e);
			throw e;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ex) {
					logger.error("Socket InputStream IO Exception", ex);
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException ex) {
					logger.error("Socket OutputStream IO Exception", ex);
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException ex) {
					logger.error("Socket IO Exception", ex);
				}
			}
		}
		return result;
	}
}
