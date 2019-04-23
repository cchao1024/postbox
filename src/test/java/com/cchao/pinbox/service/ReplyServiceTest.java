package com.cchao.pinbox.service;

import com.cchao.pinbox.bean.req.post.ReplyDTO;
import com.cchao.pinbox.bean.resp.RespBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author : cchao
 * @version 2019-03-11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReplyServiceTest {

    @Autowired
    ReplyService mReplyService;

    @Test
    public void replyNew() {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setTo(2)
                .setContent("回复 2 的回复")
                .setImages("image");

        RespBean respBean = mReplyService.ReplyNew(replyDTO);
        assertEquals(0, respBean.getCode());
    }
}