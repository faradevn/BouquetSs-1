package com.example.newbb.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newbb.model.BookList
import com.example.newbb.model.ProductList
import com.example.newbb.model.Transaction
import com.example.newbb.service.RetrofitInstance
import com.example.newbb.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragmentViewModel : ViewModel() {

    // ✅ Tidak lagi pakai lateinit dan dibuat nullable agar bisa postValue(null)
    val rcyclerProductList = MutableLiveData<ProductList?>()
    val rcyclerBookList = MutableLiveData<BookList?>()
    val rcyclerLaptopList = MutableLiveData<BookList?>()
    val rcyclerHoodieList = MutableLiveData<BookList?>()

    // ✅ Untuk transaksi
    val createTransactionLiveData = MutableLiveData<Transaction?>()

    fun getRecyclerProductListObserver() = rcyclerProductList
    fun getRecyclerBookListObserver() = rcyclerBookList
    fun getRecyclerLaptopListObserver() = rcyclerLaptopList
    fun getRecyclerHoodieListObserver() = rcyclerHoodieList
    fun getCreateTransactionObservable() = createTransactionLiveData

    // ======================= API Call ==========================

    fun createTransaction(transaction: Transaction) {
        val retrofit = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        retrofit.createTransaction(transaction).enqueue(object : Callback<Transaction> {
            override fun onResponse(call: Call<Transaction>, response: Response<Transaction>) {
                createTransactionLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Transaction>, t: Throwable) {
                createTransactionLiveData.postValue(null)
            }
        })
    }

    fun getProductList() {
        val retrofit = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        retrofit.getProductList().enqueue(object : Callback<ProductList> {
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                rcyclerProductList.postValue(response.body())
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                rcyclerProductList.postValue(null)
            }
        })
    }


    fun getBookList() {
        val retrofit = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        retrofit.getBookList().enqueue(object : Callback<BookList> {
            override fun onResponse(call: Call<BookList>, response: Response<BookList>) {
                if (response.isSuccessful) {
                    rcyclerBookList.postValue(response.body())
                } else {
                    Log.e("BookListError", "Response failed: ${response.code()} - ${response.message()}")
                    rcyclerBookList.postValue(null)
                }
            }

            override fun onFailure(call: Call<BookList>, t: Throwable) {
                Log.e("BookListError", "Network failure: ${t.message}")
                rcyclerBookList.postValue(null)
            }
        })
    }




//    fun getBookList() {
//        val retrofit = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
//        retrofit.getBookList().enqueue(object : Callback<BookList> {
//            override fun onResponse(call: Call<BookList>, response: Response<BookList>) {
//                rcyclerBookList.postValue(response.body())
//            }
//
//            override fun onFailure(call: Call<BookList>, t: Throwable) {
//                rcyclerBookList.postValue(null)
//            }
//        })
//    }
//
//    fun getLaptopList() {
//        val retrofit = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
//        retrofit.getLaptopList().enqueue(object : Callback<BookList> {
//            override fun onResponse(call: Call<BookList>, response: Response<BookList>) {
//                rcyclerLaptopList.postValue(response.body())
//            }
//
//            override fun onFailure(call: Call<BookList>, t: Throwable) {
//                rcyclerLaptopList.postValue(null)
//            }
//        })
//    }
//
//    fun getHoodieList() {
//        val retrofit = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
//        retrofit.getHoodieList().enqueue(object : Callback<BookList> {
//            override fun onResponse(call: Call<BookList>, response: Response<BookList>) {
//                rcyclerHoodieList.postValue(response.body())
//            }
//
//            override fun onFailure(call: Call<BookList>, t: Throwable) {
//                rcyclerHoodieList.postValue(null)
//            }
//        })
//    }
}
