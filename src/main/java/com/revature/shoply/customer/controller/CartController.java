package com.revature.shoply.customer.controller;

import com.revature.shoply.models.Cart;
import com.revature.shoply.customer.service.CartService;
import com.revature.shoply.customer.DTOs.IncomingCartItemDTO;
import com.revature.shoply.login.service.LoginService;


import java.util.UUID;

import com.revature.shoply.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/cart")
public class CartController {
    private final CartService cartService;

    private final JwtUtil jwtUtil;

    @Autowired
    public CartController(CartService cartService, JwtUtil jwtUtil) {
        this.cartService = cartService;
        this.jwtUtil = jwtUtil;
    }


    @DeleteMapping("/RemoveItem/{cartItemId}")
    public ResponseEntity<String> RemoveItemFromCart(@PathVariable UUID cartItemId){
        cartService.DeleteCartItemById(cartItemId);
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<Cart> addToCart(@RequestBody IncomingCartItemDTO cartItemDTO, @RequestHeader("Authorization") String token){
        cartItemDTO.setUserId(UUID.fromString(jwtUtil.extractUserId(token)));
        return ResponseEntity.ok(cartService.addToCart(cartItemDTO));
    }
}
