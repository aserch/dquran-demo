package com.example.dquran2.ui.settings

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dquran2.ui.SelectPref

class SettingViewModel : ViewModel() {
    private val Qari = MutableLiveData<String>()
    private val Translate = MutableLiveData<String>()
    private val Arabic = MutableLiveData<String>()

    init {

    }

    fun getQari(context: Context) : LiveData<String>{
        val currentQari = SelectPref(context).selectQ
        Qari.value = currentQari!!
        return Qari
    }

    fun getTranslate(context: Context) : LiveData<String>{
        val currentTranslate = SelectPref(context).selectT
        Translate.value = currentTranslate!!
        return Translate
    }

    fun getArabic(context: Context) : LiveData<String>{
        val currentArabic = SelectPref(context).selectA
        Arabic.value = currentArabic!!
        return Arabic
    }

    fun getCurrentPref() {

    }


}