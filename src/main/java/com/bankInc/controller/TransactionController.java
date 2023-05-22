package com.bankInc.controller;

import com.bankInc.Dto.CardTransactionDto;
import com.bankInc.entity.Card;
import com.bankInc.entity.Transaction;
import com.bankInc.service.TransactionServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private TransactionServices transactionServices;

    public TransactionController(TransactionServices transactionServices) {
        this.transactionServices = transactionServices;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> activeCard(@RequestBody Card card) {
        HashMap<String,String> errorMap = new HashMap<String,String>();
        try {
            return transactionServices.savePurchase(card).map(
                    newTransaction -> {
                        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
                    }).orElseThrow();
        } catch (Exception e) {
            errorMap.put("error", e.getMessage());
            return  new ResponseEntity<>(errorMap,HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long transactionId){
        HashMap<String,String> errorMap = new HashMap<String,String>();
        try {
            return transactionServices.getTransactionById(transactionId).map(
                    transaction ->{
                        return new ResponseEntity<>(transaction,HttpStatus.OK);
                    }).orElseThrow(() -> new RuntimeException("transacción no encontrada"));
        }catch (Exception e){
            errorMap.put("error", e.getMessage());
            return new ResponseEntity<>(errorMap,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/anulation")
    public ResponseEntity<?> purchaseAnulation(@RequestBody CardTransactionDto cardTransactionDto){
        HashMap<String,String> errorMap = new HashMap<String,String>();
        try{
            return transactionServices.purchaseAnulation(cardTransactionDto).map(
                    transaction -> {
                        return new ResponseEntity<>(transaction,HttpStatus.OK);
                    }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch (Exception e){
            errorMap.put("error", e.getMessage());
            return new ResponseEntity<>(errorMap,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
