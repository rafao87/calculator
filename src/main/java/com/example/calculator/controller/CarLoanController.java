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

    /**
     * Método responsável por exibir a tela principal do sistema
     * @param model
     * @return
     */
    @GetMapping("/calculate")
    public String viewHomePage(Model model) {
    	model.addAttribute("loan",new Loan());
        return "index";
    }
    
    /**
     * Método responsável por fazer o cálculo da parcela e enviar esses dados
     * para a página de contato
     * @param loan
     * @param result
     * @param model
     * @return
     */
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

    /**
     * Método responsável por carregar as informações vindas da tela de cálculo
     * @param monthlyValue
     * @param numberMonthly
     * @param type
     * @param carValue
     * @param model
     * @param loan
     * @param contact
     * @return
     */
    @GetMapping("/contact")
    public String viewContactPage(@RequestParam(value="monthlyValue",required=false) String monthlyValue, 
    		@RequestParam(value="numberMonthly",required=false) String numberMonthly, 
    		@RequestParam(value="type",required=false) String type,
    		@RequestParam(value="carValue",required=false) String carValue,
    		Model model, Loan loan, Contact contact ) {
    	model.addAttribute("loan", loan);
        return "contact";
    }
    
    /**
     * Método responsável por enviar os dados vindos da tela de cálculo mais 
     * os dados do contato e salvar eles em um arquivo csv
     * 
     * @param monthlyValue
     * @param numberMonthly
     * @param type
     * @param carValue
     * @param loan
     * @param contact
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/contact")
    public String saveContact(@RequestParam(value="monthlyValue",required=false) String monthlyValue, 
    		@RequestParam(value="numberMonthly",required=false) String numberMonthly, 
    		@RequestParam(value="type",required=false) String type,
    		@RequestParam(value="carValue",required=false) String carValue,
    		Loan loan, Contact contact, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "contact";
        }
        
        try {
			contactService.saveInFile(contact, monthlyValue, numberMonthly, type, carValue);
		} catch (IOException e) {
			log.error("Problemas ao gerar arquivo. Favor verificar se o caminho configurado existe na sua máquina.", e.getMessage());
		}

        return  "redirect:/calculate";
    }
}
