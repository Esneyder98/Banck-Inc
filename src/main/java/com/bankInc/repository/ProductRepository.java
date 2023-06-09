package com.bankInc.repository;

import com.bankInc.entity.Card;
import com.bankInc.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>, CrudRepository<Product,Long> {

}
