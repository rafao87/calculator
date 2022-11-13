package com.example.calculator.dto;

import com.example.calculator.model.TypeFinancing;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Loan {

    private TypeFinancing type;

    private int numberMonthlyInstallments;

    private BigDecimal totalValue;
}
