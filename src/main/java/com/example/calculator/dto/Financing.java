package com.example.calculator.dto;

import com.example.calculator.model.TypeFinancing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Financing {

    @NotNull(message = "Tipo é obrigatório")
    private TypeFinancing type;
    @NotNull(message = "Total de mensalidades é obrigatório")
    private Integer numberMonthlyInstallments;
}
