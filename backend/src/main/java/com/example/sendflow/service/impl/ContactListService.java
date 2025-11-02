package com.example.sendflow.service.impl;

import com.example.sendflow.dto.request.ContactListRequest;
import com.example.sendflow.dto.response.ContactListResponse;
import com.example.sendflow.entity.ContactList;
import com.example.sendflow.exception.ResourceNotFoundException;
import com.example.sendflow.mapper.ContactListMapper;
import com.example.sendflow.repository.ContactListRepository;
import com.example.sendflow.service.IContactListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactListService implements IContactListService {

    private final ContactListRepository contactListRepository;
    private final ContactListMapper contactListMapper;

    @Override
    public List<ContactListResponse> getAllContactList(Long userId) {
        List<ContactList> contactLists=contactListRepository.findAllByUserId(userId);
        return contactLists.stream().map(contactListMapper::toContactListResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ContactListResponse getContactList(Long contactListId) {
        return null;
    }

    @Override
    public ContactListResponse addContactList(ContactListRequest contactListRequest) {
        ContactList contactList=contactListMapper.toContactList(contactListRequest);
        ContactList newContactList=contactListRepository.save(contactList);
        return contactListMapper.toContactListResponse(newContactList);
    }

    @Override
    public ContactListResponse updateContactList(Long contactListId, ContactListRequest contactListRequest) {
        ContactList existingContactList=contactListRepository.findById(contactListId).orElseThrow(()->new ResourceNotFoundException("Contact list not found"));
        contactListMapper.updateContactListRequest(contactListRequest, existingContactList);
        ContactList updatedContactList=contactListRepository.save(existingContactList);
        return contactListMapper.toContactListResponse(updatedContactList);
    }

    @Override
    public void deleteContactList(Long contactListId) {
        ContactList contactList=contactListRepository.findById(contactListId).orElseThrow(()->new ResourceNotFoundException("Contact list not found"));
        contactListRepository.delete(contactList);
    }
}
