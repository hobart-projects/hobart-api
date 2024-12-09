package io.github.hobart.api;

import io.github.hobart.api.enums.ResultCodeEnum;

import java.io.Serializable;

public class Result<T> implements ResultApi<T, Result<T>>, Serializable {

    public static final long serialVersionUID = -1L;

    private String code;

    private String msg;

    private T data;

    public Result() {
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public boolean isSuccess() {
        return ResultCodeEnum.isSuccess(this.code);
    }

    @Override
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public Result<T> apply(T t) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getDesc(), t);
    }

    @Override
    public T get() {
        return this.data;
    }
}
