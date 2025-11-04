package com.example.backend.model;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Field("productId")   // ✅ Ensure Mongo uses productId field
    private String productId;

    private String title;
    private String description;
    private double price;
    private String image;
    private String category;
    private double rating;
    private int quantity;
    private int stock;
}
