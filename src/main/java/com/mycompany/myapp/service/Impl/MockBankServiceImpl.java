package com.mycompany.myapp.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.service.MockBankService;
import com.mycompany.myapp.service.api.dto.TransactionResponse;
import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class MockBankServiceImpl implements MockBankService {

    private final Logger log = LoggerFactory.getLogger(MockBankServiceImpl.class);

    @Value("classpath:mockData/Webank.json")
    Resource weBankMockResponse;

    @Value("classpath:mockData/SuperBank.json")
    Resource superBankMockResponse;

    @Value("classpath:mockData/CapitalBank.json")
    Resource capitalBankMockResponse;

    public TransactionResponse getMockWeBankResponse() {
        try (InputStream inputStream = weBankMockResponse.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, TransactionResponse.class);
        } catch (IOException e) {
            log.error("Error reading mock response: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public TransactionResponse getMockSuperBankResponse() {
        try (InputStream inputStream = superBankMockResponse.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, TransactionResponse.class);
        } catch (IOException e) {
            log.error("Error reading mock response: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public TransactionResponse getMockCapitalBankResponse() {
        try (InputStream inputStream = capitalBankMockResponse.getInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(inputStream, TransactionResponse.class);
        } catch (IOException e) {
            log.error("Error reading mock response: {}", e.getMessage());
            return null;
        }
    }
}
