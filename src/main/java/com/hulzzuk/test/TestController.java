package com.hulzzuk.test;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class TestController {

	@Autowired
	private TestService testService;

	@RequestMapping(value = "test.do", method = RequestMethod.GET)
	public String test(Model model) {
		try {
			String result = testService.connectTest();
			model.addAttribute("dbResult", result);
			model.addAttribute("message", "DB 연결 성공!");
		} catch (Exception e) {
			model.addAttribute("dbResult", null);
			model.addAttribute("message", "DB 연결 실패: " + e.getMessage());
		}
		return "test"; // test.jsp로 이동
	}

	@RequestMapping(value = "mail.do", method = RequestMethod.GET)
	public String mail(Model model) {
		try {
			String to = "thdalstj0450@gmail.com"; // 받는 사람의 이메일 주소
//			String to = "gioconda77@daum.net"; // 받는 사람의 이메일 주소
			String from = "jungdongju99@gmail.com"; // 보내는 사람의 이메일 주소
			String password = "egzibfksztflconr"; // 보내는 사람의 이메일 계정 비밀번호
			String host = "smtp.gmail.com"; // 구글 메일 서버 호스트 이름

			// SMTP 프로토콜 설정
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", host);
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.auth", "true");
			props.setProperty("mail.smtp.starttls.enable", "true");

			// 보내는 사람 계정 정보 설정
			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(from, password);
				}
			});

			// 메일 내용 작성
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			msg.setSubject("메일 제목");
			msg.setText("메일 내용");

			// 메일 보내기
			Transport.send(msg);
			model.addAttribute("message", "메일 보내 성공!");
		} catch (Exception e) {
			model.addAttribute("dbResult", null);
			model.addAttribute("message", "메일 보내기 실: " + e.getMessage());
		}
		return "mail"; // mail.jsp로 이동
	}
}
