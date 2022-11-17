package com.example.calculator.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.example.calculator.model.TypeFinancing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Financing {

    @NotNull(message = "Tipo é obrigatório")
    private TypeFinancing type;
    @NotNull(message = "Total de mensalidades é obrigatório")
    private Integer numberMonthlyInstallments;
    @NotNull(message = "Valor total é obrigatório")
    private BigDecimal carValue;
}
