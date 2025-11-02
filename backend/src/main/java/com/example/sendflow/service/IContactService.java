package com.example.sendflow.service;

import com.example.sendflow.dto.request.ContactRequest;
import com.example.sendflow.dto.response.ContactResponse;

import java.util.List;

public interface IContactService {
    List<ContactResponse> getAllContacts(Long contactListId);
    ContactResponse getContactById(Long contactId);
    ContactResponse addContact(ContactRequest contactRequest);
    ContactResponse updateContact(Long contactId, ContactRequest contactRequest);
    void deleteContact(Long contactId);
}
