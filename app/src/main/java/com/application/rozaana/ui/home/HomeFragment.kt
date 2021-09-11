package com.application.rozaana.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.application.rozaana.R
import com.application.rozaana.baseui.ViewPager2Adapter
import com.application.rozaana.databinding.FragmentHomeBinding
import com.application.rozaana.model.PexelData
import com.application.rozaana.repo.HomeRepository
import com.application.rozaana.ui.home.childFragments.MediaFragment
import com.application.rozaana.ui.home.childFragments.MediaPageBundle
import com.application.rozaana.util.network.RequestResult
import com.application.rozaana.util.viewModelFactory.ViewModelProviderFactory

class HomeFragment : Fragment() {

    companion object {
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        initViewModel()
        initViews()
        initAdapters()
        initViewModelCalls()
        initViewModelObservers()
        initPullToRefresh()
    }

    lateinit var viewModel: HomeViewModel
    private fun initViewModel() {
        viewModel =
            ViewModelProvider(
                this,
                ViewModelProviderFactory(HomeViewModel::class) {
                    HomeViewModel(HomeRepository(resources))
                }
            ).get(HomeViewModel::class.java)
    }

    var pageNumber = 1
    private fun initViewModelCalls() {
        viewModel.getLandingScreenData(pageNumber)
    }

    private fun initViewModelObservers() {
        viewModel.pageData.observe(
            viewLifecycleOwner,
            {
                processFlavorDataResult(it)
            }
        )

    }

    lateinit var adapter: HomeAdapter
    private fun initAdapters() {
//        binding.contentRv.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
//        binding.contentRv.addItemDecoration(HomeItemDecorator(requireContext()))
//        adapter = HomeAdapter(requireContext())
//        binding.contentRv.adapter = adapter
    }

    private fun processFlavorDataResult(it: RequestResult<Any>) {
        when(it){
            is RequestResult.Success ->  onLoadDataSuccess((it.data as PexelData).media as MutableList<PexelData.Media>)
            is RequestResult.Loading -> showLoading()
        }
    }

    private fun onLoadDataSuccess(data: MutableList<PexelData.Media>) {
        hideLoading()
        initViewPagerAdapter(data)
    }

    lateinit var viewpager2: ViewPager2Adapter
    private fun initViews() {
        viewpager2 = ViewPager2Adapter(
            childFragmentManager,
            lifecycle
        )
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
                if(position == viewpager2.itemCount -1) {
                    pageNumber++
                    initViewModelCalls()
                }
            }

            override fun onPageSelected(position: Int) {
                viewpager2.getFragment(position).let {
                    if(it != null) {
                        (it as MediaFragment).playVideo()
                    }
                }
                viewpager2.getFragment(position - 1).let {
                    if(it != null) {
                        (it as MediaFragment).pauseVideo()
                    }
                }
                viewpager2.getFragment(position + 1).let {
                    if(it != null) {
                        (it as MediaFragment).pauseVideo()
                    }
                }
            }
        })
    }

    private fun initViewPagerAdapter(data: MutableList<PexelData.Media>) {
        getFragments(data)
    }

    private fun getFragments(data: MutableList<PexelData.Media>) {
        data.forEach {
            viewpager2.addFrag(MediaFragment.newInstance(MediaPageBundle(it.photographer ?: "", it.src?.portrait ?: it.image ?: "", it.type ?: "None")))
        }
        viewpager2.notifyDataSetChanged()
    }

    private fun initPullToRefresh() {
        binding.pullToRefresh.setOnRefreshListener {
            pageNumber = 1
            viewpager2.clearFrag()
            initViewModelCalls()
            binding.pullToRefresh.isRefreshing = false
        }
    }

    private fun hideLoading() {
        binding.pullToRefresh.isRefreshing = false
    }

    private fun showLoading() {
        binding.pullToRefresh.isRefreshing = true
    }


}