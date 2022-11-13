package com.example.calculator.service;

import com.example.calculator.dto.Loan;
import com.example.calculator.model.TypeFinancing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
@Slf4j
public class InstallmentCalculatorService {

    @Value("${factor.internal:4}")
    private Double factorInternal;
    @Value("${factor.external:6.5}")
    private Double factorExternal;
    public BigDecimal calculate(Loan loan) {

        Double factorValue = Optional.of(loan.getFinancing().getType())
                        .filter(type -> TypeFinancing.INTERNAL.equals(type))
                        .map(value -> factorInternal)
                        .orElse(factorExternal);

        BigDecimal total = loan.getTotalValue()
                .multiply(new BigDecimal(factorValue))
                .divide(new BigDecimal(loan.getFinancing().getNumberMonthlyInstallments()), 2, RoundingMode.HALF_UP);

        log.info("Total mensal ${}", total.setScale(2, RoundingMode.HALF_UP).toString());
        return total;
    }

}
