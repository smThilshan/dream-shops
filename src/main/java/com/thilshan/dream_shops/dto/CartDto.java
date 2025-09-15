package com.thilshan.dream_shops.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartDto {
    private long cartId;
    private Set<CartItemDto> items;
    private BigDecimal totalAmount;
}
