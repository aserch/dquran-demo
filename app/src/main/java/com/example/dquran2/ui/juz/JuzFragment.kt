package com.example.dquran2.ui.juz

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dquran2.R
import com.example.dquran2.data.QuranDatabase
import com.example.dquran2.ui.surah.SurahViewModel
import kotlinx.coroutines.launch

class JuzFragment: Fragment(R.layout.fragment_juz) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_juz)
        val database = QuranDatabase.getInstance(requireContext())

        lifecycleScope.launch {
            val juzList = database.quranDao().getJuzList()
            val adapter = JuzAdapter(juzList)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter.itemClickListener = {
                val bundle = bundleOf("juz_number" to it.juzNumber,)
                findNavController().navigate(R.id.action_quranFragment_to_ayatFragment,bundle)
            }
        }
    }
}
