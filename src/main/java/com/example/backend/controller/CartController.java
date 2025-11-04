package com.example.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.Cart;
import com.example.backend.model.CartItem;
import com.example.backend.service.CartService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = {
        "https://ecom-my-shop-frontend.vercel.app/",
        "http://localhost:5173"
})
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable String userId) {
        return cartService.getCartByUser(userId);
    }

    @PostMapping("/{userId}")
    public Cart addToCart(@PathVariable String userId, @RequestBody CartItem item) {
        return cartService.addToCart(userId, item);
    }

    @DeleteMapping("/{userId}")
    public void clearCart(@PathVariable String userId) {
        cartService.clearCart(userId);
    }

    @DeleteMapping("/{userId}/{productId}")
    public Cart removeItem(@PathVariable String userId, @PathVariable String productId) {
        return cartService.removeItem(userId, productId);
    }
}
