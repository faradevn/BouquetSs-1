package com.example.newbb.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newbb.model.ProductList
import com.example.newbb.service.RetrofitInstance
import com.example.newbb.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyProductViewModel : ViewModel() {

    // ✅ Tidak menggunakan lateinit, langsung diinisialisasi & nullable
    val recyclerMyProductList = MutableLiveData<ProductList?>()

    fun getRecyclerMyProductListObserver(): MutableLiveData<ProductList?> {
        return recyclerMyProductList
    }

//    fun getMyProductList(email: String) {
//        val retrofit = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
//        val call = retrofit.getMyProduct(email)
//
//        call.enqueue(object : Callback<ProductList> {
//            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
//                if (response.isSuccessful) {
//                    recyclerMyProductList.postValue(response.body())
//                } else {
//                    recyclerMyProductList.postValue(null) // ✅ Aman, tidak error
//                }
//            }
//
//            override fun onFailure(call: Call<ProductList>, t: Throwable) {
//                recyclerMyProductList.postValue(null) // ✅ Bisa terkirim karena nullable
//            }
//        })
//    }
//}

    fun getMyProductList() {
        val retrofit = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        retrofit.getProductList().enqueue(object : Callback<ProductList> {
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                recyclerMyProductList.postValue(response.body())
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                recyclerMyProductList.postValue(null)
            }
        })
    }
}