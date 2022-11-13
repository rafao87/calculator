package com.example.calculator.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class RestControllerExceptions {

    @ExceptionHandler(InvalidNumberMontlyInstallments.class)
    public ResponseEntity<?> invalidNumberMontlyInstallments(InvalidNumberMontlyInstallments ex, HttpServletRequest request) {
      log.error("invalidNumberMontlyInstallments() ", ex);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageError.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST.toString()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<List> processUnmergeException(final MethodArgumentNotValidException ex) {

        List list = ex.getBindingResult().getAllErrors().stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
    }
}
