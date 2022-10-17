package com.example.dquran2

import androidx.lifecycle.ViewModel

class TabViewModel:ViewModel() {
    private var index = 0

    fun setIndex(newIndex:Int){
        index = newIndex
    }

    fun getIndex():Int{
        return index
    }
}