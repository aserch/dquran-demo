package com.example.dquran2.ui.surah

import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SurahFragment : Fragment(R.layout.fragment_surah){

    private val getSurahViewModel : SurahViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_surah)
        val database = QuranDatabase.getInstance(requireContext())


        lifecycleScope.launch {
            val surahList = database.quranDao().getSurahList()
            getSurahViewModel.setAyahList(surahList)
            Log.d("CHECKDATA",surahList.size.toString())
            val adapter = SurahAdapter(surahList)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            adapter.itemClickListener = {
                val bundle = bundleOf("surah_name" to it.surahName, "surah_number" to it.surahNumber)
                findNavController().navigate(R.id.action_quranFragment_to_ayatFragment,bundle)
            }
        }
    }
}
