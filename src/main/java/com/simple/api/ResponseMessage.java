package com.simple.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResponseMessage<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ResponseMessage<T> success(T data) {
        return new ResponseMessage<>(HttpStatus.OK.value(), "success", data);
    }

    public static <T> ResponseMessage<T> success() {
        return new ResponseMessage<>(HttpStatus.OK.value(), "success", null);
    }

    public static <T> ResponseMessage<T> error(String message) {
        return new ResponseMessage<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }
}