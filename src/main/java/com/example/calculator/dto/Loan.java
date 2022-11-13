package com.example.calculator.dto;

import com.example.calculator.annotation.NumberInstallmentsType;
import com.example.calculator.exception.InvalidNumberMontlyInstallments;
import com.example.calculator.model.TypeFinancing;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class Loan {

    @NumberInstallmentsType
    private Financing financing;
    @NotNull(message = "Valor total é obrigatório")
    private BigDecimal totalValue;

    public Loan() {
        this.financing = new Financing();
    }

    public TypeFinancing getType() {
        return Optional.ofNullable(financing)
                .map(Financing::getType)
                .orElse(null);
    }
}
