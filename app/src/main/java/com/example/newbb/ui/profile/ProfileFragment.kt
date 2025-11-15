package com.example.newbb.ui.profile

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.newbb.databinding.FragmentProfileBinding
import com.example.newbb.ui.add.AddProductActivity
import com.example.newbb.ui.auth.SignInActivity
import com.example.newbb.ui.myproduct.MyProductActivity
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        // ✅ Tampilkan foto profil
        currentUser?.photoUrl?.let {
            Picasso.get().load(it).into(binding.ivUser)
        }

        // ✅ Nama pengguna
        binding.tvUser.text = currentUser?.displayName ?: "Anonymous"

        // ✅ Email pengguna
        binding.tvEmail.text = currentUser?.email ?: "No email"

        // ✅ Tombol sign out
        binding.btnSignOut.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(activity, SignInActivity::class.java)
            startActivity(intent)
            activity?.finish() // optional: supaya tidak bisa back ke user fragment
        }

        // ✅ Tombol tambah produk
        binding.btnAddProduct.setOnClickListener {
            val intent = Intent(activity, AddProductActivity::class.java)
            startActivity(intent)
        }

        // ✅ Tombol produk saya
        binding.btnMyProduct.setOnClickListener {
            val intent = Intent(activity, MyProductActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}

