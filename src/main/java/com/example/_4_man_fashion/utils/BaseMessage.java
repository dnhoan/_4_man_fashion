package com.example._4_man_fashion.utils;

public interface BaseMessage {
    String CODE_SUCCESS = "000";
    String DESC_SUCCESS = "OK";

    String getCode();

    String getDesc();

    ErrorMessage format(Object... str);
}
