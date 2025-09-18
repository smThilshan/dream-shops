package com.thilshan.dream_shops.dto;

import com.thilshan.dream_shops.model.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDto   product;
}
