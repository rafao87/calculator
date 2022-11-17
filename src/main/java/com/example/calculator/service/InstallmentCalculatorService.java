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

    @Value("${factor.internal:4}")
    private Double factorInternal;
    @Value("${factor.external:6.5}")
    private Double factorExternal;
    /*@Value("${interval.number.installments.internal:2..18}")
    private List intervalNumberInstallmentsInternal;
    @Value("${interval.number.installments.external:12,24,36,48,60}")
    private List intervalNumberInstallmentsExternal;*/
    
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
    	/*List intervals = Optional.of(financing.getType())
                .filter(type -> TypeFinancing.INTERNAL.equals(type))
                .map(value -> intervalNumberInstallmentsInternal)
                .orElse(intervalNumberInstallmentsExternal);
    	
        return intervals.contains(String.valueOf(financing.getNumberMonthlyInstallments()));*/
    }
    
    /*public List<Integer> listMonths() {
		
		List<Integer> months = new ArrayList<>();
        months.add(12);
        months.add(24);
        months.add(36);
        months.add(48);
        months.add(60);
		
		return months;
	}*/
}
