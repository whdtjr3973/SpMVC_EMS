package com.biz.ems.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.biz.ems.mapper.EmailDao;
import com.biz.ems.model.EmailVO;

@Service
public class FileService {

	private final String uploadFolder = "c:/bizwork/upload/";
	@Autowired
	EmailDao eDao;
	
	public String fileUp(MultipartFile file) {
		
		String fileName = file.getOriginalFilename();
		
		File saveFile = new File(uploadFolder,fileName);
		
		try {
			file.transferTo(saveFile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileName;
	}
	public int insert(@RequestParam("emailVO") EmailVO emailVO) {
		
		int ret = eDao.insert(emailVO);
		
		return ret;
	}
	//vo일경우 ModelAttribute
	public int update(@ModelAttribute("emailVO") EmailVO emailVO) {
		int ret = eDao.update(emailVO);
		
		return ret;
	}
	
	public int delete(long ems_seq) {
		// TODO Auto-generated method stub
		
		int ret = eDao.delete(ems_seq);
		return ret;
	}
}
