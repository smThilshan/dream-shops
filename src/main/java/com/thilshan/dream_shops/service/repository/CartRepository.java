package com.thilshan.dream_shops.service.repository;

import com.thilshan.dream_shops.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.ScopedValue;


public interface CartRepository extends JpaRepository<Cart,Long> {

    <T> ScopedValue<T> deleteAllByCartId(Long id);
}
