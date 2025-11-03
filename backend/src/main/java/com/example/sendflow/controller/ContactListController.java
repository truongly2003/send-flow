package com.example.sendflow.controller;

import com.example.sendflow.dto.request.ContactListRequest;
import com.example.sendflow.dto.request.ContactRequest;
import com.example.sendflow.dto.response.ApiResponse;
import com.example.sendflow.dto.response.ContactListResponse;
import com.example.sendflow.dto.response.ContactResponse;
import com.example.sendflow.service.IContactListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact-list")
@RequiredArgsConstructor
public class ContactListController {
    private final IContactListService contactListService;
    // Lấy tất cả danh sách
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<ContactListResponse>>> getAllContactList(@PathVariable Long userId) {
        List<ContactListResponse> contactListResponses = contactListService.getAllContactList(userId);
        ApiResponse<List<ContactListResponse>> apiResponse = ApiResponse.<List<ContactListResponse>>builder()
                .code(2000)
                .message("success")
                .data(contactListResponses)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    // Tạo list contact mới
    @PostMapping
    public ResponseEntity<ApiResponse<ContactListResponse>> addContact(@RequestBody ContactListRequest contactListRequest) {
        ContactListResponse contactListResponse = contactListService.addContactList(contactListRequest);
        ApiResponse<ContactListResponse> apiResponse = ApiResponse.<ContactListResponse>builder()
                .code(2000)
                .message("Add list contact success")
                .data(contactListResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    // Cập nhật list contact
    @PutMapping("/{contactListId}")
    public ResponseEntity<ApiResponse<ContactListResponse>> updateContactList(@PathVariable Long contactListId,@RequestBody ContactListRequest contactListRequest) {
        ContactListResponse contactListResponse = contactListService.updateContactList(contactListId,contactListRequest);
        ApiResponse<ContactListResponse> apiResponse = ApiResponse.<ContactListResponse>builder()
                .code(2000)
                .message("Update list contact success")
                .data(contactListResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    // Xóa list contact
    @DeleteMapping("/{contactListId}")
    public ResponseEntity<ApiResponse<Void>> deleteContactList(@PathVariable Long contactListId) {
        contactListService.deleteContactList(contactListId);
        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .code(2000)
                .message("Delete contact list success")
                .data(null)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
