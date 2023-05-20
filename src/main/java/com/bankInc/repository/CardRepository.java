package com.bankInc.repository;

import com.bankInc.Dto.CardDto;
import com.bankInc.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card,Long>, CrudRepository<Card,Long> {
    @Query("SELECT new com.bankInc.Dto.CardDto(c.cardId, c.cardNumber, c.balance)" +
            " FROM Card c " +
            " WHERE c.cardNumber =:card_number")
    Optional<CardDto> getBycardNumber(@Param("card_number") Long card_number);
}
