package com.example.calculator.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.calculator.dto.Financing;
import com.example.calculator.dto.Loan;
import com.example.calculator.model.TypeFinancing;

@ExtendWith(MockitoExtension.class)
public class InstallmentCalculatorServiceTest {
	 
	@Mock
	private BigDecimal factorInternal = BigDecimal.valueOf(1.04);
	@Mock
	private BigDecimal factorExternal = BigDecimal.valueOf(1.065);
	@Mock
	private BigDecimal factorValue = BigDecimal.valueOf(1.04);
	
	@InjectMocks
	private InstallmentCalculatorService service;
	
	@Test
	public void calculaValorParcelaTest() {
		service = new InstallmentCalculatorService();
		Loan loan = new Loan();
		Financing financing = new Financing();
		financing.setType(TypeFinancing.INTERNAL);
		financing.setCarValue(BigDecimal.valueOf(3000));
		financing.setNumberMonthlyInstallments(12);
		loan.setFinancing(financing);
		
		BigDecimal valorParcela = service.calculate(loan);
		assertEquals(BigDecimal.valueOf(260), valorParcela);
	}

	@Test
	public void validaSeOTipoEhInternoTest() {
		String type = TypeFinancing.INTERNAL.getNameTypeFinancing();
		assertEquals("Interno", type);
	}
	
	@Test
	public void validaSeOTipoEhInternoEQtdIgualASessentaTest() {
		service = new InstallmentCalculatorService();
		Boolean isValid= service.isValidNumberInstallments(new Financing(TypeFinancing.INTERNAL, 60, new BigDecimal(1000)));
		assertEquals(true, isValid);
	}
	
	@Test
	public void validaSeOTipoNaoEhInternoTest() {
		service = new InstallmentCalculatorService();
		Boolean isValid= service.isValidNumberInstallments(new Financing(TypeFinancing.EXTERNAL, 60, new BigDecimal(1000)));
		assertEquals(false, isValid);
	}
	

}
