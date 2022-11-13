package com.example.calculator.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageError {

    private String message;
    private String status;
}
