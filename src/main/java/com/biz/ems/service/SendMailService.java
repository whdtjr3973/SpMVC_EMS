package com.biz.ems.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.biz.ems.controller.HomeController;
import com.biz.ems.mapper.EmailDao;
import com.biz.ems.model.EmailVO;

@Service
public class SendMailService {
	
	@Autowired
	EmailDao eDao;
	@Autowired
	JavaMailSender xMail;
	@Autowired
	ServletContext context;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private final String uploadFolder = "c:/bizwork/upload";
	
	public void sendMail(EmailVO emailVO) {
		String from_email = emailVO.getEms_from_email();
		String from_name = emailVO.getEms_from_name();
		String to_email = emailVO.getEms_to_email();
		String subject = emailVO.getEms_subject();
		String content = emailVO.getEms_content();
		String file1 = emailVO.getEms_file1();
		String file2 = emailVO.getEms_file2();
		//Email에 사용되는 문서구조 생성 : MimeMessage라고한다
		MimeMessage message = xMail.createMimeMessage();
		
		// 메일을 보낼때 사용할 보조도구 선언
		MimeMessageHelper mHelper;
		
		try {
			
			// MimeMessageHelper를 만들때 
			// 2번째 항목을 true로 하면 메일에 파일을 첨부할 수 있고
			// 				false로 하면 수순하면 text만 전송이 된다.
			
			mHelper = new MimeMessageHelper(message,true,"UTF-8");
			mHelper.setFrom(from_email,from_name);
			mHelper.setTo(to_email);
			mHelper.setSubject(subject);
			
			// 2번째 항목을
			//		true 하면 HTML방식으로 메일보내기
			//		생략하거나 false 하면 text방식으로 보내기
			mHelper.setText(content,true);
			
			// 서버의 저장소에 저장된 파일을
			// 메일에 첨부할 수 있도록 변환하는 과정
			FileSystemResource fr =null;
 
			
			// fr파일리소스를 file1의 이름으로 첨부하겠다.
			if(!file1.isEmpty()) {
				
				fr=new FileSystemResource(new File(uploadFolder,file1));
				
				mHelper.addAttachment(file1, fr);
			}
			
			
			if(!file2.isEmpty()) {
				
				fr=new FileSystemResource(new File(uploadFolder,file2));
				
				mHelper.addAttachment(file2, fr);
			}
			
	
			xMail.send(message);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public List<EmailVO> selectAll() {
		// TODO Auto-generated method stub
		
		List<EmailVO> eList = eDao.selectAll();
		return eList;
	}


	public EmailVO findBySeq(long ems_seq) {
		// TODO Auto-generated method stub
		
		EmailVO emailVO = eDao.findBySeq(ems_seq);
		return emailVO;
	}


	




	
}