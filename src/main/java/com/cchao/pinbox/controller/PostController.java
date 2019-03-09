package com.cchao.pinbox.controller;

import com.cchao.pinbox.bean.req.post.PostDTO;
import com.cchao.pinbox.bean.resp.RespBean;
import com.cchao.pinbox.service.PostService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author : cchao
 * @version 2019-03-09
 */
@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    PostService mPostService;

    @RequestMapping("/new")
    @RequiresAuthentication
    public RespBean postNew(@Valid PostDTO params) {
        return mPostService.postNew(params);
    }


    @RequestMapping("/like")
    @RequiresAuthentication
    public RespBean like(Long id) {
        return mPostService.likePost(id);
    }
}
