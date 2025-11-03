package com.example.sendflow.mapper;

import com.example.sendflow.dto.request.ContactListRequest;
import com.example.sendflow.dto.response.ContactListResponse;
import com.example.sendflow.entity.ContactList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContactListMapper {

    @Mapping(target = "user.id", source = "userId")
    ContactList toContactList(ContactListRequest contactListRequest);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "totalContacts", expression = "java(contactList.getContacts() != null ? contactList.getContacts().size() : 0)")
    ContactListResponse toContactListResponse(ContactList contactList);

    void updateContactListRequest(ContactListRequest request, @MappingTarget ContactList existingContactList);
}
