package com.example.calculator.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        //return "redirect:/contact?monthlyValue=" +monthlyValue.toPlainString();
        return "redirect:/contact?monthlyValue=" +monthlyValue.toPlainString()+ 
        		"&numberMonthly="+loan.getFinancing().getNumberMonthlyInstallments()+
        		"&type="+loan.getFinancing().getType().getNameTypeFinancing()+
        		"&carValue="+loan.getFinancing().getCarValue();
        /*return "contact?monthlyValue=" +monthlyValue.toPlainString()+ 
				"&numberMonthly="+loan.getFinancing().getNumberMonthlyInstallments()+
				"&type="+loan.getFinancing().getType().getNameTypeFinancing()+
				"&carValue="+loan.getFinancing().getCarValue();*/
        
    }

    @GetMapping("/contact")
    public String viewContactPage(Model model, Loan loan, Contact contact ) {
    	model.addAttribute("loan", loan);
        return "contact";
    }
    
    @PostMapping("/contact")
    public String saveContact(Loan loan, Contact contact, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "contact";
        }

        //contactService.save(contact);
        
        try {
			contactService.saveInFile(contact, loan/*, monthlyValue, numberMonthly, type, carValue*/);
		} catch (IOException e) {
			log.error("Problemas ao gerar arquivo", e.getMessage());
		}

        return  "redirect:/calculate";
    }
}
