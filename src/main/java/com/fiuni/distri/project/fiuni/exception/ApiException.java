package com.fiuni.distri.project.fiuni.exception;

import com.fiuni.distri.project.fiuni.dto.ResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@EqualsAndHashCode(callSuper = true)
@Data
public class ApiException extends RuntimeException {

    private HttpStatus httpStatus;
    private String[] errors;

    public ApiException(HttpStatus httpStatus, String message, String[] errors) {
        super(message);
        this.httpStatus = httpStatus;
        this.errors = errors;
    }

    public ResponseEntity<ResponseDto> generateResponse() {
        return ResponseEntity
                .status(this.httpStatus)
                .body(new ResponseDto<>(this.httpStatus.value(), false, this.getMessage(), null, this.getErrors()));
    }
}
