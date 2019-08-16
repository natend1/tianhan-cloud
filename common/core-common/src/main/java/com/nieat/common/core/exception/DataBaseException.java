package com.nieat.common.core.exception;

/**
 * @Author: NieAnTai
 * @Description: 数据库异常
 * @Date: 2019/08/16 15:51
 */
public class DataBaseException extends RuntimeException {
    private final static long serialVersionUID = 1L;
    private String msg;

    public DataBaseException() {
        this("数据库错误!");
    }

    public DataBaseException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
