package com.bankInc.service;

import com.bankInc.entity.Transaction;
import com.bankInc.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionServices {

    private TransactionRepository transactionRepository;

    public TransactionServices(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Optional<Transaction> save (Transaction transaction) {
        return Optional.ofNullable(Optional.ofNullable(transactionRepository.save(transaction))
                .orElseThrow(() -> new RuntimeException("No se pudo guardar la transaccion ")));
    }
}
