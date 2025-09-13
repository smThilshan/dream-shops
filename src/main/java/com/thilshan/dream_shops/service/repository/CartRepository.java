package com.thilshan.dream_shops.service.repository;

import com.thilshan.dream_shops.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;




public interface CartRepository extends JpaRepository<Cart,Long> {

    Cart findByUserId(long userId);

}
