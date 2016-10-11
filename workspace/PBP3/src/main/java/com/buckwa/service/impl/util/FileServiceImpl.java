package com.buckwa.service.impl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.List;

import org.slf4j.Logger;import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.buckwa.domain.util.Files;
import com.buckwa.domain.validator.UserValidator;
import com.buckwa.service.intf.util.FilesService;

@Service("fileService")
public class FileServiceImpl implements FilesService{	
	private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	@Override
	public Files getFileByPath(String filePath) {
		logger.info(" ## FileServiceImpl.getFileByPath  filePath: "+filePath);	
		Files returnFile = new Files();
		try{ 
			 File f = new File(filePath);
			    FileInputStream fin = null;
			    FileChannel ch = null;
			    try {
			        fin = new FileInputStream(f);
			        ch = fin.getChannel();
			        int size = (int) ch.size();
			        MappedByteBuffer buf = ch.map(MapMode.READ_ONLY, 0, size);
			        byte[] bytes = new byte[size];
			        buf.get(bytes);
			        returnFile.setFile(bytes);
			        returnFile.setFilename(f.getName());
			    } catch (IOException e) {
			        // TODO Auto-generated catch block
			        e.printStackTrace();
			    } finally {
			        try {
			            if (fin != null) {
			                fin.close();
			            }
			            if (ch != null) {
			                ch.close();
			            }
			        } catch (IOException e) {
			            // TODO Auto-generated catch block
			            e.printStackTrace();
			        }
			    }
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return returnFile;
	}

	@Override
	public Files find(int id) {
 
		 return null;  
	}

	@Override
	public List<Files> listAll() {
 

	         return null;  

	}

	@Override
	public void save(Files file) {
	 
		
	}

	@Override
	public void delete(int id) {
	 
		
	}
	
	

}
