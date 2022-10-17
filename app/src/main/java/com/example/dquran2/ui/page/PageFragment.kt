package com.example.dquran2.surah

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dquran2.R
import com.example.dquran2.data.QuranDatabase
import com.example.dquran2.ui.page.PageAdapter
import kotlinx.coroutines.launch

class PageFragment : Fragment(R.layout.fragment_page) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_page)
        val database = QuranDatabase.getInstance(requireContext())

        lifecycleScope.launch {
            val pageList = database.quranDao().getPageList()
            val adapter = PageAdapter(pageList)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter.itemClickListener = {
                val bundle = bundleOf("page_number" to it.pageNumber,)
                findNavController().navigate(R.id.action_quranFragment_to_ayatFragment,bundle)
            }
        }
    }

}