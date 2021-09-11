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
import com.application.rozaana.databinding.FragmentAuthBinding
import com.application.rozaana.ui.home.SliderTransformer
import com.application.rozaana.ui.home.childFragments.MediaFragment
import com.google.firebase.auth.FirebaseAuth

class AuthFragment : Fragment() {

    companion object {
        fun newInstance(param1: String, param2: String) =
            AuthFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance();
        arguments?.let {
        }
    }

    lateinit var binding: FragmentAuthBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false)
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

    lateinit var viewpager2: ViewPager2Adapter
    private fun initViews() {
        viewpager2 = ViewPager2Adapter(
            childFragmentManager,
            lifecycle
        )
        getFragments()
        binding.viewpager.adapter = viewpager2
        binding.viewpager.offscreenPageLimit = 3
        binding.viewpager.setPageTransformer(SliderTransformer())


        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }

    private fun getFragments() {
        viewpager2.addFrag(LoginFragment.newInstance())
        viewpager2.addFrag(RegisterFragment.newInstance())
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