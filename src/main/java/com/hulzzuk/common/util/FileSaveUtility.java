package com.hulzzuk.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@Component
public class FileSaveUtility {
	
	

    public String save(MultipartFile file, String filePath) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        // UUID로 고유한 파일 이름 생성
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + extension;

        File saveFile = new File(filePath + newFilename);

        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // JSP에서 사용할 상대경로로 리턴
        return "/resources/images/logList/" + newFilename;
    }
    
    // 파일 저장 로직
    public static String saveFile(MultipartFile file, String basePath, String subDir) throws Exception {
        if (file == null || file.isEmpty()) {
            return null;
        }

        // 저장 경로 설정
        String fullPath = basePath + File.separator + subDir;
        File dir = new File(fullPath);
        if (!dir.exists()) dir.mkdirs();

        // 파일명 변경: 예시 log_202505152312.jpg
        String originalFileName = file.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        String newFileName = "log_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ext;

        // 실제 저장
        File savedFile = new File(fullPath, newFileName);
        file.transferTo(savedFile);

        // 리턴 경로 (웹에서 사용될 경로)
        return "/resources/images/" + subDir + "/" + newFileName;
    }
    
    
    
}
