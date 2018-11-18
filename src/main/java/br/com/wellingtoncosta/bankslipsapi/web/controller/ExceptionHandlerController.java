package br.com.wellingtoncosta.bankslipsapi.web.controller;

import br.com.wellingtoncosta.bankslipsapi.web.json.ExceptionJson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * @author Wellington Costa on 17/11/18
 */
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionJson> handleMethodArgumentNotValidException() {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(
                        ExceptionJson.builder()
                                .timestamp(new Date())
                                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                                .error("Unprocessable entity")
                                .message("Some properties are not present or are not valid.")
                                .build()
                );
    }

}
