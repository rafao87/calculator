package com.example.calculator.dto;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.example.calculator.model.TypeFinancing;

import lombok.Data;

@Data
public class Loan {

    @NotNull
    private Financing financing;

    public Loan() {
        this.financing = new Financing();
    }

    public TypeFinancing getType() {
        return Optional.ofNullable(financing)
                .map(Financing::getType)
                .orElse(null);
    }
}
