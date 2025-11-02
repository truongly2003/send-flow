package com.example.sendflow.service;

import com.example.sendflow.dto.request.ContactListRequest;
import com.example.sendflow.dto.response.ContactListResponse;

import java.util.List;

public interface IContactListService {
    List<ContactListResponse> getAllContactList(Long userId);
    ContactListResponse getContactList(Long contactListId);
    ContactListResponse addContactList(ContactListRequest contactListRequest);
    ContactListResponse updateContactList(Long contactListId,ContactListRequest contactListRequest);
    void deleteContactList(Long contactListId);
}
