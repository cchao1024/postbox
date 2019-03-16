package com.cchao.pinbox.controller;

import com.cchao.pinbox.bean.resp.RespBean;
import com.cchao.pinbox.config.GlobalConfig;
import com.cchao.pinbox.constant.enums.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author : cchao
 * @version 2019-03-12
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    GlobalConfig mGlobalConfig;

    @RequestMapping("/upload")
    public RespBean upload(@RequestParam("file") MultipartFile[] uploadingFiles, HttpServletRequest request) throws IOException {

        if (uploadingFiles.length == 0) {
            return RespBean.fail(Results.FILE_EMPTY);
        }

        for (MultipartFile uploadedFile : uploadingFiles) {
            uploadedFile.transferTo(GlobalConfig.getCurUploadFile(uploadedFile.getOriginalFilename()));
        }
        return RespBean.suc();
    }
}
