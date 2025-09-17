package com.thilshan.dream_shops.service.cart;

import com.thilshan.dream_shops.model.Cart;
import com.thilshan.dream_shops.model.CartItem;
import com.thilshan.dream_shops.model.User;
import com.thilshan.dream_shops.service.exception.ResourceNotFoundException;
import com.thilshan.dream_shops.service.repository.CartItemRepository;
import com.thilshan.dream_shops.service.repository.CartRepository;
import com.thilshan.dream_shops.service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id " + id));
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        return getCart(id).getTotalAmount();
    }

    @Override
    public Long initializeNewCart() {
        Cart newCart = new Cart();
        Long newCartId = cartIdGenerator.incrementAndGet();
        newCart.setId(newCartId);
        return cartRepository.save(newCart).getId();
    }



    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
