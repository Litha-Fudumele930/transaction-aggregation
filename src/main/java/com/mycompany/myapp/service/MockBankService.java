package com.mycompany.myapp.service;

import com.mycompany.myapp.service.api.dto.TransactionResponse;

public interface MockBankService {
    TransactionResponse getMockWeBankResponse();

    TransactionResponse getMockSuperBankResponse();

    TransactionResponse getMockCapitalBankResponse();
}
