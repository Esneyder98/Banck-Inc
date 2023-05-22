package com.bankInc.Dto;

public class CardTransactionDto {
    private Long CardNumber;
    private Long TransactionId;

    public CardTransactionDto() {
    }

    public CardTransactionDto(Long cardNumber, Long transactionId) {
        CardNumber = cardNumber;
        TransactionId = transactionId;
    }

    public Long getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        CardNumber = cardNumber;
    }

    public Long getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(Long transactionId) {
        TransactionId = transactionId;
    }
}
