package com.example.newbb.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.newbb.databinding.ActivityProductDetailsBinding
import com.example.newbb.model.Transaction
import com.example.newbb.ui.MainActivity
import com.example.newbb.ui.edit_products.EditProductsActivity
import com.example.newbb.utils.Constant.BASE_URL_MIDTRANS
import com.example.newbb.utils.Constant.CLIENT_KEY_MIDTRANS
import com.example.newbb.view_model.HomeFragmentViewModel
import com.example.newbb.view_model.ProductDetailsViewModel
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.squareup.picasso.Picasso
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ShippingAddress

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailsBinding
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var viewModelDelete: ProductDetailsViewModel

    private var transactionResult = TransactionResult()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initViewModel2()
        createTransactionObservable()

        val name_product = intent.getStringExtra("name_product")
        val image_url = intent.getStringExtra("image_url")
        val description = intent.getStringExtra("description")
        val price = intent.getStringExtra("price")
        val name_user = intent.getStringExtra("name_user")
        val email_user = intent.getStringExtra("email_user")

        // Setup Midtrans
        SdkUIFlowBuilder.init()
            .setClientKey(CLIENT_KEY_MIDTRANS)
            .setContext(applicationContext)
            .setTransactionFinishedCallback {
                when (transactionResult.status) {
                    TransactionResult.STATUS_SUCCESS -> {
                        Toast.makeText(this, "Success transaction", Toast.LENGTH_LONG).show()
                        createTransaction(
                            name_product!!,
                            description!!,
                            image_url!!,
                            price!!,
                            name_user!!,
                            email_user!!,
                            "Anonymous",
                            "anonymous@example.com"
                        )
                    }
                    TransactionResult.STATUS_PENDING -> Toast.makeText(this, "Pending transaction", Toast.LENGTH_LONG).show()
                    TransactionResult.STATUS_FAILED -> Toast.makeText(this, "Failed transaction", Toast.LENGTH_LONG).show()
                    TransactionResult.STATUS_INVALID -> Toast.makeText(this, "Invalid transaction", Toast.LENGTH_LONG).show()
                    else -> Toast.makeText(this, "Failure transaction", Toast.LENGTH_LONG).show()
                }
            }
            .setMerchantBaseUrl(BASE_URL_MIDTRANS)
            .enableLog(true)
            .setColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255"))
            .setLanguage("id")
            .buildSDK()

        // Load product data
        Picasso.get().load(image_url).into(binding.ivProductDetails)
        binding.tvNameProduct.text = name_product
        binding.tvDescriptionProduct.text = description
        binding.tvPriceProductDetails.text = "Rp. $price"
        binding.tvUserName.text = name_user
        binding.tvUserEmail.text = email_user

        // Hide buy buttons if the product belongs to the same user
        if (email_user == "anonymous@example.com") {
            binding.btnMidtrans.visibility = View.GONE
            binding.ivDelete.visibility = View.VISIBLE
            binding.ivEdit.visibility = View.VISIBLE
            binding.tvCannotBuy.visibility = View.VISIBLE
        }

        // Button listeners
        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnMidtrans.setOnClickListener {
            Toast.makeText(this, "Open transaction", Toast.LENGTH_LONG).show()
            val transactionRequest = TransactionRequest(
                "Beep-Midtrans" + System.currentTimeMillis(),
                price!!.toDouble()
            )
            val detail = com.midtrans.sdk.corekit.models.ItemDetails(
                name_product,
                price.toDouble(),
                1,
                name_product
            )
            val itemDetails = arrayListOf(detail)
            uiKitDetails(transactionRequest)
            transactionRequest.itemDetails = itemDetails
            MidtransSDK.getInstance().transactionRequest = transactionRequest
            MidtransSDK.getInstance().startPaymentUiFlow(this)
        }

        binding.ivDelete.setOnClickListener {
            deleteProduct(name_product!!, email_user!!, price!!)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.ivEdit.setOnClickListener {
            val intent = Intent(this, EditProductsActivity::class.java).apply {
                putExtra("name_product", name_product)
                putExtra("image_url", image_url)
                putExtra("description", description)
                putExtra("price", price)
            }
            startActivityForResult(intent, 1000)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[HomeFragmentViewModel::class.java]
    }

    private fun initViewModel2() {
        viewModelDelete = ViewModelProvider(this)[ProductDetailsViewModel::class.java]
    }

    private fun createTransactionObservable() {
        viewModel.getCreateTransactionObservable().observe(this) {
            if (it == null) {
                Toast.makeText(this, "Failed to create/update new user", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Success to create/update new user", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun deleteProduct(name_product: String, email_user: String, price: String) {
        viewModelDelete.getDeleteProductObservable().observe(this) {
            if (it == null) {
                Toast.makeText(this, "Failed to delete product", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Success to delete product", Toast.LENGTH_LONG).show()
                finish()
            }
        }
        viewModelDelete.deleteProduct(name_product, email_user, price)
    }

    private fun createTransaction(
        name_product: String,
        description: String,
        image_url: String,
        price: String,
        name_user: String,
        email_user: String,
        name_buyer: String,
        name_email: String
    ) {
        val transaction = Transaction(
            name_product,
            description,
            image_url,
            price.toInt(),
            name_user,
            email_user,
            name_buyer,
            name_email
        )
        viewModel.createTransaction(transaction)
    }

    private fun uiKitDetails(transactionRequest: TransactionRequest) {
        val customerDetails = CustomerDetails().apply {
            customerIdentifier = "Supriyanto"
            phone = "082345678999"
            firstName = "Supri"
            lastName = "Yanto"
            email = "supriyanto6543@gmail.com"

            shippingAddress = ShippingAddress().apply {
                address = "Baturan, Gantiwarno"
                city = "Klaten"
                postalCode = "51193"
            }

            billingAddress = BillingAddress().apply {
                address = "Baturan, Gantiwarno"
                city = "Klaten"
                postalCode = "51193"
            }
        }
        transactionRequest.customerDetails = customerDetails
    }
}
