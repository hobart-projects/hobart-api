package io.github.hobart.api.enums;

import java.util.Objects;

public enum ResultCodeEnum implements BaseEnum<Integer> {

    SUCCESS(0, "ok"),

    FAILURE(1,"failure"),
    ;

    private final Integer code;

    private final String desc;

    ResultCodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public static boolean isSuccess(Integer code) {
        return Objects.equals(SUCCESS.getCode(), code);
    }

    public static boolean isFailure(Integer code) {
        return !isSuccess(code);
    }
}
