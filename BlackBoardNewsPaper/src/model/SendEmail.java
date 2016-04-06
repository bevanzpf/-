package model;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	public static final String HOST = "smtp.163.com";
	public static final String PROTOCOL = "smtp";
	public static final int PORT = 25;
	public static final String FROM ="bevanpf@163.com";
	public static final String PWD = "bevanzpf123";
	
	private static Session getSession(){
		Properties props = new Properties();
		props.put("mail.smtp.host", HOST);
		props.put("mail.store.protocol", PROTOCOL);
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.auth",true);
		
		Authenticator authenticator = new Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(FROM,PWD);
			}
		};
		Session session = Session.getDefaultInstance(props,authenticator);
		return session;
	}
	
	public static void send(String toEmail,String content,String subject){
		Session session = getSession();
		try{
			System.out.println("---send---"+content);
			Message msg = new MimeMessage(session);
			
			msg.setFrom(new InternetAddress(FROM));
			InternetAddress[] address = {new InternetAddress(toEmail)};
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);
			msg.setSentDate(new Date());
			msg.setContent(content,"text/html;charset=utf-8");
			
			Transport.send(msg);
		}catch(MessagingException mex){
			mex.printStackTrace();
		}
	}
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer("欢迎使用xxx,点击下面链接激活账号，"
				+ "48小时生效，否则重新注册");
		sb.append("<a href=\"http://hello.com/register?action=active&email=/");
		sb.append("&validateCode=");
		sb.append("<a>");
		SendEmail.send("bevanzpf@gmail.com", sb.toString(),"register");
	}
}
