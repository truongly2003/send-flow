package com.example.sendflow.service;

import com.example.sendflow.dto.response.PagedResponse;
import com.example.sendflow.dto.response.TransactionResponse;

import java.util.List;

public interface ITransactionService {
    PagedResponse<TransactionResponse> getAllTransactions(int page, int size);
}
