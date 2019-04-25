package com.cchao.pinbox.config;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

import lombok.Data;

/**
 * @author : cchao
 * @version 2019-03-12
 */
@Data
@ConfigurationProperties(prefix = "project")
@Component
public class GlobalConfig {

    /**
     * 文件上传的保存路径
     */
    public String uploadDirPath = "/www/file/upload/";

    public static String mUserHome = System.getProperty("user.home");

    public static String getUploadFileName(String suffix) {
        return System.currentTimeMillis() % 100000 + RandomUtils.nextInt() % 10000 + suffix;
    }

    public static String getUploadDir(String type) {
        return "/upload/" + type + "/" + DateFormatUtils.format(System.currentTimeMillis(), "yyyy_MM_dd/");
    }

    public static String getUploadAbsolutePath(String relativePath) {
        // https://insomnia-1254010092.cos.ap-hongkong.myqcloud.com/common/default/avatar.png
        return "https://insomnia-1254010092.cos.ap-hongkong.myqcloud.com" + relativePath;
    }

    public static File getTempSaveFile(String uploadDirPath, String fileName) {
        String dir = mUserHome + uploadDirPath;
        if (!new File(dir).exists()) {
            new File(dir).mkdirs();
        }
        return new File(dir + fileName);
    }
}
