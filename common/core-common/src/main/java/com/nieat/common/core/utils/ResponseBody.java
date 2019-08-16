package com.nieat.common.core.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: NieAnTai
 * @Description:
 * @Date: 2019/08/15 11:26
 */
@Data
public class ResponseBody {
    private Integer code;
    private String message;
    private Object data;

    public ResponseBody() {
        this(200, "操作成功!", "");
    }

    public ResponseBody(Object data) {
        this(200, "操作成功!", data);
    }

    public ResponseBody(int code, String message) {
        this(code, message, "");
    }

    public ResponseBody(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
