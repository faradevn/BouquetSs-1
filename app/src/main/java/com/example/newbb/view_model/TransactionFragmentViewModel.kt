package com.example.newbb.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newbb.model.TransactionList
import com.example.newbb.service.RetrofitInstance
import com.example.newbb.service.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionFragmentViewModel : ViewModel() {

    // ✅ Tidak pakai lateinit, langsung aman & nullable
    val transactionList = MutableLiveData<TransactionList?>()

    // ✅ Getter tetap tersedia jika masih digunakan di Fragment
    fun getRecyclerTransactionListObserver(): MutableLiveData<TransactionList?> {
        return transactionList
    }

    fun getTransactionList(email_buyer: String?) {
        // ✅ Hindari email_buyer!! untuk mencegah crash
        if (email_buyer.isNullOrEmpty()) {
            transactionList.postValue(null)
            return
        }

        val retrofit = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
        val call = retrofit.getTransactionList(email_buyer)

        call.enqueue(object : Callback<TransactionList?> {
            override fun onResponse(call: Call<TransactionList?>, response: Response<TransactionList?>) {
                if (response.isSuccessful) {
                    transactionList.postValue(response.body())
                } else {
                    transactionList.postValue(null)
                }
            }

            override fun onFailure(call: Call<TransactionList?>, t: Throwable) {
                transactionList.postValue(null)
            }
        })
    }
}
//class TransactionFragmentViewModel : ViewModel() {
//
//    // LiveData untuk daftar transaksi
//    val transactionList = MutableLiveData<TransactionList?>()
//
//    // Getter tetap disediakan untuk Fragment
//    fun getRecyclerTransactionListObserver(): MutableLiveData<TransactionList?> {
//        return transactionList
//    }
//
//    // Tidak lagi butuh email_buyer
//    fun getTransactionList() {
//        val retrofit = RetrofitInstance.getRetrofitInstance().create(RetrofitService::class.java)
//        val call = retrofit.getTransactionList() // tanpa parameter
//
//        call.enqueue(object : Callback<TransactionList?> {
//            override fun onResponse(call: Call<TransactionList?>, response: Response<TransactionList?>) {
//                if (response.isSuccessful) {
//                    transactionList.postValue(response.body())
//                } else {
//                    transactionList.postValue(null)
//                }
//            }
//
//            override fun onFailure(call: Call<TransactionList?>, t: Throwable) {
//                transactionList.postValue(null)
//            }
//        })
//    }
//}

