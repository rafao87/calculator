package com.example.calculator.service;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.calculator.model.Contact;
import com.example.calculator.repository.ContactRepository;
import com.opencsv.CSVWriter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    
    @Value("${path.file}")
    private String pathFile;

    public Contact save(com.example.calculator.dto.Contact dto, String monthlyValue,
    		String numberMonthly, String type, String carValue) {
        Contact contact = Contact.builder().contact(dto.getContact()).name(dto.getName()).build();
        return contactRepository.save(contact);
    }
    
    public void saveInFile(com.example.calculator.dto.Contact contact, String monthlyValue,
    		String numberMonthly, String type, String carValue) throws IOException {
    	
    	String[] cabecalho = {"Nome", "Contato", "Vlr Parcelas", "Parcelas", "Tipo", "Vlr Viatura"};
    	
    	List<String[]> linhas = new ArrayList<>();
        linhas.add(new String[]{contact.getName(), contact.getContact(), monthlyValue, numberMonthly, type, carValue});

        Writer writer = Files.newBufferedWriter(Paths.get(pathFile));
        CSVWriter csvWriter = new CSVWriter(writer);

        csvWriter.writeNext(cabecalho);
        csvWriter.writeAll(linhas);

        csvWriter.flush();
        writer.close();
    }
}
