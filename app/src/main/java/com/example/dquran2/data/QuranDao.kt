package com.example.dquran2.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface QuranDao {

    @Query("SELECT * FROM surah")
    suspend fun getSurahList(): List<Surah>

    @Query("SELECT * FROM juz")
    suspend fun getJuzList(): List<Juz>

    @Query("SELECT * FROM page")
    suspend fun getPageList(): List<Page>

    @Query("SELECT * FROM quran WHERE sora = :surahNumber")
    suspend fun getAyahFromSurah(surahNumber: Int): List<Quran>

    @Query("SELECT * FROM quran WHERE jozz = :juzNumber")
    suspend fun getAyahFromJuz(juzNumber: Int): List<Quran>

    @Query("SELECT * FROM quran WHERE page = :pageNumber")
    suspend fun getAyahFromPage(pageNumber: Int): List<Quran>

    @Query("SELECT * FROM quran WHERE translation LIKE :querySearch ")
    suspend fun searchAyah (querySearch : String) : List<Quran>


}