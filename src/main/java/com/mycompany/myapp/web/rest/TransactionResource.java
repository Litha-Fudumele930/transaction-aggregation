package com.mycompany.myapp.web.rest;

import brave.Tracer;
import com.mycompany.myapp.service.TransactionService;
import com.mycompany.myapp.service.api.dto.TransactionResponse;
import com.mycompany.myapp.web.api.TransactionsApiDelegate;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransactionResource implements TransactionsApiDelegate {

    private final Logger log = LoggerFactory.getLogger(TransactionResource.class);

    //    @Autowired
    //    Tracer tracer;
    @Autowired
    TransactionService transactionService;

    @Override
    public ResponseEntity<TransactionResponse> getAllTransactions(
        String user,
        LocalDate startDate,
        LocalDate endDate,
        String category,
        Integer page,
        Integer size
    ) {
        long startTime = System.currentTimeMillis();
        log.info("Retrieving {} transactions from {} to {}", user, startDate, endDate);

        TransactionResponse response = transactionService.findTransactions(user, startDate, endDate, category, page, size);

        log.info("Get transaction completed. Duration: {} ms", System.currentTimeMillis() - startTime);

        return ResponseEntity
            .status(HttpStatus.OK)
            //  .header("X-Trace-Id", tracer.currentSpan().context().traceIdString())
            .body(response);
    }
}
