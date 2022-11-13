package com.example.calculator.controller;

import com.example.calculator.dto.Loan;
import com.example.calculator.model.TypeFinancing;
import com.example.calculator.service.InstallmentCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.expression.Lists;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarLoanController {

    private final InstallmentCalculatorService installmentCalculatorService;


    @GetMapping("/")
    public String viewHomePage(Model model) {

        model.addAttribute("carLoan", new Loan());
        return "index";
    }

    @PostMapping("/calculate")
    public String calculatePage(@ModelAttribute("carLoan") Loan carLoan, Model model) {

        BigDecimal montlyValue = installmentCalculatorService.calculate(carLoan);
        model.addAttribute("monthlyValueLabel", "Mensalidade: $" + montlyValue.setScale(2, RoundingMode.HALF_UP).toPlainString());
        return "index";
    }

    @GetMapping("/type/{typeFinancing}")
    public String getIntervals(@PathVariable("typeFinancing")TypeFinancing typeFinancing, Model model) {
        List<Integer> intervals = typeFinancing.getIntervals();
        model.addAttribute("intervals", intervals);
        return "index";
    }
}
