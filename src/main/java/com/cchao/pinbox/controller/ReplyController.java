package com.cchao.pinbox.controller;

import com.cchao.pinbox.bean.req.post.ReplyDTO;
import com.cchao.pinbox.bean.resp.RespBean;
import com.cchao.pinbox.service.ReplyService;
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
@RequestMapping(value = "/reply")
public class ReplyController {

    @Autowired
    ReplyService mReplyService;

    @RequestMapping("/new")
    @RequiresAuthentication
    public RespBean ReplyNew(@Valid ReplyDTO params) {
        return mReplyService.ReplyNew(params);
    }


    @RequestMapping("/like")
    @RequiresAuthentication
    public RespBean like(Long id) {
        return mReplyService.likeReply(id);
    }
}
