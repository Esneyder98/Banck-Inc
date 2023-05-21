package com.bankInc.service;

import com.bankInc.entity.Card;
import com.bankInc.entity.Transaction;
import com.bankInc.repository.CardRepository;
import com.bankInc.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionServices {

    private TransactionRepository transactionRepository;
    private CardRepository cardRepository;


    public TransactionServices(TransactionRepository transactionRepository,CardRepository cardRepository) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
    }

    public Optional<Transaction> save (Transaction transaction) {
        return Optional.ofNullable(Optional.ofNullable(transactionRepository.save(transaction))
                .orElseThrow(() -> new RuntimeException("No se pudo guardar la transaccion ")));
    }

    public Optional<Transaction> savePurchase(Card card) {
        return cardRepository.findByCardNumber(card.getCardNumber()).map(
                cardd -> {
                    BigDecimal result = cardd.getBalance().subtract(card.getBalance());
                    if (result.compareTo(BigDecimal.ZERO) > 0) {
                        Transaction transactionSave = new Transaction();
                        transactionSave.setCardId(cardd.getCardId());
                        transactionSave.setBalance(card.getBalance());
                        transactionSave.setTransactionType("PURCHASE");
                        transactionSave.setDate(LocalDateTime.now());
                        cardd.setBalance(result);
                        cardRepository.save(cardd);
                        return transactionRepository.save(transactionSave);
                    }
                    return null;
                });

    }
}
