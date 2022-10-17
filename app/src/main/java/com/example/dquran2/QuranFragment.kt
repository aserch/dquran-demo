package com.example.dquran2

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.dquran2.ui.juz.JuzFragment
import com.example.dquran2.surah.PageFragment
import com.example.dquran2.ui.surah.SurahFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class QuranFragment : Fragment(R.layout.fragment_quran) {
    private val tabViewModel:TabViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager2)
        val fragmentList = listOf<Fragment>(SurahFragment(), JuzFragment(),PageFragment())
        val adapter = ViewPagerAdapter(requireParentFragment(),fragmentList)
        viewPager.adapter = adapter

        val titles = listOf<String>("Surat","Juz","Halaman")
        TabLayoutMediator(tabLayout, viewPager){tab,postion ->
            tab.text = titles[postion]
        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabViewModel.setIndex(position)
            }
        })
    }
}