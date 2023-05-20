package com.bankInc.repository;

import com.bankInc.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card,Long>, CrudRepository<Card,Long> {

}
