package com.example.sendflow.mapper;

import com.example.sendflow.dto.request.ContactListRequest;
import com.example.sendflow.dto.response.ContactListResponse;
import com.example.sendflow.entity.ContactList;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContactListMapper {
    ContactList toContactList(ContactListRequest contactListRequest);
    ContactListResponse toContactListResponse(ContactList contactList);

    void updateContactListRequest(ContactListRequest request, @MappingTarget ContactList existingContactList);
}
