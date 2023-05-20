package com.bankInc.controller;

import com.bankInc.entity.Card;
import com.bankInc.service.CardServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class CardController {
    private CardServices cardServices;

    public CardController(CardServices cardServices) {
        this.cardServices = cardServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCard(@PathVariable("id") Long id){
        try {
            return cardServices.getFindByCardId(id)
                    .map(card->{
                        return new ResponseEntity<>(card,HttpStatus.OK);
                    }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
