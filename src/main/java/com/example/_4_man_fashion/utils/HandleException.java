package com.example._4_man_fashion.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class HandleException {
    private final static Logger LOGGER = LoggerFactory.getLogger(HandleException.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        HashMap<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
        ApiResponse<HashMap<String, String>> res = ApiResponse.error(ErrorMessage.ARGUMENT_NOT_VALID, errors);
        return ResponseEntity.ok(res);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResponse<?>> ItsolException(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        if (ex instanceof DATNException) {
            DATNException exception = (DATNException) ex;
            return ResponseEntity.ok(ApiResponse.error(exception.getError(), exception.getData()));
        }
        if (ex.getCause() instanceof DATNException) {
            DATNException exception = (DATNException) ex.getCause();
            return ResponseEntity.ok(ApiResponse.error(exception.getError(), exception.getData()));
        }
        BaseMessage message = new BaseMessage() {
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
        return ResponseEntity.ok(ApiResponse.error(message, ex.getMessage()));
    }
}
