package com.bankInc.controller;

import com.bankInc.entity.Card;
import com.bankInc.entity.Product;
import com.bankInc.repository.ProductRepository;
import com.bankInc.service.CardServices;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    @ApiOperation(value = "Consultar saldo de tarjeta")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Product not Found"),
            @ApiResponse(code = 500,message = "INTERNAL SERVER ERROR")
    })
    public ResponseEntity<?> getCard(@ApiParam(value = "numero de tarjeta",required = true,example = "1020302340506759")@PathVariable("id") String id){
        HashMap<String,String> errorMap = new HashMap<String,String>();
        try {
            Long cardNumber = Long.parseLong(id);
            return cardServices.getFindByCardNumber(cardNumber)
                    .map(card->{
                        return new ResponseEntity<>(card,HttpStatus.OK);
                    }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch(Exception e){
            errorMap.put("error",e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{productId}/number")
    @ApiOperation(value = "Generar numero de tarjeta")
    @ApiResponses({
            @ApiResponse(code = 201, message = "CREATED"),
            @ApiResponse(code = 404, message = "TARJETA NOT_FOUND"),
            @ApiResponse(code = 500,message = "INTERNAL SERVER ERROR")
    })
    public ResponseEntity<?> generateCardNumber(@ApiParam(value = "datos de la tarjeta a crear",required = true) @RequestBody Card card, @ApiParam(value = "Id del producto",required = true,example = "102030")@PathVariable Long productId){
        HashMap<String,String> errorMap = new HashMap<String,String>();
        try {
            return cardServices.generateCardNumber(card,productId).map(cardd ->{
                        return new ResponseEntity<Card>(cardd, HttpStatus.CREATED);
                    }
            ).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch(Exception e){
            errorMap.put("error", e.getMessage());
            return new ResponseEntity<>(errorMap,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/endroll")
    @ApiOperation(value = "Activar  tarjeta")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "TARJETA NOT_FOUND"),
            @ApiResponse(code = 500,message = "INTERNAL SERVER ERROR")
    })
    public ResponseEntity<?>activeCard(@ApiParam(value="Numero de la tarjeta a activar",required = true)@RequestBody Card card){
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
    @ApiOperation(value = "Bloquear  tarjeta")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "TARJETA NOT_FOUND"),
            @ApiResponse(code = 500,message = "INTERNAL SERVER ERROR")
    })
    public ResponseEntity<?>Blockcard(@ApiParam(value = "Numero de tarjeta a bloquear",required = true,example = "1020302340506759") @PathVariable Long cardNumber){
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
    @ApiOperation(value = "Recargar saldo")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "TARJETA NOT_FOUND"),
            @ApiResponse(code = 500,message = "INTERNAL SERVER ERROR")
    })
    public ResponseEntity<?>topUpBalance(@ApiParam(value = "numero de tarjeta y valor a recargar o balance",required = true)@RequestBody Card card){
        HashMap<String,String> errorMap = new HashMap<String,String>();
        try {
            return cardServices.topUpBalance(card).map(cardBalance->{
                return new ResponseEntity<>(cardBalance,HttpStatus.OK);
            }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }catch (Exception e){
            errorMap.put("error",e.getMessage());
            return new ResponseEntity<>(errorMap,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
