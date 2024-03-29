package com.application.rozaana.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.application.rozaana.R
import com.application.rozaana.baseui.ViewPager2Adapter
import com.application.rozaana.databinding.FragmentProfileBinding
import com.application.rozaana.ui.home.SliderTransformer
import com.google.firebase.auth.FirebaseAuth
import androidx.annotation.NonNull
import com.application.rozaana.MainActivity
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import android.content.Intent
import com.testbook.tbapp.prefs.MySharedPreferences


class ProfileFragment : Fragment() {

    companion object {

        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initViewModel()
        initViews()
        initAdapters()
        initViewModelCalls()
        initViewModelObservers()
    }

    private fun initViewModel() {
//        TODO("Not yet implemented")
    }

    private fun initViews() {
        binding.logout.text = "Logout ${MySharedPreferences.username}"
        initOnClickListeners()
    }

    private fun initOnClickListeners() {
        val authStateListener =
            AuthStateListener { firebaseAuth ->
                if (firebaseAuth.currentUser == null) {
                    activity?.finish()
                    startActivity(Intent(this.activity, MainActivity::class.java))
                }
            }
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.addAuthStateListener(authStateListener)
        binding.logout.setOnClickListener {
            firebaseAuth.signOut()
        }
    }

    private fun getFragments() {

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



}