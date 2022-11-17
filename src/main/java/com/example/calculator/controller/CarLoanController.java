package com.example.calculator.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.calculator.dto.Contact;
import com.example.calculator.dto.Loan;
import com.example.calculator.service.ContactService;
import com.example.calculator.service.InstallmentCalculatorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CarLoanController {

    private final InstallmentCalculatorService installmentCalculatorService;
    private final ContactService contactService;

    
    @GetMapping("/calculate")
    public String viewHomePage(Model model) {
    	model.addAttribute("loan",new Loan());
        return "index";
    }
    
    @PostMapping("/calculate")
    public String calculatePage(@Valid Loan loan, BindingResult result, Model model) {

    	BigDecimal monthlyValue;
    	
        if (result.hasErrors()) {
            return "index";
        }

        try {
        	monthlyValue = installmentCalculatorService.calculate(loan);
        	model.addAttribute("monthlyValueLabel", "Mensalidade: $" + monthlyValue.setScale(2, RoundingMode.HALF_UP).toPlainString());
        	model.addAttribute("loan", loan);
        } catch(Exception e) {
        	FieldError error = new FieldError("financing", "financing", e.getMessage());
        	result.addError(error);
        	return "index";
        }
        
        return "redirect:/contact?monthlyValue=" +monthlyValue.toPlainString()+ 
        		"&numberMonthly="+loan.getFinancing().getNumberMonthlyInstallments()+
        		"&type="+loan.getFinancing().getType().getNameTypeFinancing()+
        		"&carValue="+loan.getFinancing().getCarValue();
        
    }

    @GetMapping("/contact")
    public String viewContactPage(@RequestParam(value="monthlyValue",required=false) String monthlyValue, 
    		@RequestParam(value="numberMonthly",required=false) String numberMonthly, 
    		@RequestParam(value="type",required=false) String type,
    		@RequestParam(value="carValue",required=false) String carValue,
    		Model model, Loan loan, Contact contact ) {
    	model.addAttribute("loan", loan);
        return "contact";
    }
    
    @PostMapping("/contact")
    public String saveContact(@RequestParam(value="monthlyValue",required=false) String monthlyValue, 
    		@RequestParam(value="numberMonthly",required=false) String numberMonthly, 
    		@RequestParam(value="type",required=false) String type,
    		@RequestParam(value="carValue",required=false) String carValue,
    		Loan loan, Contact contact, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "contact";
        }

        //TODO: Esse método é responsável por salvar no banco H2
        //contactService.save(contact, monthlyValue, numberMonthly, type, carValue);
        
        try {
			contactService.saveInFile(contact, monthlyValue, numberMonthly, type, carValue);
		} catch (IOException e) {
			log.error("Problemas ao gerar arquivo", e.getMessage());
		}

        return  "redirect:/calculate";
    }
}
