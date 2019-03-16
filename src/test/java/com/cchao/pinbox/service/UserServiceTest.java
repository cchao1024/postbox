package com.cchao.pinbox.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : cchao
 * @version 2019-03-12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService mUserService;

    @Test
    @Transactional
    public void findUserById() {
        String nikeName = mUserService.findUserById(2L).getNickName();
        Assert.assertEquals(nikeName, "666666");
    }

    @Test
    public void findUserByEmail() {
        String nikeName = mUserService.findUserByEmail("888888@qq.com").getNickName();
        Assert.assertEquals(nikeName, "888888");
    }
}