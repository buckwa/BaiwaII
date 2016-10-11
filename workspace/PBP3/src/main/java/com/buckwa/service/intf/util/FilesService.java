package com.buckwa.service.intf.util;

import java.util.List;

import com.buckwa.domain.util.Files;

public interface FilesService {

	
	public Files getFileByPath(String filePath);
	
	public Files find(int id);
	public List<Files> listAll() ;
	public void save(final Files file);
	public void delete(int id) ;
}
