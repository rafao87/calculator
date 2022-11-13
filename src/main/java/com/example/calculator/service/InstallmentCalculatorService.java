package com.example.calculator.service;

import com.example.calculator.dto.Loan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Slf4j
public class InstallmentCalculatorService {

    public BigDecimal calculate(Loan loan) {

        BigDecimal total = loan.getTotalValue()
                .multiply(new BigDecimal(loan.getType().getTax()))
                .divide(new BigDecimal(loan.getNumberMonthlyInstallments()), 2, RoundingMode.HALF_UP);

        log.info("Total mensal ${}", total.setScale(2, RoundingMode.HALF_UP).toString());
        return total;
    }
}
