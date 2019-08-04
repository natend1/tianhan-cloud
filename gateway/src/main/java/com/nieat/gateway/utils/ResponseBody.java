package com.nieat.gateway.utils;

import lombok.Data;

/**
 * @Author NieAt
 * @Description
 * @create 2019/6/30 0030 下午 16:44
 */
@Data
public class ResponseBody {
    private Integer code;
    private String message;
    private Object data;
}
