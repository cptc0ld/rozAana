package com.application.rozaana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.application.rozaana.databinding.ActivityMainBinding

import com.application.rozaana.ui.profile.ProfileFragment

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.application.rozaana.ui.home.HomeFragment
import com.application.rozaana.ui.profile.AuthFragment
import com.application.rozaana.ui.search.SearchFragment

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.testbook.tbapp.prefs.MySharedPreferences


class MainActivity : AppCompatActivity() {

    private var al: ArrayList<String>? = null
    private var arrayAdapter: ArrayAdapter<String>? = null
    private var i = 0

    lateinit var binding: ActivityMainBinding
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance();
        MySharedPreferences.instantiate(application)

        binding.bottomNavigation.setOnItemSelectedListener(navListener)
        val currentUser = mAuth!!.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser == null) {
            binding.bottomNavigation.selectedItemId = R.id.profile
        }else{
            binding.bottomNavigation.selectedItemId = R.id.home
        }
    }

    private val navListener =
        NavigationBarView.OnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.home -> selectedFragment = HomeFragment()
                R.id.search -> selectedFragment = SearchFragment()
                R.id.profile -> {
                    if(mAuth?.currentUser != null) {
                        selectedFragment = ProfileFragment()
                    }else{
                        selectedFragment = AuthFragment()
                    }
                }
            }
            // It will help to replace the
            // one fragment to other.
            binding.toolbarActionbar.toolbarTitleTv.text = item.title
            if (selectedFragment != null) {
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, selectedFragment)
                    .commit()
            }
            true
        }

//    private val reSelectListener =
//        NavigationBarView.OnItemReselectedListener { item ->
//            var selectedFragment: Fragment? = null
//            when (item.itemId) {
//                R.id.home -> {
//                    selectedFragment = HomeFragment()
//                }
//                R.id.search -> {
//                    selectedFragment = SearchFragment()
//                }
//                R.id.profile -> {
//                    selectedFragment = ProfileFragment()
//                }
//            }
//            // It will help to replace the
//            // one fragment to other.
//            binding.toolbarActionbar.toolbarTitleTv.text = item.title
//            if (selectedFragment != null) {
//                supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.fragment_container, selectedFragment)
//                    .commit()
//            }
//            true
//        }
}