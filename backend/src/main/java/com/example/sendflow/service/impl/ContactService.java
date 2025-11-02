package com.example.sendflow.service.impl;

import com.example.sendflow.dto.request.ContactRequest;
import com.example.sendflow.dto.response.ContactResponse;
import com.example.sendflow.entity.Contact;
import com.example.sendflow.exception.ResourceNotFoundException;
import com.example.sendflow.mapper.ContactMapper;
import com.example.sendflow.repository.ContactRepository;
import com.example.sendflow.service.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactService implements IContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    @Override
    public List<ContactResponse> getAllContacts(Long contactListId) {
        List<Contact> contacts=contactRepository.findAllByContactListId(contactListId);
        return contacts.stream()
                .map(contactMapper::toContactResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ContactResponse getContactById(Long contactId) {
        Contact contact=contactRepository.findById(contactId).orElseThrow(()->new ResourceNotFoundException("Contact not found"));
        return contactMapper.toContactResponse(contact);
    }

    @Override
    public ContactResponse addContact(ContactRequest contactRequest) {
        Contact contact=contactMapper.toContact(contactRequest);
        Contact savedContact=contactRepository.save(contact);
        return contactMapper.toContactResponse(savedContact);
    }

    @Override
    public ContactResponse updateContact(Long contactId, ContactRequest contactRequest) {
        Contact existingContact=contactRepository.findById(contactId).orElseThrow(()->new ResourceNotFoundException("Contact not found"));
        contactMapper.updateContactRequest(contactRequest, existingContact);
        Contact updatedContact=contactRepository.save(existingContact);
        return contactMapper.toContactResponse(updatedContact);
    }

    @Override
    public void deleteContact(Long contactId) {
        Contact existingContact=contactRepository.findById(contactId).orElseThrow(()->new ResourceNotFoundException("Contact not found"));
        contactRepository.delete(existingContact);
    }
}
