package com.example.calculator.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class CarLoan {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private TypeFinancing type;

    private Integer numberMonthlyInstallments;

    private BigDecimal totalValue;


}
