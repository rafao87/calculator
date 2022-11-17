package com.example.calculator.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.calculator.dto.Financing;
import com.example.calculator.dto.Loan;
import com.example.calculator.model.TypeFinancing;

@RunWith(MockitoJUnitRunner.class)
public class InstallmentCalculatorServiceTest {
	
	@InjectMocks
	private InstallmentCalculatorService service;
	
	@Before
	public void setup(){
		service = Mockito.spy(new InstallmentCalculatorService()); // Here!
	}
	
	@Test
	public void calculaValorParcelaTest() {
		Loan loan = new Loan();
		Financing financing = new Financing();
		financing.setType(TypeFinancing.INTERNAL);
		financing.setCarValue(BigDecimal.valueOf(3000));
		financing.setNumberMonthlyInstallments(12);
		loan.setFinancing(financing);
		
		Mockito.doReturn(1.04).when(service).getFactorInternal();
		
		BigDecimal valorParcela = service.calculate(loan);
		assertEquals(BigDecimal.valueOf(260.00).setScale(2, RoundingMode.HALF_UP), valorParcela.setScale(2, RoundingMode.HALF_UP));
	}
	
}
