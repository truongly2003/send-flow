package com.example.sendflow.service;

import com.example.sendflow.dto.request.ContactRequest;
import com.example.sendflow.dto.response.ContactResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IContactService {
    Page<ContactResponse> getAllContacts(Long contactListId, Pageable pageable);
    ContactResponse getContactById(Long contactId);
    ContactResponse addContact(ContactRequest contactRequest);
    ContactResponse updateContact(Long contactId, ContactRequest contactRequest);
    void deleteContact(Long contactId);
}
