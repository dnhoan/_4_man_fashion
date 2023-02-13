package com.example._4_man_fashion.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DATNException extends RuntimeException{

    private BaseMessage error;
    private Object data;

    public DATNException() {
    }

    public DATNException(String message) {
        super(message);
        this.error = new BaseMessage() {
            @Override
            public String getCode() {
                return "0001";
            }

            @Override
            public String getDesc() {
                return "Unhandled error";
            }

            @Override
            public ErrorMessage format(Object... str) {
                return null;
            }
        };
    }

    public DATNException(BaseMessage error) {
        super(error.getDesc());
        this.error = error;
    }

    public DATNException(String message, Throwable cause) {
        super(message, cause);
    }

    public DATNException(Throwable cause) {
        super(cause);
    }

    public DATNException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DATNException(ApiResponse<?> response) {
        super(response.getDesc());
        this.error = new BaseMessage() {
            @Override
            public String getCode() {
                return response.getCode();
            }

            @Override
            public String getDesc() {
                return response.getDesc();
            }

            @Override
            public ErrorMessage format(Object... str) {
                return null;
            }
        };
        this.data = response.getData();
    }
}
