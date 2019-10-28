package com.ibm.fsd.mod.auth.handler;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.IntStream;

//@ControllerAdvice
//@ResponseBody
@Slf4j
public class ExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleHttpMessageNotReadableException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder error = new StringBuilder(bindingResult.getFieldErrors().size() * 16);

        error.append("Invalid Request: ");
        IntStream.range(0, bindingResult.getAllErrors().size()).forEach(i -> {
            if (i > 0) {
                error.append(",");
            }
            FieldError fieldError = bindingResult.getFieldErrors().get(i);
            error.append(fieldError.getField());
            error.append(":");
            error.append(fieldError.getDefaultMessage());
        });

        return JSONObject.parse(error.toString());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        log.error(e.getMessage());

        return JSONObject.parse(e.getMessage());
    }
}
