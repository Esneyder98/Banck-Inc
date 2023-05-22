package com.bankInc.service;

import com.bankInc.Dto.CardDto;
import com.bankInc.entity.Card;
import com.bankInc.entity.Product;
import com.bankInc.entity.Transaction;
import com.bankInc.repository.CardRepository;
import com.bankInc.repository.ProductRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Optional.empty;

@Service
public class CardServices {
    private CardRepository cardRepository;
    private ProductRepository productRepository;

    private TransactionServices transactionServices;

    public CardServices(CardRepository cardRepository, ProductRepository productRepository,TransactionServices transactionServices) {
        this.cardRepository = cardRepository;
        this.productRepository = productRepository;
        this.transactionServices = transactionServices;
    }


    public Optional<CardDto> getFindByCardNumber(Long cardNumber) {
        return Optional.ofNullable(cardRepository.getBycardNumber(cardNumber)
                .orElseThrow(() -> new RuntimeException("Targeta no encontrada: " + cardNumber)));
    }

        public static long generarNumeroAleatorio() {
            long min = 1000000000L;
            long max = 9999999999L;

            return ThreadLocalRandom.current().nextLong(min, max + 1);
        }
        public Long concatNumber(Long number1, Long number2) {
            String num1 = Long.toString(number1);
            String num2 = Long.toString(number2);
            String concat = num1 + num2;
            return Long.parseLong(concat);
        }

    public static LocalDateTime getFutureDate() {
        LocalDateTime fechaActual = LocalDateTime.now();
        LocalDateTime fechaFutura = fechaActual.plusYears(3);
        return fechaFutura;
    }
    public Optional<Card> generateCardNumber(Card card, Long productId) {
        Long number = 0L;
        Optional<Card> saveCard = null;

        number= productRepository.findById(productId).map(product -> {
            return this.concatNumber(product.getProductId(),this.generarNumeroAleatorio());
        }).orElse(0L);

        if(number != 0L) {
            card.setCardNumber(number);
            card.setState(false);
            card.setExpirationDate(this.getFutureDate());
            card.setBalance(BigDecimal.valueOf(0));
            card.setProductId(productId);
            return saveCard.ofNullable(cardRepository.save(card));
        } else {
            return saveCard;
        }
    }

    public Optional<Card> updateStateCard (Card card,boolean state) {
        Long cardNumber= card.getCardNumber();
        return Optional.ofNullable(cardRepository.findByCardNumber(cardNumber)
                .map(
                        cardd -> {
                            cardd.setState(state);
                            return cardRepository.save(cardd);
                        }
                ).orElseThrow(() -> new RuntimeException("No se encontro la tarjeta a modificar " + cardNumber)));
    }

    public Optional<Card> updateStateCard(Long cardNumber,boolean state){
        return Optional.ofNullable(cardRepository.findByCardNumber(cardNumber)
                .map(
                        cardd -> {
                            cardd.setState(state);
                            return cardRepository.save(cardd);
                        }
                ).orElseThrow(() -> new RuntimeException("No se encontro la tarjeta a modificar " + cardNumber)));
    }

    public Optional<Card> topUpBalance(Card card){
        Long cardNumber= card.getCardNumber();
        BigDecimal amount= card.getBalance();
        Optional<Card> newTransaction = null;
        return Optional.ofNullable(cardRepository.findByCardNumber(cardNumber).map(
                cardd -> {
                    if(cardd.getState()){
                        BigDecimal preBalance = cardd.getBalance();
                        BigDecimal sum = preBalance.add(amount);
                        cardd.setBalance(sum);

                        Transaction transaction = new Transaction();
                        transaction.setCardId(cardd.getCardId());
                        transaction.setBalance(amount);
                        transaction.setTransactionType("RECARGA");
                        transaction.setDate(LocalDateTime.now());
                        transactionServices.save(transaction);
                        return cardRepository.save(cardd);
                    }
                    return null;
                }
        ).orElseThrow(() -> new RuntimeException("Tarjeta Bloqueada")));
    }

}

