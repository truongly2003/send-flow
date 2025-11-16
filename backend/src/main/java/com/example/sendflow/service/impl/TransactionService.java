package com.example.sendflow.service.impl;

import com.example.sendflow.dto.response.PagedResponse;
import com.example.sendflow.dto.response.TransactionResponse;
import com.example.sendflow.entity.Transaction;
import com.example.sendflow.repository.TransactionRepository;
import com.example.sendflow.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public PagedResponse<TransactionResponse> getAllTransactions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        List<TransactionResponse> transactionResponses = transactions
                .stream()
                .map(transaction -> TransactionResponse.builder()
                        .id(transaction.getId())
                        .planName(transaction.getPlan().getName())
                        .userId(transaction.getUser().getId())
                        .userName(transaction.getUser().getName())
                        .email(transaction.getUser().getEmail())
                        .amount(transaction.getAmount())
                        .paymentMethod(transaction.getPaymentMethod())
                        .paymentStatus(transaction.getPaymentStatus())
                        .description(transaction.getDescription())
                        .createdAt(transaction.getCreatedAt())
                        .build()).toList();
        return PagedResponse.<TransactionResponse>builder()
                .pageNumber(transactions.getNumber())
                .pageSize(transactions.getSize())
                .totalElements(transactions.getTotalElements())
                .totalPages(transactions.getTotalPages())
                .content(transactionResponses)
                .build();
    }
}
