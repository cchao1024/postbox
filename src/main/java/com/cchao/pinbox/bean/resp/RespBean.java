package com.cchao.pinbox.bean.resp;

import com.cchao.pinbox.constant.enums.Results;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class RespBean<T> {
    int code;
    String msg;
    T data;

    public static RespBean suc() {
        return new RespBean<>(Results.SUC);
    }

    public static <T> RespBean<T> suc(T data) {
        return RespBean.suc(data, Results.SUC.getMessage());
    }

    public static <T> RespBean<T> suc(T data, String msg) {
        return new RespBean<T>(Results.SUC).setData(data).setMsg(msg);
    }

    public static RespBean fail(Results results) {
        return new RespBean(results.getCode(), results.getMessage());
    }

    public static RespBean of(int code, String msg) {
        return new RespBean(code, msg);
    }

    public RespBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RespBean(Results results) {
        this(results.getCode(), results.getMessage());
    }
}
