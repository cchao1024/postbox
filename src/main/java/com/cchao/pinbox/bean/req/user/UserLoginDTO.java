package com.cchao.pinbox.bean.req.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

/**
 * 用户登录
 *
 * @author Jcxcc
 * @date 2019-02-23
 * @since 1.0
 */
@Data
public class UserLoginDTO {

    @Email(message = "不是有效的电子邮箱")
    protected String email;

    @Length(max = 12, min = 6, message = "密码需为6到12位英文和数字组合")
    protected String password;
}
