package com.example.dquran2.ui

import android.content.Context
import android.preference.PreferenceManager

class SelectPref(context: Context) {
    companion object {
        private val QKEY = "QVALUE"
        private val TKEY = "TVALUE"
        private val AKEY = "AVALUE"
        private val NKEY = "NVALUE"
    }

    private val currentSelectQ = PreferenceManager.getDefaultSharedPreferences(context)
    var selectQ = currentSelectQ.getString(QKEY, "Hani ar Rifai")
        set(value) = currentSelectQ.edit().putString(QKEY, value).apply()

    private val currentSelectT = PreferenceManager.getDefaultSharedPreferences(context)
    var selectT = currentSelectT.getString(TKEY, "Medium")
        set(value) = currentSelectT.edit().putString(TKEY, value).apply()

    private val currentSelectA = PreferenceManager.getDefaultSharedPreferences(context)
    var selectA = currentSelectA.getString(AKEY,"Medium")
        set(value) = currentSelectA.edit().putString(AKEY,value).apply()

    private val currentSelectN = PreferenceManager.getDefaultSharedPreferences(context)
    var selectN = currentSelectN.getBoolean(NKEY,false)
        set(value) = currentSelectN.edit().putBoolean(NKEY,value).apply()


}
