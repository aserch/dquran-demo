package com.example.dquran2.data

import androidx.room.ColumnInfo
import androidx.room.DatabaseView


@DatabaseView("SELECT sora, sora_name_en, sora_name_ar, COUNT(aya_no) as total_ayah FROM quran GROUP BY sora")
data class Surah(
    @ColumnInfo(name="sora")
    val surahNumber: Int,

    @ColumnInfo(name="total_ayah")
    val totalAyah: Int,

    @ColumnInfo(name="sora_name_en")
    val surahName: String,

    @ColumnInfo(name="sora_name_ar")
    val surahNameArabic: String,
)
