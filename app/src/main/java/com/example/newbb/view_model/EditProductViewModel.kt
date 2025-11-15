package com.example.newbb.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newbb.model.Product
import com.example.newbb.service.RetrofitInstance
import com.example.newbb.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EditProductViewModel : ViewModel() {

    // ✅ Tidak pakai lateinit, dan bisa null saat error
    val updateProductLiveData = MutableLiveData<Product?>()

    fun getEditProductObservable(): MutableLiveData<Product?> {
        return updateProductLiveData
    }

    fun updateProduct(
        name_product: String,
        email_user: String,
        price: String,
        product: Product
    ) {
        val retrofit = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        val call = retrofit.updateProduct(name_product, email_user, price, product)

        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    updateProductLiveData.postValue(response.body())
                } else {
                    updateProductLiveData.postValue(null) // ✅ Aman karena nullable
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                updateProductLiveData.postValue(null) // ✅ Tidak error lagi
            }
        })
    }
}
