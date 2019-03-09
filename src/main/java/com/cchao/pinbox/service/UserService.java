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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author : cchao
 * @version 2019-01-31
 */
@Service
public class UserService {

    @Autowired
    UserRepository mUserRepository;

    public User findUserById(Long id) {
        return mUserRepository.getOne(id);
    }

    public void increaseLike(Long id) {
        // 用户 like +1
        User user = mUserRepository.getOne(id);
        mUserRepository.save(user.increaseLike());
    }

    public User findUserByEmail(String email) {
        return mUserRepository.findByEmail(email)
                .orElse(null);
    }

    public LoginResp login(UserLoginDTO params) {
        String email = params.getEmail();
        String password = params.getPassword();

        User user = findUserByEmail(email);
        boolean validPassword = StringUtils.equals(user.getPassword(), password);
        if (validPassword) {
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
