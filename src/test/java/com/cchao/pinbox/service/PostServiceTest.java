package com.cchao.pinbox.service;

import com.cchao.pinbox.bean.req.PageDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotEquals;

/**
 * @author : cchao
 * @version 2019-03-11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

    @Autowired
    PostService mPostService;

    @Test
    public void findPostVo() {
    }

    @Test
    public void findPostList() {
        int size = mPostService.findPostList(PageDTO.of(0, 10))
                .getContent().size();
        assertNotEquals(0, size);
    }
}