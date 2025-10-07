package com.mycompany.myapp.service;

import com.mycompany.myapp.service.api.dto.TransactionResponse;
import java.time.LocalDate;

public interface TransactionService {
    TransactionResponse findTransactions(String user, LocalDate startDate, LocalDate endDate, String category, Integer page, Integer size);
}
