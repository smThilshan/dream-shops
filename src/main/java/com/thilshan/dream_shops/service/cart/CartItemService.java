package com.thilshan.dream_shops.service.cart;

import com.thilshan.dream_shops.model.Cart;
import com.thilshan.dream_shops.model.CartItem;
import com.thilshan.dream_shops.model.Product;
import com.thilshan.dream_shops.service.exception.ResourceNotFoundException;
import com.thilshan.dream_shops.service.product.IProductService;
import com.thilshan.dream_shops.service.product.ProductService;
import com.thilshan.dream_shops.service.repository.CartItemRepository;
import com.thilshan.dream_shops.service.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ICartService cartService;
    private final IProductService productService;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //  1. Get the cart
        //  2. Get the product
        //  3. Check if the item already exists in the cart

        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst().orElse(new CartItem());
        if (cartItem.getId() == null) {
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setCart(cart);
            cart.getItems().add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        //  Update prices
        cartItem.setTotalPrice();
        cart.addItem(cartItem);

        //  Save the cart and cart item
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getItems()
                .stream().map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }

}
