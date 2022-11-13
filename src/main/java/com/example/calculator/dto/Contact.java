package com.example.calculator.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Contact {

    @NotNull
    private String name;
    @NotNull
    private String contact;

}
