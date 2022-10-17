package com.example.dquran2.ui

import android.content.Context
import android.preference.PreferenceManager

class SharedPref(context: Context) {
    companion object{
        private const val THEME_KEY = "THEME_VALUE"
        // buat ngatur default tema, terserah mau itu gelap atau terang
    }

    private val currentTheme = PreferenceManager.getDefaultSharedPreferences(context)
    // manggil tema dari system di android atau HP / mobile
    var theme = currentTheme.getString(THEME_KEY,"light")
        // disini gw atur default temanya terang
        // bisa diganti jadi ->  var theme = currentTheme.getInt(THEME_KEY,0)
    set(value) = currentTheme.edit().putString(THEME_KEY,value).apply()
    // set agar theme bisa di edit dan di apply
}