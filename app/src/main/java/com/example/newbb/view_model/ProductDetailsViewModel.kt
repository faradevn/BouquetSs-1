package com.example.newbb.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newbb.model.Product
import com.example.newbb.service.RetrofitInstance
import com.example.newbb.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductDetailsViewModel : ViewModel() {

    // ✅ Tidak pakai lateinit dan dibuat nullable agar bisa postValue(null)
    val deleteProductLiveData = MutableLiveData<Product?>()

    fun getDeleteProductObservable(): MutableLiveData<Product?> {
        return deleteProductLiveData
    }

    fun deleteProduct(name_product: String, email_user: String, price: String) {
        val retrofit = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
//        val call = retrofit.deleteProduct(name_product, email_user, price)
        val call = retrofit.deleteProduct(name_product, email_user, price)

        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    deleteProductLiveData.postValue(response.body())
                } else {
                    deleteProductLiveData.postValue(null) // ✅ Sekarang aman
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                deleteProductLiveData.postValue(null) // ✅ Bisa karena nullable
            }
        })
    }
}
