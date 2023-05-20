package com.bankInc.service;

import com.bankInc.entity.Card;
import com.bankInc.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardServices {
    private CardRepository cardRepository;

    public CardServices(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Optional<Card>getFindByCardId(Long cardId) {
        return cardRepository.findById(cardId);
    }
}
