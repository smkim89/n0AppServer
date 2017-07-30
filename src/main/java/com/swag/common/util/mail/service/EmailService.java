package com.swag.common.util.mail.service;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.swag.common.util.AES;
import com.swag.common.util.mail.domain.Email;

@Service
public class EmailService {

	static Logger logger = LoggerFactory.getLogger(EmailService.class);

	@Value("#{config['crypto']}")
	private String CryptoStr;
	
	@Value("#{config['SMTP_MAIL_SENDER']}")
	private String SMTP_MAIL_SENDER;

	@Value("#{config['SMTP_SERVER_IP']}")
	private String SMTP_SERVER_IP;

	@Value("#{config['SMTP_SERVER_PORT']}")
	private String SMTP_SERVER_PORT;

	@Value("#{config['SMTP_ACCOUNT_ID']}")
	private String SMTP_ACCOUNT_ID;

	@Value("#{config['SMTP_ACCOUNT_PASS']}")
	private String SMTP_ACCOUNT_PASS;

	public void readyAndSendMail(Email email) throws Exception {
		int num = email.getNumOfmail();
	
		for (int i = 0; i < num; i++) {
			String mailAddr = email.getMailAddrList().get(i);
			String name = email.getNameList().get(i);
			sendMail(email, mailAddr, name);
		}
	}

	private void sendMail(Email email, String mailAddr, String name) {

		Authenticator auth = new PopupAuthenticator();

		Properties props = new Properties();
		// smtp 주소 설정.
		// 로컬 Gmail
//		 props.put("mail.smtp.starttls.enable", "true");
//		 props.put("mail.smtp.host", "smtp.gmail.com");
//		 props.put("mail.smtp.port", "587");
//		 props.put("mail.smtp.auth", "true");

		//개발 및 운영
		props.put("mail.smtp.host", AES.aesDecodeStr(SMTP_SERVER_IP, CryptoStr));
		props.put("mail.smtp.port", AES.aesDecodeStr(SMTP_SERVER_PORT, CryptoStr));
		props.put("mail.smtp.auth", "true");


		// 메일 발송자의 주소.
		String sender = SMTP_MAIL_SENDER;

		// 세션 취득.
		Session sess = Session.getInstance(props, auth);

		MimeMessage msg = new MimeMessage(sess);

		try {
			msg.setFrom(new InternetAddress(sender));

			// 수신자
			InternetAddress[] address = { new InternetAddress(mailAddr) };
			logger.info("mailAddr : {}", mailAddr);

			msg.setRecipients(Message.RecipientType.TO, address);
			// 제목 지정 
			String addStr = "";

			if(!name.isEmpty()){
			 addStr = "["+name+"]님 ";
			}

			msg.setSubject(addStr + email.getArticleTitle());
			logger.info("subject : {}", email.getArticleTitle());
			
			// 발송시간 지정.
			msg.setSentDate(new java.util.Date());

			String strMailBodyContent = email.getArticleText();
			// 콘텐트 타입 지정.
			msg.setContent(strMailBodyContent, "text/html; charset=euc-kr");

			// 메일 본문 부분.
			MimeBodyPart bodypart = new MimeBodyPart();

			// 콘텐트 타입 지정.
			bodypart.setContent(strMailBodyContent, "text/html;charset=euc-kr");

			// 메시지 전체 부분.
			Multipart multipart = new MimeMultipart();

			// 본문 추가.
			multipart.addBodyPart(bodypart);

			// 메시지에 추가.
			msg.setContent(multipart);

			// 메일 전송.
			Transport.send(msg);
			logger.info("Mail is sent, OK.");
			
			
		} catch (AddressException e) {
			logger.error("AddressException", e);
		} catch (MessagingException e) {
			e.printStackTrace();
			Exception ex = e;
			
			if(ex instanceof SendFailedException) {
				SendFailedException sfex = (SendFailedException) ex;
				Address[] invalid = sfex.getInvalidAddresses();
				if(invalid != null){
					logger.info("Invalid Addresses");
				}
				if(invalid != null){
					for(int i=0; i<invalid.length; i++)
						logger.info(" "+invalid[i]);
				}
			}
			
			
		
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}

	public class PopupAuthenticator extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {

			//개발 및 운영
			return new PasswordAuthentication(AES.aesDecodeStr(SMTP_ACCOUNT_ID, CryptoStr), AES.aesDecodeStr(SMTP_ACCOUNT_PASS, CryptoStr)); 
		}
	}
}
