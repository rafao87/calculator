package com.example.calculator.controller;

import com.example.calculator.dto.Contact;
import com.example.calculator.dto.Loan;
import com.example.calculator.exception.InvalidNumberMontlyInstallments;
import com.example.calculator.model.TypeFinancing;
import com.example.calculator.service.ContactService;
import com.example.calculator.service.InstallmentCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.expression.Lists;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarLoanController {

    private final InstallmentCalculatorService installmentCalculatorService;
    private final ContactService contactService;


    @GetMapping("/calculate")
    public String viewHomePage(Loan loan) {

        return "index";
    }

    @PostMapping("/calculate")
    public String calculatePage(@Valid Loan loan, Model model, BindingResult result) {

        if (result.hasErrors()) {
            return "index";
        }


        BigDecimal montlyValue = installmentCalculatorService.calculate(loan);
        model.addAttribute("monthlyValueLabel", "Mensalidade: $" + montlyValue.setScale(2, RoundingMode.HALF_UP).toPlainString());
        return "redirect:/contact";
    }
    @GetMapping("/type/{typeFinancing}")
    public String getIntervals(@PathVariable("typeFinancing")TypeFinancing typeFinancing, Model model) {
        List<Integer> intervals = typeFinancing.getIntervals();
        model.addAttribute("intervals", intervals);
        return "index";
    }

    @GetMapping("/contact")
    public String viewContactPage(Contact contact) {

        return "contact";
    }
    @PostMapping("/contact")
    public String saveContact(@Valid Contact contact, Model model, BindingResult result) {

        if (result.hasErrors()) {
            return "contact";
        }

        contactService.save(contact);

        return  "redirect:/calculate";
    }
}
