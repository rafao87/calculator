package com.example.calculator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.calculator.dto.Loan;
import com.example.calculator.model.TypeFinancing;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InstallmentCalculatorService {

	/** Variável responsável por armazenar o valor do factor interno */
    @Value("${factor.internal}")
    private Double factorInternal;
    /** Variável responsável por armazenar o valor do factor interno */
    @Value("${factor.external}")
    private Double factorExternal;
    
    /**
     * Método responsável por calcular o valor da parcela
     * 
     * @param loan
     * @return
     */
    public BigDecimal calculate(Loan loan) {
    	
        Double factorValue = Optional.of(loan.getFinancing().getType())
                        .filter(type -> TypeFinancing.INTERNAL.equals(type))
                        .map(value -> getFactorInternal())
                        .orElse(getFactorExternal());

        BigDecimal total = loan.getFinancing().getCarValue()
        		.multiply(new BigDecimal(factorValue))
                .divide(new BigDecimal(loan.getFinancing().getNumberMonthlyInstallments()), 2, RoundingMode.HALF_UP);

        log.info("Total mensal ${}", total.setScale(2, RoundingMode.HALF_UP).toString());
        return total;
    }

	public Double getFactorInternal() {
		return factorInternal;
	}
	
	public Double getFactorExternal() {
		return factorExternal;
	}

}
