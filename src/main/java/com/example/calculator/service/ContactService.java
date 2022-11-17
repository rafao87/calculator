package com.example.calculator.service;

import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.calculator.dto.Loan;
import com.example.calculator.model.Contact;
import com.example.calculator.repository.ContactRepository;
import com.opencsv.CSVWriter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public Contact save(com.example.calculator.dto.Contact dto) {
        Contact contact = Contact.builder().contact(dto.getContact()).name(dto.getName()).build();
        return contactRepository.save(contact);
    }
    
    public void saveInFile(com.example.calculator.dto.Contact contact, Loan loan/*, String monthlyValue,
    		String numberMonthly, String type, String carValue*/) throws IOException {
    	
    	String[] cabecalho = {"Nome", "Contato", "Vlr Parcelas", "N° de Parcelas", "Tipo", "Vlr Viatura"};
    	
    	List<String[]> linhas = new ArrayList<>();
        /*linhas.add(new String[]{contact.getName(), contact.getContact(), monthlyValue, 
        		numberMonthly, type, carValue});*/
    	linhas.add(new String[]{contact.getName(), contact.getContact()/*, 
    			loan.getFinancing().getNumberMonthlyInstallments().toString(),
    			loan.getFinancing().getType().getNameTypeFinancing(), loan.getFinancing().getCarValue().toPlainString()*/});

        Writer writer = Files.newBufferedWriter(Paths.get("C:\\Miranda\\desafio\\contato.csv"));
        CSVWriter csvWriter = new CSVWriter(writer);

        csvWriter.writeNext(cabecalho);
        csvWriter.writeAll(linhas);

        csvWriter.flush();
        writer.close();
    }
}
