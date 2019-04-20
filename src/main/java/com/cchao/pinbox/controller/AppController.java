package com.cchao.pinbox.controller;

import com.cchao.pinbox.bean.resp.RespBean;
import com.cchao.pinbox.bean.resp.app.AppLaunch;
import com.cchao.pinbox.bean.resp.user.LoginResp;
import com.cchao.pinbox.constant.Constant;
import com.cchao.pinbox.security.JWTUtil;
import com.cchao.pinbox.service.UserService;
import com.cchao.pinbox.util.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 移动端app controller
 *
 * @author : cchao
 * @version 2019-04-13
 */
@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    UserService userService;

    /**
     * 获取启动初始化
     *
     * @param request
     * @return
     */
    @RequestMapping("/getLaunch")
    public RespBean<AppLaunch> getLaunch(HttpServletRequest request) {
        String token = JWTUtil.getToken(request);
        LoginResp loginResp = null;
        Logs.println(token);
        // 没有token 就给他游客身份
        if (!JWTUtil.haveToken(request)) {
            loginResp = userService.visitorSignup();
        } else {
            loginResp = userService.updateToken(token);
        }
        // token 过期
//        if(JWTUtil.verify())

        AppLaunch appLaunch = new AppLaunch();
        appLaunch.setLastAndroidVersion(Constant.ANDROID_LAST_VERSION)
                .setVersionUpdateMsg("empty")
                .setUserInfo(loginResp);
        return RespBean.suc(appLaunch);
    }
}
