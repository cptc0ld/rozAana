package com.application.rozaana.baseui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        fragments: List<Fragment>
    ) : this(fragmentManager, lifecycle) {
        mFragmentList.addAll(fragments)
    }

    private val mFragmentList = mutableListOf<Fragment>()

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    fun addFrag(fragment: Fragment) {
        mFragmentList.add(fragment)
    }

    fun removeFrag(position: Int) {
        mFragmentList.removeAt(position)
        notifyItemRangeChanged(position, mFragmentList.size)
    }

    fun clearFrag() {
        mFragmentList.clear()
        notifyItemRangeChanged(0, mFragmentList.size)
    }

    fun getFragment(position: Int): Fragment? {
        return if(position >=0 && position < mFragmentList.size){
            mFragmentList[position]
        } else {
            null
        }
    }
}
