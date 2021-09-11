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
import com.application.rozaana.ui.search.SearchFragment

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView


class MainActivity : AppCompatActivity() {

    private var al: ArrayList<String>? = null
    private var arrayAdapter: ArrayAdapter<String>? = null
    private var i = 0

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.bottomNavigation.setOnItemSelectedListener(navListener)

        binding.toolbarActionbar.toolbarTitleTv.text = "Home"

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment()).commit()
    }

    private val navListener =
        NavigationBarView.OnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.home -> selectedFragment = HomeFragment()
                R.id.search -> selectedFragment = SearchFragment()
                R.id.profile -> selectedFragment = ProfileFragment()
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