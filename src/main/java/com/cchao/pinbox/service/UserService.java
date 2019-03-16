package com.cchao.pinbox.service;

import com.cchao.pinbox.bean.req.user.UserLoginDTO;
import com.cchao.pinbox.bean.req.user.UserSignUpDTO;
import com.cchao.pinbox.bean.resp.user.LoginResp;
import com.cchao.pinbox.constant.enums.Results;
import com.cchao.pinbox.dao.User;
import com.cchao.pinbox.exception.CommonException;
import com.cchao.pinbox.exception.SystemErrorMessage;
import com.cchao.pinbox.repository.UserRepository;
import com.cchao.pinbox.security.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author : cchao
 * @version 2019-01-31
 */
@CacheConfig(cacheNames = "User")
@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository mUserRepository;

    @Cacheable(key = "'user_id_' + #id", unless = "#result eq null")
    public User findUserById(Long id) {
        log.info("UserService#findUserById:" + id);
        return mUserRepository.getOne(id);
    }

    public void increaseLike(Long id) {
        // 用户 like +1
        User user = mUserRepository.getOne(id);
        mUserRepository.save(user.increaseLike());
    }

    @Cacheable(key = "'user_email_' + #id", unless = "#result eq null")
    public User findUserByEmail(String email) {
        log.info("UserService#findUserByEmail:" + email);
        return mUserRepository.findByEmail(email)
                .orElse(null);
    }

    public LoginResp login(UserLoginDTO params) {
        String email = params.getEmail();
        String password = params.getPassword();

        User user = findUserByEmail(email);
        // 检验密码是否正确
        boolean validPassword = StringUtils.equals(user.getPassword(), password);
        if (validPassword) {
            // 登录成功 返回 token
            String token = JWTUtil.sign(email, user.getId(), password);

            return new LoginResp()
                    .setAge(user.getAge())
                    .setNikeName(user.getNickName())
                    .setEmail(email)
                    .setToken(token);
        } else {
            throw new CommonException(SystemErrorMessage.USER_PASSWORD_INVALID);
        }
    }

    public LoginResp signup(UserSignUpDTO params) {
        String email = params.getEmail();
        String password = params.getPassword();
        String nikeName = params.getEmail().split("@")[0];

        User user = findUserByEmail(email);
        if (user != null) {
            throw CommonException.of(Results.EMAIL_EXIST);
        }

        user = new User().setEmail(email)
                .setNickName(nikeName)
                .setPassword(password);

        Long id = mUserRepository.save(user).getId();
        return new LoginResp()
                .setAge(user.getAge())
                .setNikeName(user.getNickName())
                .setEmail(email)
                .setToken(JWTUtil.sign(email, id, password));
    }

    /**
     * 更新用户信息
     */
    public User saveUserInfo(String email, Map<String, String> map) {
        User user = findUserByEmail(email);

        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                switch (entry.getKey()) {
                    case "nickName":
                        user.setNickName(entry.getValue());
                        break;
                    case "age":
                        user.setAge(Integer.valueOf(entry.getValue()));
                        break;
                }
            }
            mUserRepository.save(user);
        } catch (Exception ex) {
            throw CommonException.of(Results.PARAM_ERROR);
        }
        return user;
    }
}
