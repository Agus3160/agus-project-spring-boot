package com.fiuni.distri.project.fiuni.exception;


import com.fiuni.distri.project.fiuni.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ResponseDto> handleGenericErrors(ApiException ex) {
        return ex.generateResponse();
    }

}
