package com.biz.ems.controller;

import java.util.List;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.biz.ems.model.EmailVO;
import com.biz.ems.service.FileService;
import com.biz.ems.service.SendMailService;

@Controller
@RequestMapping(value="/ems")
public class EmailController {
	@Autowired
	FileService fService;
	
	@Autowired
	SendMailService xMailService;
	
	@ModelAttribute("emailVO")
	public EmailVO newEmailVO() {
		return new EmailVO();
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model) {
		
		List<EmailVO> emsList = xMailService.selectAll();
		
		model.addAttribute("ELIST", emsList);
		model.addAttribute("BODY","LIST");
		
		return "home";
	}
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String view(@RequestParam("ems_seq") long ems_seq,Model model) {
		
		EmailVO emailVO = xMailService.findBySeq(ems_seq);
			
		
		model.addAttribute("EVIEW", emailVO);
		model.addAttribute("BODY", "VIEW");
		return "home";
	}
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(@ModelAttribute("emailVO") EmailVO emailVO, Model model) {
		
		emailVO.setEms_send_date("2019-08-08");
		emailVO.setEms_send_time("15:20:00");
		
		emailVO.setEms_from_email("whdtjr3973@naver.com");
		emailVO.setEms_from_name("내멋으로");
		
		
		model.addAttribute("emailVO",emailVO);
		model.addAttribute("BODY", "WRITE");
		return "home";
	}
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(
			@ModelAttribute("emailVO") EmailVO emailVO,
			@RequestParam("file1") MultipartFile file1,
			@RequestParam("file2") MultipartFile file2,
			BindingResult result,
			Model model
			) {
		
		
		String file_name1 = fService.fileUp(file1);
		emailVO.setEms_file1(file_name1);
		String file_name2 = fService.fileUp(file2);
		emailVO.setEms_file2(file_name2);
		
		int ret = fService.insert(emailVO);
		xMailService.sendMail(emailVO);
		return "home";
	}
	
	@RequestMapping(value="/delete/{ems_seq}", method=RequestMethod.GET)
	public String delete(
			
		@PathVariable long ems_seq, Model model) {
		
		fService.delete(ems_seq);
		
		return "home";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(
			@RequestParam("ems_seq")long ems_seq,
			Model model) {
		
		EmailVO emailVO = xMailService.findBySeq(ems_seq);
		
		model.addAttribute("emailVO",emailVO);
		model.addAttribute("BODY","WRITE");
		return "home";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(
			@ModelAttribute("emailVO")EmailVO emailVO,
			@RequestParam("file1") MultipartFile file1,
			@RequestParam("file2") MultipartFile file2,
			Model model) {
		
		String file_name1 = fService.fileUp(file1);
		emailVO.setEms_file1(file_name1);
		String file_name2 = fService.fileUp(file2);
		emailVO.setEms_file2(file_name2);
		
		int ret = fService.update(emailVO);
		xMailService.sendMail(emailVO);
		
		return "redirect:/ems/list";
	}
	
	
}
