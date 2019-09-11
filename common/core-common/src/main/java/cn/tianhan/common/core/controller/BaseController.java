package cn.tianhan.common.core.controller;

import cn.tianhan.common.core.exception.DataBaseException;
import cn.tianhan.common.core.utils.ResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author: NieAnTai
 * @Description: 基本控制器、异常拦截
 * @Date: 2019/08/15 11:21
 */
@Slf4j
public class BaseController {
    protected @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody doJsonDefault() {
        return new ResponseBody();
    }

    protected @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody doJsonMsg(int code, String message) {
        return new ResponseBody(code, message);
    }

    protected @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody doJsonData(Object data) {
        return new ResponseBody(data);
    }

    @ExceptionHandler({DataBaseException.class})
    private @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody handlerDataBaseException(DataBaseException e) {
        log.error("异常信息: " + e.getMessage());
        return new ResponseBody(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMsg());
    }

    @ExceptionHandler({Exception.class})
    private @org.springframework.web.bind.annotation.ResponseBody
    ResponseBody handlerException(Exception e) {
        log.error("异常信息: " + e.getMessage());
        return new ResponseBody(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
