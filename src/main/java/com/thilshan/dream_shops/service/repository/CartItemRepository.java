package com.thilshan.dream_shops.service.repository;

import com.thilshan.dream_shops.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    void deleteAllByCartId(Long id);
}
