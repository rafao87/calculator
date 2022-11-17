package com.example.calculator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.calculator.dto.Financing;
import com.example.calculator.dto.Loan;
import com.example.calculator.exception.InvalidNumberMontlyInstallments;
import com.example.calculator.model.TypeFinancing;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InstallmentCalculatorService {

    @Value("${factor.internal}")
    private Double factorInternal;
    @Value("${factor.external}")
    private Double factorExternal;
    
    public BigDecimal calculate(Loan loan) {
    	
    	if(isValidNumberInstallments(loan.getFinancing())) {
    		throw new InvalidNumberMontlyInstallments("Para o tipo INTERNO não é possivel inserir a quantidade de parcelas igual a 60");
    	}

        Double factorValue = Optional.of(loan.getFinancing().getType())
                        .filter(type -> TypeFinancing.INTERNAL.equals(type))
                        .map(value -> factorInternal)
                        .orElse(factorExternal);

        BigDecimal total = loan.getFinancing().getCarValue()
        		.multiply(new BigDecimal(factorValue))
                .divide(new BigDecimal(loan.getFinancing().getNumberMonthlyInstallments()), 2, RoundingMode.HALF_UP);

        log.info("Total mensal ${}", total.setScale(2, RoundingMode.HALF_UP).toString());
        return total;
    }

    public boolean isValidNumberInstallments(Financing financing) {
    	
    	if(TypeFinancing.INTERNAL.equals(financing.getType()) && financing.getNumberMonthlyInstallments() == 60 ){
    		return true;
    	}
    	
    	return false;
    }
}
