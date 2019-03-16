package com.cchao.pinbox.config;

import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

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

    public static File getCurUploadFile(String fileName) {
        String dir = mUserHome + "/file/upload/" + DateFormatUtils.format(System.currentTimeMillis(), "yyyy_MM_dd/");
        if (!new File(dir).exists()) {
            new File(dir).mkdirs();
        }
        return new File(dir + fileName);
    }
}
