package com.application.rozaana.ui.profile

import android.R.attr
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.application.rozaana.R
import com.application.rozaana.baseui.ViewPager2Adapter
import com.application.rozaana.databinding.FragmentLoginBinding
import com.application.rozaana.ui.home.SliderTransformer
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.AuthResult

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener

import android.R.attr.password
import android.content.Intent
import com.application.rozaana.MainActivity
import com.google.firebase.firestore.QueryDocumentSnapshot

import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.testbook.tbapp.prefs.MySharedPreferences


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() =
            LoginFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        arguments?.let {
        }
    }

    lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initViewModel()
        initViewModelCalls()
        initViewModelObservers()
        initOnClickListeners()
    }

    private fun initOnClickListeners() {
        binding.btnLogin.setOnClickListener{
            signInUser(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        }
    }

    private fun initViewModel() {
//        TODO("Not yet implemented")
    }


    private fun initAdapters() {
//        TODO("Not yet implemented")
    }

    private fun initViewModelCalls() {
//        TODO("Not yet implemented")
    }

    private fun initViewModelObservers() {
//        TODO("Not yet implemented")
    }

    private fun signInUser(email: String, password: String) {
        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth!!.currentUser
                    getUserName(email)
                    activity?.finish()
                    startActivity(Intent(this.activity, MainActivity::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    binding.error.text = "Incorrect login"
                }
            }
    }

    private fun getUserName(email: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot?> { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if(document.data["email"] == email){
                            MySharedPreferences.username = document.data["username"] as String?
                            MySharedPreferences.userId = document.data["uid"] as String?
                        }
                    }
                }
            })
    }
}