package com.example.dquran2.ui.surah

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dquran2.data.Surah

class SurahViewModel : ViewModel(){

    private val listTotalAyah : MutableLiveData<List<Int>> = MutableLiveData()

    fun setAyahList(surahList: List<Surah>){
        val mutableList = mutableListOf<Int>()
        surahList.forEach {
            val totalAyah = it.totalAyah
            mutableList.add(totalAyah)
        }
        listTotalAyah.value = mutableList
    }

    fun getayahList() = listTotalAyah
}