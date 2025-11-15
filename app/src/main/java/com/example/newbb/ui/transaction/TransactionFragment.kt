package com.example.newbb.ui.transaction

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newbb.adapter.RecyclerViewAdapterTransaction
import com.example.newbb.databinding.FragmentTransactionBinding
import com.example.newbb.model.TransactionList
import com.example.newbb.view_model.TransactionFragmentViewModel
import com.google.firebase.auth.FirebaseAuth

class TransactionFragment : Fragment() {

    // ✅ Binding
    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!

    private lateinit var rcylerTransactionAdapter: RecyclerViewAdapterTransaction
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        initRecyclerView()
        getTransactionList(currentUser?.email ?: "")
    }

    private fun initRecyclerView() {
        binding.recyclerViewTransaction.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        rcylerTransactionAdapter = RecyclerViewAdapterTransaction()
        binding.recyclerViewTransaction.adapter = rcylerTransactionAdapter
    }

    private fun getTransactionList(emailBuyer: String) {
        val viewModel = ViewModelProvider(this)[TransactionFragmentViewModel::class.java]

        viewModel.getRecyclerTransactionListObserver()
            .observe(viewLifecycleOwner, Observer<TransactionList?> { it ->
                if (it != null) {
                    rcylerTransactionAdapter.transactionList = it.data.toMutableList()
                    rcylerTransactionAdapter.notifyDataSetChanged()
                    binding.constraintLayoutTransaction.visibility = View.GONE
                } else {
                    binding.constraintLayoutTransaction.visibility = View.VISIBLE
                }
            })

        viewModel.getTransactionList(emailBuyer)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // ✅ Hindari memory leak
    }

    companion object {
        @JvmStatic
        fun newInstance() = TransactionFragment()
    }
}