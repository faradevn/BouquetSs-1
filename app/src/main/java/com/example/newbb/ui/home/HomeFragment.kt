package com.example.newbb.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newbb.R
import com.example.newbb.adapter.RecyclerViewAdapterBook
import com.example.newbb.adapter.RecyclerViewAdapterProduct
import com.example.newbb.model.Book
import com.example.newbb.model.Product
import com.example.newbb.ui.detail.ProductDetailsActivity
import com.example.newbb.view_model.HomeFragmentViewModel


class HomeFragment : Fragment(),


    RecyclerViewAdapterProduct.OnItemClickListener,
    RecyclerViewAdapterBook.OnItemClickListener
{

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recyclerView3: RecyclerView
    private lateinit var recyclerView4: RecyclerView

    private lateinit var rcylerProductList: RecyclerViewAdapterProduct
    private lateinit var rcylerBookList: RecyclerViewAdapterBook
    private lateinit var rcylerLaptopList: RecyclerViewAdapterBook
    private lateinit var rcylerHoodieList: RecyclerViewAdapterBook

    private lateinit var viewModel: HomeFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Inisialisasi RecyclerView
        initGetAllProductRecyclerView(view)
        initGetBookRecyclerView(view)
        initGetLaptopRecyclerView(view)
        initGetHoodieRecyclerView(view)

        // Observe data
        observeProductList()
        observeBookList()
        observeLaptopList()
        observeHoodieList()

        // Fetch data dari API
        viewModel.getProductList()
        viewModel.getBookList()
//        viewModel.getLaptopList()
//        viewModel.getHoodieList()

        return view
    }

    // ----------------- RecyclerView Initialization -----------------

    private fun initGetAllProductRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcylerProductList = RecyclerViewAdapterProduct(this)
        recyclerView.adapter = rcylerProductList
    }

    private fun initGetBookRecyclerView(view: View) {
        recyclerView2 = view.findViewById(R.id.recyclerView2)
        recyclerView2.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcylerBookList = RecyclerViewAdapterBook(this)
        recyclerView2.adapter = rcylerBookList
    }

    private fun initGetLaptopRecyclerView(view: View) {
        recyclerView3 = view.findViewById(R.id.recyclerView3)
        recyclerView3.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcylerLaptopList = RecyclerViewAdapterBook(this)
        recyclerView3.adapter = rcylerLaptopList
    }

    private fun initGetHoodieRecyclerView(view: View) {
        recyclerView4 = view.findViewById(R.id.recyclerView4)
        recyclerView4.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rcylerHoodieList = RecyclerViewAdapterBook(this)
        recyclerView4.adapter = rcylerHoodieList
    }


    private fun observeProductList() {
        viewModel.getRecyclerProductListObserver().observe(viewLifecycleOwner) { productList ->
            val dataList = productList?.data

            if (!dataList.isNullOrEmpty()) {
                rcylerProductList.productList = dataList.toMutableList()
                rcylerProductList.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "Error getting product data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeBookList() {
        viewModel.getRecyclerBookListObserver().observe(viewLifecycleOwner) { bookList ->
            bookList?.let {
                if (::rcylerBookList.isInitialized) {
                    rcylerBookList.bookList = it.data.toMutableList()
                    rcylerBookList.notifyDataSetChanged()
                }
            } ?: run {
                Toast.makeText(activity, "Error getting book data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeLaptopList() {
        viewModel.getRecyclerLaptopListObserver().observe(viewLifecycleOwner) { laptopList ->
            laptopList?.let {
                if (::rcylerLaptopList.isInitialized) {
                    rcylerLaptopList.bookList = it.data.toMutableList()
                    rcylerLaptopList.notifyDataSetChanged()
                }
            } ?: run {
                Toast.makeText(activity, "Error getting laptop data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeHoodieList() {
        viewModel.getRecyclerHoodieListObserver().observe(viewLifecycleOwner) { hoodieList ->
            hoodieList?.let {
                if (::rcylerHoodieList.isInitialized) {
                    rcylerHoodieList.bookList = it.data.toMutableList()
                    rcylerHoodieList.notifyDataSetChanged()
                }
            } ?: run {
                Toast.makeText(activity, "Error getting hoodie data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ----------------- Item Click Handlers -----------------

    override fun onItemEditClickProduct(product: Product) {
        val intent = Intent(activity, ProductDetailsActivity::class.java).apply {
            putExtra("name_product", product.name_product)
            putExtra("image_url", product.image_url)
            putExtra("description", product.description)
            putExtra("price", product.price.toString())
            putExtra("name_user", product.name_user)
            putExtra("email_user", product.email_user)
        }
        startActivityForResult(intent, 1000)
    }

    override fun onItemClickListenerBook(book: Book) {
        val intent = Intent(activity, ProductDetailsActivity::class.java).apply {
            putExtra("name_product", book.name_product)
            putExtra("image_url", book.image_url)
            putExtra("description", book.description)
            putExtra("price", book.price.toString())
            putExtra("name_user", book.name_user)
            putExtra("email_user", book.email_user)
        }
        startActivityForResult(intent, 1000)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}

