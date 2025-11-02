package com.example.sendflow.mapper;

import com.example.sendflow.dto.request.ContactRequest;
import com.example.sendflow.dto.response.ContactListResponse;
import com.example.sendflow.dto.response.ContactResponse;
import com.example.sendflow.entity.Contact;
import com.example.sendflow.entity.ContactList;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    Contact toContact(ContactRequest contactRequest);
    ContactResponse toContactResponse(Contact contact);
    void updateContactRequest(ContactRequest request, @MappingTarget Contact existingContact);
}
