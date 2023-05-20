package com.bankInc.service;

import com.bankInc.Dto.CardDto;
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

    public Optional<CardDto>getFindByCardId(Long cardId) {
        return cardRepository.getBycardNumber(cardId);
    }
}
