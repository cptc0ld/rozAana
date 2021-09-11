package com.application.rozaana.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.application.rozaana.R
import com.application.rozaana.databinding.FragmentRegisterBinding
import android.widget.Toast
import com.application.rozaana.MainActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.testbook.tbapp.prefs.MySharedPreferences


class RegisterFragment : Fragment() {
    companion object {
        fun newInstance() =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private var mAuth: FirebaseAuth? = null
    var db: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        arguments?.let {
        }
    }

    lateinit var binding: FragmentRegisterBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
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
         binding.btnRegister.setOnClickListener{
             if(validateData()){
                 signInUser(binding.etName.text.toString(), binding.etEmail.text.toString(), binding.etPassword.text.toString())
             }
         }
    }

    private fun validateData(): Boolean {
        if(binding.etPassword.text.toString() != binding.etRepassword.text.toString()){
            binding.error.text = "Password doesnot match"
            return false
        }
        return true
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

    private fun signInUser(name: String, email: String, password: String) {
        mAuth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(requireActivity()
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user: FirebaseUser? = mAuth!!.currentUser
                    createNewUser(name, task.result?.user);
//                    updateUI(user)
                } else {
                    binding.error.text = task.exception?.message
//                    updateUI(null)
                }
            }
    }

    private fun createNewUser(name: String, firebaseUser: FirebaseUser?) {
        val user: MutableMap<String, Any> = HashMap()
        user["username"] = name
        user["email"] = firebaseUser?.email as String
        user["uid"] = firebaseUser.uid

        db!!.collection("users")
            .add(user)
            .addOnSuccessListener {
                MySharedPreferences.username = name
                MySharedPreferences.userId = firebaseUser.uid
                activity?.finish()
                startActivity(Intent(this.activity, MainActivity::class.java))
            }
            .addOnFailureListener { }
    }

}