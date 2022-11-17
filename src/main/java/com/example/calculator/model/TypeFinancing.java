package com.example.calculator.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum TypeFinancing {

    INTERNAL("Interno"),
    EXTERNAL("Externo");
	
	private String nameTypeFinancing;
	
	TypeFinancing(String nameTypeFinancing) {
		this.nameTypeFinancing = nameTypeFinancing;
	}

	 public String getNameTypeFinancing() {
        return nameTypeFinancing;
    }
}
