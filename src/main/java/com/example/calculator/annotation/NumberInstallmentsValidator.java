package com.example.calculator.annotation;

import com.example.calculator.dto.Financing;
import com.example.calculator.model.TypeFinancing;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class NumberInstallmentsValidator implements ConstraintValidator<NumberInstallmentsType, Financing> {

    @Override
    public void initialize( NumberInstallmentsType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Financing financing, ConstraintValidatorContext constraintValidatorContext) {
        return Optional.ofNullable(financing)
                .map(Financing::getType)
                .map(TypeFinancing::getIntervals)
                .stream().anyMatch(list -> list.contains(financing.getNumberMonthlyInstallments()));
    }

}
