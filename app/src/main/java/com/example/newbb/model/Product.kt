package com.example.newbb.model

data class Product(
    val name_product: String,
    val image_url: String,
    val description: String,
    val price: Int,
    val name_user: String,
    val email_user: String,
    val category: String
)