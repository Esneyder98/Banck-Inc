package com.bankInc.service;

import com.bankInc.Dto.CardDto;
import com.bankInc.entity.Card;
import com.bankInc.entity.Product;
import com.bankInc.repository.CardRepository;
import com.bankInc.repository.ProductRepository;
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

    public CardServices(CardRepository cardRepository, ProductRepository productRepository) {
        this.cardRepository = cardRepository;
        this.productRepository = productRepository;
    }


    public Optional<CardDto> getFindByCardId(Long cardId) {
        return cardRepository.getBycardNumber(cardId);
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
                ).orElseThrow(() -> new RuntimeException("No se encontro la targeta a modificar " + cardNumber)));
    }
}

