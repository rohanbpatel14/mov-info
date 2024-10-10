package com.example.moviereview.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var fragmentList = mutableListOf<FragmentPage>()

    override fun getPageTitle(position: Int): CharSequence? = fragmentList[position].title
    override fun getItem(position: Int): Fragment = fragmentList[position].fragment
    override fun getCount(): Int = fragmentList.size

    fun addPage(title: String, fragment: Fragment) {
        fragmentList.add(FragmentPage(title, fragment))
    }

    class FragmentPage(val title: String, val fragment: Fragment)

}
