package com.bankInc.repository;

import com.bankInc.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long>, CrudRepository<Transaction,Long> {

    Optional<Transaction>findByTransactionIdAndTransactionType(Long transactionId,String TransactionType);
}
