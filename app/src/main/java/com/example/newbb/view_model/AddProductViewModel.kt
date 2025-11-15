package com.example.newbb.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newbb.model.Book
import com.example.newbb.model.Product
import com.example.newbb.service.RetrofitInstance
import com.example.newbb.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddProductViewModel : ViewModel() {


    // ✅ Tidak pakai lateinit, dan dibuat nullable agar bisa postValue(null)
    val addProductLiveData = MutableLiveData<Product?>()
    val addBookLiveData = MutableLiveData<Book?>()
    val addLaptopLiveData = MutableLiveData<Book?>()
    val addHoodieLiveData = MutableLiveData<Book?>()

    fun getAddProductObservable() = addProductLiveData
    fun getAddBookObservable() = addBookLiveData
    fun getAddLaptopObservable() = addLaptopLiveData
    fun getAddHoodieObservable() = addHoodieLiveData

    fun addProduct(product: Product) {
        val retrofit = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        retrofit.addProduct(product).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                addProductLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                addProductLiveData.postValue(null) // ✅ Tidak error lagi
            }
        })
    }

    fun addBook(book: Book) {
        val retrofit = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        retrofit.addBook(book).enqueue(object : Callback<Book> {
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                addBookLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                addBookLiveData.postValue(null)
            }
        })
    }

    fun addLaptop(laptop: Book) {
        val retrofit = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        retrofit.addLaptop(laptop).enqueue(object : Callback<Book> {
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                addLaptopLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                addLaptopLiveData.postValue(null)
            }
        })
    }

    fun addHoodie(hoodie: Book) {
        val retrofit = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        retrofit.addHoodie(hoodie).enqueue(object : Callback<Book> {
            override fun onResponse(call: Call<Book>, response: Response<Book>) {
                addHoodieLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Book>, t: Throwable) {
                addHoodieLiveData.postValue(null)
            }
        })
    }
}