package cn.itcast.utils;

import cn.itcast.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUpload {
    public static String upload(String filename, String filepath, MultipartFile upload) throws IOException {
        File file = new File(filepath); //上传到images下
        if (!file.exists())
            file.mkdirs();
        // 直接获得上传文件名字
        System.out.println(filename);
        String extend = filename.substring(filename.lastIndexOf("."));
        if (extend.equals(".jpg") || extend.equals(".png")) {
            filename = System.currentTimeMillis() + extend;
            upload.transferTo(new File(file, filename));
            System.out.println("文件" + filename + "上传成功1");

        }
        return filename;
    }
}
