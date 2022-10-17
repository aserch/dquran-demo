@file:OptIn(InternalCoroutinesApi::class)

package com.example.dquran2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dquran2.R
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(version = 1,
entities = [Quran::class],
views = [Surah::class,Juz::class,Page::class])
abstract class QuranDatabase : RoomDatabase() {
    abstract fun quranDao(): QuranDao

    companion object{
        @Volatile
        private var INSTANCE: QuranDatabase? = null
        fun getInstance(context: Context): QuranDatabase {
            return INSTANCE ?: synchronized(this){
                INSTANCE ?: Room.databaseBuilder(
                    context,
                    QuranDatabase::class.java,
                    "quran.db"
                ).createFromInputStream{
                    context.resources.openRawResource(R.raw.quran)
                }.build()

            }

        }
    }

}