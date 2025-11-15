package com.example.newbb.service

import com.example.newbb.model.Book
import com.example.newbb.model.BookList
import com.example.newbb.model.Product
import com.example.newbb.model.ProductList
import com.example.newbb.model.Transaction
import com.example.newbb.model.TransactionList
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RetrofitService {

    @GET("products")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getProductList(): Call<ProductList>

    //    http://localhost:3000/v1/products/book
    @GET("products/book")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getBookList(): Call<BookList>

    //    http://localhost:3000/v1/products/laptop
    @GET("products/laptop")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getLaptopList(): Call<BookList>

    //    http://localhost:3000/v1/products/laptop
    @GET("products/hoodie")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getHoodieList(): Call<BookList>

    //    http://localhost:3000/v1/transaction/:user_buyer
    @GET("transaction/user/{email_buyer}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getTransactionList(@Path("email_buyer") email_buyer: String): Call<TransactionList?>

    //    http://localhost:3000/v1/product/user/:email
    @GET("products/user/{email}")
    @Headers("Accept:application/json", "Content-Type:application/json")
    fun getMyProduct(@Path("email") email: String): Call<ProductList>

    // http://localhost:3000/v1/transaction
    @POST("transaction")
    @Headers(
        "Accept:application/json",
        "Content-Type:application/json",
    )
    fun createTransaction(@Body params: Transaction): Call<Transaction>

    // http://localhost:3000/v1/products
    @POST("products")
    @Headers(
        "Accept:application/json",
        "Content-Type:application/json",
    )
    fun addProduct(@Body params: Product): Call<Product>

    // http://localhost:3000/v1/products/book
    @POST("products/book")
    @Headers(
        "Accept:application/json",
        "Content-Type:application/json",
    )
    fun addBook(@Body params: Book): Call<Book>

    // http://localhost:3000/v1/products/laptop
    @POST("products/laptop")
    @Headers(
        "Accept:application/json",
        "Content-Type:application/json",
    )
    fun addLaptop(@Body params: Book): Call<Book>

    // http://localhost:3000/v1/products/hoodie
    @POST("products/hoodie")
    @Headers(
        "Accept:application/json",
        "Content-Type:application/json",
    )
    fun addHoodie(@Body params: Book): Call<Book>

    @DELETE("products/np/{name_product}/{email_user}/{price}")
    @Headers(
        "Accept:application/json",
        "Content-Type:application/json",
    )
    fun deleteProduct(@Path("name_product") name_product: String, @Path("email_user") email_user: String, @Path("price") price:String): Call<Product>

    @PUT("products/np/{name_product}/{email_user}/{price}")
    @Headers(
        "Accept:application/json",
        "Content-Type:application/json",
    )
    fun updateProduct(@Path("name_product") name_product: String, @Path("email_user") email_user: String, @Path("price") price:String, @Body params: Product): Call<Product>

}