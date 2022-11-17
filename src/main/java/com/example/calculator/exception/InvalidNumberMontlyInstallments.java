package com.example.calculator.exception;

public class InvalidNumberMontlyInstallments extends RuntimeException {

    private static final long serialVersionUID = 286036857315001442L;

	public InvalidNumberMontlyInstallments(String msg) {
        super(msg);
    }
}
