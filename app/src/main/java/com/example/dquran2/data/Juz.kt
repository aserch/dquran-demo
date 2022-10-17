package com.example.dquran2.data

import androidx.room.ColumnInfo
import androidx.room.DatabaseView


@DatabaseView("SELECT jozz, sora_name_en, aya_no FROM quran GROUP BY jozz")
data class Juz(
    @ColumnInfo(name="jozz")
    val juzNumber: Int,

    @ColumnInfo(name="sora_name_en")
    val surahName: String,

    @ColumnInfo(name="aya_no")
    val ayahNumber: Int,
)
