package com.example.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.model.Cart;
import com.example.backend.model.CartItem;
import com.example.backend.repository.CartRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    // ✅ Get or create user cart
    public Cart getCartByUser(String userId) {
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cart.setItems(new ArrayList<>());
            cartRepository.save(cart);
        }

        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }

        return cart;
    }

    // ✅ Add item to cart
    public Cart addToCart(String userId, CartItem item) {
        Cart cart = getCartByUser(userId);

        System.out.println("🛒 Adding to cart: user = " + userId);
        System.out.println("📦 productId received = " + item.getProductId());

        // ✅ Safety: set default quantity
        if (item.getQuantity() <= 0) {
            item.setQuantity(1);
        }

        List<CartItem> items = cart.getItems();
        boolean exists = false;

        for (CartItem existing : items) {
            if (existing.getProductId() != null &&
                existing.getProductId().equals(item.getProductId())) {

                existing.setQuantity(existing.getQuantity() + item.getQuantity());
                exists = true;
                break;
            }
        }

        if (!exists) {
            items.add(item);
        }

        cartRepository.save(cart);
        return cart;
    }

    // ✅ Remove product
    public Cart removeItem(String userId, String productId) {
        Cart cart = getCartByUser(userId);
        cart.getItems().removeIf(item -> productId.equals(item.getProductId()));
        cartRepository.save(cart);
        return cart;
    }

    // ✅ Clear cart
    public void clearCart(String userId) {
        Cart cart = getCartByUser(userId);
        cart.setItems(new ArrayList<>());
        cartRepository.save(cart);
    }
}
