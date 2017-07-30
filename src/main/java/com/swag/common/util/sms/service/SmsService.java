package com.swag.common.util.sms.service;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URLEncoder;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.swag.common.util.DataBox;
import com.swag.common.util.sms.mapper.SmsMapper;



@Service
public class SmsService {

	static Logger logger = LoggerFactory.getLogger(SmsService.class);
	@Inject
	private SmsMapper sendSmsMapper;
	
	@Value("#{config[MCP_SEND_SMS_HOST]}")
	private String MCP_SEND_SMS_HOST;
	
	@Value("#{config[MCP_SEND_SMS_PORT]}")
	private int MCP_SEND_SMS_PORT;
	
	@Value("#{config[MCP_SEND_SMS_URL]}")
	private String MCP_SEND_SMS_URL;
	
	@Value("#{config[MCP_SEND_SMS_TIMEOUT]}")
	private int MCP_SEND_SMS_TIMEOUT;
	
	//무료(보내는자, 받는자, 메시지, 통신사(S:SKT, K:KT) LGT는 지원안함.
	public boolean sendNoCostSms(String send_number, String receive_number, String msg, String mtel_co) {
		boolean result = false;
		DataBox param = new DataBox();
          
		String cidList = "010,011,019,018,017,016";
		String cid = receive_number.substring(0,3);
		if (cidList.indexOf(cid)>-1 && (mtel_co.equals("K") || mtel_co.equals("S"))) {
			//정상처리
			String number = receive_number.substring(3,receive_number.length());
			param.put("mobile_no_cid", cid);
			param.put("mobile_no_call_no", number);
			param.put("msg", msg);
			param.put("callback_no", send_number);
			param.put("mtel_co", mtel_co);
			int resultCnt = sendSmsMapper.insertNoCostSms(param);
			if (resultCnt >0){
				result = true;
			}
		}
		
		
		return result;
		
	}

	//유료
	public boolean sendCostSms(String send_number, String receive_number, String msg) {
		boolean result = false;
		DataBox param = new DataBox();
		
		param.put("mobile_no", receive_number);
		param.put("callback_no", send_number);
		param.put("msg", msg);
		
		int resultCnt = sendSmsMapper.insertSms(param);
		if(resultCnt>0){
			result = true;
		}
		return result;
		
	}
	
	public boolean sendSmsIntJob(String send_number, String receive_number, String smsMsg, String jobId) {
		//
		// jobId 가 1 이면 일반 메시지, 2 이면 barcode 메시지임
		// 1: 114, 2:16440088, 3: 발신번호
		// rtel_no : 발신번호

		try {
			// rtel_no 
			String smsParam = "job_id=" + jobId + "&mtel_no=" + receive_number + "&msg="
					+ URLEncoder.encode(smsMsg, "EUC-KR") + "&rtel_no=" + send_number;
			
			logger.info("MCP_SMS info : " + MCP_SEND_SMS_HOST + ":" + MCP_SEND_SMS_PORT + MCP_SEND_SMS_URL );
			logger.info("MCP_SMS info smsParam : " +smsParam);
			Socket sock = new Socket();
			InetSocketAddress endpoint = new InetSocketAddress(MCP_SEND_SMS_HOST, MCP_SEND_SMS_PORT);
			sock.connect(endpoint, MCP_SEND_SMS_TIMEOUT);

			OutputStream os = sock.getOutputStream();
			InputStream is = sock.getInputStream();

			sock.setSoTimeout(MCP_SEND_SMS_TIMEOUT);

			os.write(("GET " + MCP_SEND_SMS_URL + "?" + smsParam + " HTTP/1.0\n\n")
					.getBytes());
			os.flush();

			byte[] buffer = new byte[400];
			int nread = is.read(buffer);

			logger.info("MCP_SMS - result bytes:" + nread);
			logger.debug("MCP_SMS - result:" + new String(buffer));
			
			return true;

		} catch (Exception e) {
			logger.error("SMS 발송실패 : " + e.getMessage(), e);
		}

		return false;
	}

	
}