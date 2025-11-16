package com.example.sendflow.controller;

import com.example.sendflow.dto.response.PagedResponse;
import com.example.sendflow.dto.response.TransactionResponse;
import com.example.sendflow.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final ITransactionService transactionService;
    @GetMapping
    public ResponseEntity<PagedResponse<TransactionResponse>> getAllTransactions(
            @RequestParam(defaultValue = "0" ) int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(transactionService.getAllTransactions(page, size));
    }
}
