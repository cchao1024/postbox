package com.cchao.pinbox.bean.resp.app;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * app启动初始化参数
 * @author : cchao
 * @version 2019-04-13
 */
@Data
@Accessors(chain = true)
public class AppLaunch {
    int lastAndroidVersion;
    String versionUpdateMsg;
    String token;

}
