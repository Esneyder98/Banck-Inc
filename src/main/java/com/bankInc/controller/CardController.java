package com.bankInc.controller;

import com.bankInc.entity.Card;
import com.bankInc.entity.Product;
import com.bankInc.repository.ProductRepository;
import com.bankInc.service.CardServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/card")
public class CardController {
    private CardServices cardServices;

    public CardController(CardServices cardServices) {
        this.cardServices = cardServices;
    }

    @GetMapping("/balance/{id}")
    public ResponseEntity<?> getCard(@PathVariable("id") String id){
        try {
            Long cardId = Long.parseLong(id);
            return cardServices.getFindByCardId(cardId)
                    .map(card->{
                        return new ResponseEntity<>(card,HttpStatus.OK);
                    }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{productId}/number")
    public ResponseEntity<?> generateCardNumber(@RequestBody Card card,@PathVariable Long productId){
        try {
            return cardServices.generateCardNumber(card,productId).map(cardd ->{
                        return new ResponseEntity<Card>(cardd, HttpStatus.CREATED);
                    }
            ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/endroll")
    public ResponseEntity<?>activeCard(@RequestBody Card card){
        try{
            return cardServices.updateStateCard(card,true).map(
                    cardResponse ->{
                        return new ResponseEntity<>(cardResponse,HttpStatus.OK);
                    }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{cardNumber}")
    public ResponseEntity<?>Blockcard(@PathVariable Long cardNumber){
        try{
            return cardServices.updateStateCard(cardNumber,false).map(
                    cardResponse ->{
                        return new ResponseEntity<>(cardResponse,HttpStatus.OK);
                    }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/balance")
    public ResponseEntity<?>topUpBalance(@RequestBody Card card){
        try {
            return cardServices.topUpBalance(card).map(cardBalance->{
                return new ResponseEntity<>(cardBalance,HttpStatus.OK);
            }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
