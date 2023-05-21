package com.bankInc.controller;

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
                    }).orElseThrow(() -> new RuntimeException("carNumber no encontrado"));
        } catch (Exception e) {
            errorMap.put("error", e.getMessage());
            return  new ResponseEntity<>(errorMap,HttpStatus.NOT_FOUND);
        }
    }

}
