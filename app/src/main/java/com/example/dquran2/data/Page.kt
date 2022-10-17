package com.example.dquran2.data

import androidx.room.ColumnInfo
import androidx.room.DatabaseView


@DatabaseView("SELECT page, sora_name_en, aya_no FROM quran GROUP BY page")
data class Page(
    @ColumnInfo(name="page")
    val pageNumber: Int,

    @ColumnInfo(name="aya_no")
    val ayahNumber: Int,

    @ColumnInfo(name="sora_name_en")
    val surahName: String,


)
