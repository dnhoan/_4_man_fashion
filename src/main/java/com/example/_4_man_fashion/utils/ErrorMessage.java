package com.example._4_man_fashion.utils;

public enum ErrorMessage implements BaseMessage {
    OK("000", "OK"),
    UNHANDLED_ERROR("001", "Unhandled error", "%s"),
    OBJECT_NOT_FOUND("002", "Object not found", "%s không tồn tại"),
    OBJECT_ALREADY_EXIST("003", "Object already exist", "%s đã tồn tại"),
    ARGUMENT_NOT_VALID("007", "Method argument not valid", "Tham số không chính xác"),

    //AuthError
    AUTH_USER_PASS_INVALID("AUTH_001", "User or Password invalid", "Tài khoản hoặc mật khẩu không chính xác"),
    UPDATE_FAIL_OBJECT_ALREADY_EXIST("008", "Update Fail, Object already exist", "Cập nhật lỗi, %s đã tồn tại thông tin tương ứng"),
    CALL_INTERNAL_API_ERROR("009", "Internal Server Error", "Internal Server Error"),

    OBJECT_CAN_NOT_UPDATE("010", "Object can not update", "%s không được phép cập nhật"),
    CUSTOM_ARGUMENT_NOT_VALID("007", "Method argument not valid", "Tham số không chính xác %s"),
    CUSTOM_ARGUMENT_NOT_VALID_V2("054", "Method argument not valid", "%s không chính xác"),
    OBJECT_NOT_FOUND_OR_INACTIVE("012", "Object not found or inactive", "%s không tồn tại hặc không hoạt động"),
    CAN_NOT_UPDATE("013", "Can not update", "Không thể cập nhật đối tượng"),
    DUPLICATE_PARAMS("409", "Duplicate unique params", "%s đã tồn tại trên hệ thống"),
    OBJECT_INACTIVE("033", "Object inactive", "%s ngừng hoạt động"),
    OBJECT_ARCHIVED("034", "Object archived", "%s đã ở trạng thái lưu trữ"),
    FIELD_CANNOT_UNSET("043", "Fields cannot unset", "%s trường không được phép để trống"),
    OBJECT_CAN_NOT_DELETE("045", "Object can not delete", "%s không được phép xóa"),
    REPASSWORD_NOT_DUPLICATE("046","Password is not duplicate", "%s không được trùng %s cũ!")
    ;

    private final String code;
    private String desc;
    private String descFormat;

    ErrorMessage(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    ErrorMessage(String code, String desc, String descFormat) {
        this.code = code;
        this.desc = desc;
        this.descFormat = descFormat;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    public ErrorMessage format(Object... str) {
        if (this.descFormat != null) {
            this.desc = String.format(this.descFormat, str);
        }
        return this;
    }
}
