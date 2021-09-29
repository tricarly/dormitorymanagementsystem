package com.suyi.dms.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UploadFileUtils {
    public String uploadFile(MultipartFile file) throws IOException {
        String path = "D:\\SAVE\\upload\\dms\\student\\";
        String originalFilename = file.getOriginalFilename();
        String type=originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
        String picture= UUID.randomUUID()+type;
        file.transferTo(new File(path+picture));
        return picture;
    }
}
