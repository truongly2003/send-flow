package com.example.sendflow.mapper;

import com.example.sendflow.dto.request.ContactRequest;
import com.example.sendflow.dto.response.ContactListResponse;
import com.example.sendflow.dto.response.ContactResponse;
import com.example.sendflow.entity.Contact;
import com.example.sendflow.entity.ContactList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    @Mapping(target = "contactList.id" ,source = "contactListId")
    Contact toContact(ContactRequest contactRequest);

    @Mapping(target = "contactListId" ,source = "contactList.id")
    ContactResponse toContactResponse(Contact contact);
    void updateContactRequest(ContactRequest request, @MappingTarget Contact existingContact);
}
