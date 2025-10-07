package com.mycompany.myapp.service.Impl;

import com.mycompany.myapp.service.MockBankService;
import com.mycompany.myapp.service.TransactionService;
import com.mycompany.myapp.service.api.dto.TransactionListResponse;
import com.mycompany.myapp.service.api.dto.TransactionResponse;
import com.mycompany.myapp.service.api.dto.TransactionResponsePagination;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    MockBankService bankService;

    @Override
    public TransactionResponse findTransactions(
        String user,
        LocalDate startDate,
        LocalDate endDate,
        String category,
        Integer page,
        Integer size
    ) {
        //call bank services
        TransactionResponse weBankResponse = bankService.getMockWeBankResponse();
        TransactionResponse superBankResponse = bankService.getMockSuperBankResponse();
        TransactionResponse capitalBankResponse = bankService.getMockCapitalBankResponse();

        List<TransactionListResponse> allTransactions = new ArrayList<>();

        if (weBankResponse != null && weBankResponse.getTransactions() != null) {
            log.info("WeBank client returned status 200");
            allTransactions.addAll(weBankResponse.getTransactions());
        }
        if (superBankResponse != null && superBankResponse.getTransactions() != null) {
            log.info("Super Bank client returned status 200");
            allTransactions.addAll(superBankResponse.getTransactions());
        }
        if (capitalBankResponse != null && capitalBankResponse.getTransactions() != null) {
            log.info("Capital Bank client returned status 200");
            allTransactions.addAll(capitalBankResponse.getTransactions());
        }

        // Normalize empty strings to null
        String customerName = (user != null && !user.trim().isEmpty()) ? user.trim() : null;
        String categoryName = (category != null && !category.trim().isEmpty()) ? category.trim() : null;

        // filter on customer name, category and date range
        List<TransactionListResponse> filteredResults = allTransactions
            .stream()
            .filter(c -> {
                boolean userMatch = customerName == null || c.getUsername().equalsIgnoreCase(user);
                boolean categoryMatch = categoryName == null || c.getCategory().equalsIgnoreCase(category);

                LocalDate txDate = convertDate(c.getTransactionDate());

                boolean afterStart = startDate == null || !txDate.isBefore(startDate);
                boolean beforeEnd = endDate == null || !txDate.isAfter(endDate);
                return categoryMatch && userMatch && afterStart && beforeEnd;
            })
            .collect(Collectors.toList());

        log.debug("filtered results : {}", filteredResults);

        // pagination on results
        int safePage = page != null ? page : 0;
        int safeSize = size != null ? size : 10;
        int total = filteredResults.size();
        int start = safePage * safeSize;
        int end = Math.min(start + safeSize, total);

        List<TransactionListResponse> pagedResults = (start < total) ? filteredResults.subList(start, end) : Collections.emptyList();

        TransactionResponse paginatedResponse = new TransactionResponse();
        paginatedResponse.setTransactions(pagedResults);

        // set response
        TransactionResponsePagination pagination = new TransactionResponsePagination();
        pagination.setTotalItems(BigDecimal.valueOf(total));
        pagination.setPage(BigDecimal.valueOf(safePage));
        pagination.setSize(BigDecimal.valueOf(safeSize));
        pagination.setTotalPages(BigDecimal.valueOf((int) Math.ceil((double) total / safeSize)));

        paginatedResponse.setPagination(pagination);

        return paginatedResponse;
    }

    public LocalDate convertDate(String dateToConvert) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateToConvert);
        return zonedDateTime.toLocalDate();
    }
}
