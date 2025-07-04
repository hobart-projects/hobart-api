package io.github.hobart.api;

import io.github.hobart.api.enums.ResultCodeEnum;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Results {

    private Results() {
        /** Suppresses default constructor, ensuring non-instantiability */
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getDesc(), null);
    }

    public static <T> Result<T> success(T data) {
        requireNonNull(data, "data not null");
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getDesc(), data);
    }

    public static <T> PageResult<T> success(List<T> list, long total, int pageSize) {
        return PageResult.success(list, total, pageSize);
    }

    public static <T> PageResult<T> success(List<T> list, long total, int pageNum, int pageSize) {
        return PageResult.success(list, total, pageNum, pageSize);
    }

    public static <T> Result<T> success(T data, String msg) {
        requireNonNull(data, "data not null");
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), msg, data);
    }

    public static <T> PageResult<T> emptyPage() {
        return PageResult.success(Collections.emptyList(), 0L, 10);
    }

    public static <T> PageResult<T> emptyPage(int pageSize) {
        return PageResult.success(Collections.emptyList(), 0L, pageSize);
    }

    public static <T> Result<T> failure() {
        return new Result<>(ResultCodeEnum.FAILURE.getCode(), ResultCodeEnum.FAILURE.getDesc());
    }

    public static <T> Result<T> failure(String msg) {
        return new Result<>(ResultCodeEnum.FAILURE.getCode(), msg == null ? ResultCodeEnum.FAILURE.getDesc() : msg);
    }

    public static <T> Result<T> failure(Integer code, String msg) {
        requireNonNull(code, "code not null");
        if (ResultCodeEnum.isSuccess(code)) {
            throw new IllegalArgumentException("code " + code + " is success");
        }
        return new Result<>(code, msg == null ? ResultCodeEnum.FAILURE.getDesc() : msg);
    }

    public static <T> Result<T> failure(T data) {
        return new Result<>(ResultCodeEnum.FAILURE.getCode(), ResultCodeEnum.FAILURE.getDesc(), data);
    }

    public static <T> Result<T> failure(T data, String msg) {
        return new Result<>(ResultCodeEnum.FAILURE.getCode(), msg == null ? ResultCodeEnum.FAILURE.getDesc() : msg, data);
    }

    public static <T> Result<T> failure(Throwable throwable) {
        requireNonNull(throwable, "throwable not null");
        return new Result<>(ResultCodeEnum.FAILURE.getCode(), throwable.getMessage());
    }

    public static <T> Result<T> result(T data, int code, String msg) {
        return new Result<>(code, msg, data);
    }

    public static <T> ResultOps<T> of(Result<T> original) {
        return new ResultOps<>(Objects.requireNonNull(original));
    }
}
