package com.example.calculator.repository;

import com.example.calculator.model.CarLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarLoanRepository extends JpaRepository<CarLoan, Long> {
}
