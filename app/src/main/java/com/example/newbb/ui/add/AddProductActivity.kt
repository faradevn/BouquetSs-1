package com.example.newbb.ui.add

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newbb.R
import com.example.newbb.databinding.ActivityAddProductBinding
import com.example.newbb.model.Book
import com.example.newbb.model.Product
import com.example.newbb.ui.MainActivity
import com.example.newbb.view_model.AddProductViewModel


class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private lateinit var viewModel: AddProductViewModel
    private var selectedItemSpinner = "Book"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        setupObservers()
        setupSpinner()
        setupListeners()
    }

private fun initViewModel() {
    viewModel = ViewModelProvider(this)[AddProductViewModel::class.java]

}
    private fun setupObservers() {
        viewModel.getAddProductObservable().observe(this) { /* optional */ }

        viewModel.getAddLaptopObservable().observe(this, Observer<Book?> {
            showResultToast(it, "laptop")
        })

        viewModel.getAddHoodieObservable().observe(this, Observer<Book?> {
            showResultToast(it, "hoodie")
        })

        viewModel.getAddBookObservable().observe(this, Observer<Book?> {
            showResultToast(it, "book")
        })

    }

    private fun showResultToast(result: Book?, category: String) {
        if (result == null) {
            Toast.makeText(
                this,
                "Failed to create category $category",
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                this,
                "Success to create category $category",
                Toast.LENGTH_LONG
            ).show()
            finish()
        }
    }

    private fun setupSpinner() {
        val arrayCategorySpinner = resources.getStringArray(R.array.category)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            arrayCategorySpinner
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.categorySpinner.adapter = adapter
        binding.categorySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItemSpinner = arrayCategorySpinner[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupListeners() {
        binding.btnAddProduct.setOnClickListener {
            addProduct()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun addProduct() {
        val name = binding.etNameProduct.text.toString()
        val imageUrl = binding.etImageUrl.text.toString()
        val description = binding.etDescription.text.toString()
        val priceText = binding.etPrice.text.toString()

        if (name.isEmpty() || imageUrl.isEmpty() || description.isEmpty() || priceText.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val price = priceText.toIntOrNull() ?: 0
        val product = Product(
            name,
            imageUrl,
            description,
            price,
            "Anonymous", // Tanpa Firebase user name
            "anonymous@email.com", // Tanpa Firebase email
            selectedItemSpinner
        )

        val book = Book(
            name,
            imageUrl,
            description,
            price,
            "Anonymous",
            "anonymous@email.com"
        )

        viewModel.addProduct(product)
        addByCategory(book)
    }

    private fun addByCategory(book: Book) {
        when (selectedItemSpinner) {
            "Book" -> viewModel.addBook(book)
            "Hoodie" -> viewModel.addHoodie(book)
            "Laptop" -> viewModel.addLaptop(book)
        }
    }
}
