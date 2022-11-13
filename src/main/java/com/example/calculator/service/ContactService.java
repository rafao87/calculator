package com.example.calculator.service;

import com.example.calculator.model.Contact;
import com.example.calculator.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public Contact save(com.example.calculator.dto.Contact dto) {
        Contact contact = Contact.builder().contact(dto.getName()).name(dto.getName()).build();
        return contactRepository.save(contact);
    }
}
