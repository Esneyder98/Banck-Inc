package com.bankInc.controller;

import com.bankInc.Dto.CardTransactionDto;
import com.bankInc.entity.Card;
import com.bankInc.entity.Transaction;
import com.bankInc.service.TransactionServices;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
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
    @ApiOperation(value = "Registrar transacciones de compra")
    @ApiResponses({
            @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 404, message = "TARJETA NOT_FOUND"),
            @ApiResponse(code = 500,message = "INTERNAL SERVER ERROR")
    })
    public ResponseEntity<?> activeCard(@ApiParam(value = "Numero de tarjeta y valor a pagar",required = true)@RequestBody Card card) {
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
    @ApiOperation(value = "Consultar transacciones de compra")
    @ApiResponses({
            @ApiResponse(code = 201, message = "OK"),
            @ApiResponse(code = 404, message = "Transaction NOT_FOUND"),
            @ApiResponse(code = 500,message = "INTERNAL SERVER ERROR")
    })
    public ResponseEntity<?> getTransactionById(@ApiParam(value = "Id de transaccion",required = true,example = "2")@PathVariable Long transactionId){
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
    @ApiOperation(value = "Anular transacciones de compra menos de 24 horas")
    @ApiResponses({
            @ApiResponse(code = 201, message = "OK"),
            @ApiResponse(code = 404, message = "Transaction NOT_FOUND"),
            @ApiResponse(code = 500,message = "INTERNAL SERVER ERROR")
    })
    public ResponseEntity<?> purchaseAnulation(@ApiParam(value = "Número de tarjeta y id de transaccion")@RequestBody CardTransactionDto cardTransactionDto){
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
