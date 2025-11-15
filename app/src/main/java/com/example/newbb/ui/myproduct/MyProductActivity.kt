package com.example.newbb.ui.myproduct

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.newbb.adapter.RecyclerViewAdapterProduct
import com.example.newbb.databinding.ActivityMyProductBinding
import com.example.newbb.model.Product
import com.example.newbb.ui.MainActivity
import com.example.newbb.ui.detail.ProductDetailsActivity
import com.example.newbb.view_model.MyProductViewModel
import kotlin.toString

class MyProductActivity : AppCompatActivity(), RecyclerViewAdapterProduct.OnItemClickListener {

    private lateinit var binding: ActivityMyProductBinding
    private lateinit var recyclerViewAdapter: RecyclerViewAdapterProduct
    private lateinit var viewModel: MyProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initViewModel()

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun initRecyclerView() {
        recyclerViewAdapter = RecyclerViewAdapterProduct(this)
        binding.recyclerViewMyProduct.apply {
            layoutManager = GridLayoutManager(this@MyProductActivity, 2)
            adapter = recyclerViewAdapter
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[MyProductViewModel::class.java]

        // Email bisa statis atau dikirim dari intent
//        val emailUser = intent.getStringExtra("email_user") ?: "example@email.com"

        viewModel.getRecyclerMyProductListObserver().observe(this) { productList ->
            if (productList == null || productList.data.isNullOrEmpty()) {
                binding.recyclerViewMyProduct.visibility = View.GONE
                binding.ivNoProduct.visibility = View.VISIBLE
                binding.tvNoProduct.visibility = View.VISIBLE
            } else {
                binding.recyclerViewMyProduct.visibility = View.VISIBLE
                binding.ivNoProduct.visibility = View.GONE
                binding.tvNoProduct.visibility = View.GONE

                recyclerViewAdapter.productList = productList.data.toMutableList()
                recyclerViewAdapter.notifyDataSetChanged()
            }
        }

        viewModel.getMyProductList()
    }

    // 🟢 Fungsi ini HARUS di luar fungsi lain
    override fun onItemEditClickProduct(product: Product) {
        val intent = Intent(this, ProductDetailsActivity::class.java).apply {
            putExtra("name_product", product.name_product)
            putExtra("image_url", product.image_url)
            putExtra("description", product.description)
            putExtra("price", product.price.toString())
            // Jika perlu, tambahkan ini lagi
            // putExtra("name_user", product.name_user)
            // putExtra("email_user", product.email_user)
        }
        startActivity(intent)
    }
}