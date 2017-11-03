package com.zxlvoid.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author whoiszxl
 *
 */
public interface IFileService {
	String upload(MultipartFile file, String path);
}
