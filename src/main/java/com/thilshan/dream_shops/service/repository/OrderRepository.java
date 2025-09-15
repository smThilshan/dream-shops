package com.thilshan.dream_shops.service.repository;

import com.thilshan.dream_shops.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order, Long> {
}
