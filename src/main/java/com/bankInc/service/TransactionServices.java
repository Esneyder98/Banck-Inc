package com.bankInc.service;

import com.bankInc.Dto.CardTransactionDto;
import com.bankInc.entity.Card;
import com.bankInc.entity.Transaction;
import com.bankInc.repository.CardRepository;
import com.bankInc.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        return Optional.ofNullable(cardRepository.findByCardNumber(card.getCardNumber()).map(
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
                    } else {
                        throw new MiExcepcion("saldo Insuficiente");
                    }
                }).orElseThrow(() -> new RuntimeException("CarNumber No existe")));
    }
    public Optional<Transaction>getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId);
    }

    public Optional<Transaction> purchaseAnulation(CardTransactionDto cardTransactionDto){
        Long cardNumber = cardTransactionDto.getCardNumber();
        Long transactionId = cardTransactionDto.getTransactionId();
        return Optional.ofNullable(cardRepository.findByCardNumber(cardNumber).map(
                card -> {
                    return transactionRepository.findByTransactionIdAndTransactionType(transactionId,"PURCHASE")
                            .map(transaction -> {
                                LocalDateTime fechaActual = LocalDateTime.now();
                                LocalDateTime dateTransaction = transaction.getDate();
                                long horasDiferencia = dateTransaction.until(fechaActual, ChronoUnit.HOURS);
                                if (horasDiferencia > 24) {
                                    throw new MiExcepcion("fecha de transaccion superior a 24 horas");
                                } else {
                                    BigDecimal sum = card.getBalance().add(transaction.getBalance());
                                    card.setBalance(sum);
                                    transaction.setTransactionType("Anulada");
                                    cardRepository.save(card);
                                    return transactionRepository.save(transaction);
                                }
                            }).orElseThrow(() -> new RuntimeException("Transaction no existe"));
                }).orElseThrow(() -> new RuntimeException("CarNumber no existe")));
    }
}
