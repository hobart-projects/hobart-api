package io.github.hobart.api.enums;

public enum ResultCodeEnum implements BaseEnum<String> {

    SUCCESS("0", "ok"),

    FAILURE("1","failure"),
    ;

    private final String code;

    private final String desc;

    ResultCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public static boolean isSuccess(String code) {
        return SUCCESS.code.equals(code);
    }

    public static boolean isFailure(String code) {
        return !isSuccess(code);
    }
}
