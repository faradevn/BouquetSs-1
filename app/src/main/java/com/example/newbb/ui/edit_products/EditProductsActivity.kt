package com.example.newbb.ui.edit_products

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import com.example.newbb.R
import com.example.newbb.databinding.ActivityEditProductsBinding
import com.example.newbb.model.Product
import com.example.newbb.ui.MainActivity
import com.example.newbb.view_model.EditProductViewModel


class EditProductsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProductsBinding
    lateinit var viewModel: EditProductViewModel
    var selectedItemSpinnner = "Book"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari intent
        val name_product = intent.getStringExtra("name_product")
        val image_url = intent.getStringExtra("image_url")
        val description = intent.getStringExtra("description")
        val price = intent.getStringExtra("price")

        // Set ke input form
        binding.etNameProduct.setText(name_product)
        binding.etImageUrl.setText(image_url)
        binding.etDescription.setText(description)
        binding.etPrice.setText(price)

        // Spinner kategori
        val arrayCategorySpinner = resources.getStringArray(R.array.category)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayCategorySpinner)
        binding.categorySpinner.adapter = adapter

        binding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                selectedItemSpinnner = arrayCategorySpinner[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) { }
        }

        initViewModel()
        editProductObservable()

        binding.btnEditProduct.setOnClickListener {
            updateProduct()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(EditProductViewModel::class.java)
    }

    private fun editProductObservable() {
        viewModel.getEditProductObservable().observe(this, Observer { product ->
            if (product == null) {
                Toast.makeText(this, "Failed to update product", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Success to update product", Toast.LENGTH_LONG).show()
                finish()
            }
        })
    }

    private fun updateProduct() {
        val product = Product(
            binding.etNameProduct.text.toString(),
            binding.etImageUrl.text.toString(),
            binding.etDescription.text.toString(),
            binding.etPrice.text.toString().toInt(),
            "Anonymous",           // tanpa user auth → ganti manual
            "no-email@example.com", // atau ambil dari local DB kalau punya
            selectedItemSpinnner
        )

        viewModel.updateProduct(
            binding.etNameProduct.text.toString(),
            "no-email@example.com",
            binding.etPrice.text.toString(),
            product
        )
    }
}
