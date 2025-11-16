package com.example.sendflow.controller;

import com.example.sendflow.dto.request.ContactRequest;
import com.example.sendflow.dto.response.ApiResponse;
import com.example.sendflow.dto.response.ContactResponse;
import com.example.sendflow.dto.response.PagedResponse;
import com.example.sendflow.service.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {
    private final IContactService contactService;

    // Lấy tất cả liên hệ
    @GetMapping("/list/{listContactId}")
    public ResponseEntity<ApiResponse<PagedResponse<ContactResponse>>> getAllContacts(
            @PathVariable Long listContactId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedResponse<ContactResponse> contactResponses = contactService.getAllContacts(listContactId,page,size);
        ApiResponse<PagedResponse<ContactResponse>> apiResponse = ApiResponse.<PagedResponse<ContactResponse>>builder()
                .code(2000)
                .message("success")
                .data(contactResponses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    //    Tạo mới liên hệ
    @PostMapping
    public ResponseEntity<ApiResponse<ContactResponse>> addContact(@RequestBody ContactRequest contactRequest) {
        ContactResponse contactResponses = contactService.addContact(contactRequest);
        ApiResponse<ContactResponse> apiResponse = ApiResponse.<ContactResponse>builder()
                .code(2000)
                .message("Add contact success")
                .data(contactResponses)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    // Lấy liên hệ theo id
    @GetMapping("/{contactId}")
    public ResponseEntity<ApiResponse<ContactResponse>> getContact(@PathVariable Long contactId) {
        ContactResponse contactResponses = contactService.getContactById(contactId);
        ApiResponse<ContactResponse> apiResponse = ApiResponse.<ContactResponse>builder()
                .code(2000)
                .message("success")
                .data(contactResponses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    //    Cập nhật contact
    @PutMapping("/{contactId}")
    public ResponseEntity<ApiResponse<ContactResponse>> updateContact(@PathVariable Long contactId, @RequestBody ContactRequest contactRequest) {
        ContactResponse contactResponses = contactService.updateContact(contactId, contactRequest);
        ApiResponse<ContactResponse> apiResponse = ApiResponse.<ContactResponse>builder()
                .code(2000)
                .message("Update contact success")
                .data(contactResponses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    // Xóa contact
    @DeleteMapping("/{contactId}")
    public ResponseEntity<ApiResponse<Void>> deleteContact(@PathVariable Long contactId) {
        contactService.deleteContact(contactId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code(2000)
                .message("Delete contact success")
                .data(null)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
