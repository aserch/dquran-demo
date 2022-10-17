package com.example.dquran2.ui.surah

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dquran2.R
import com.example.dquran2.data.Surah
import com.google.android.material.card.MaterialCardView

class SurahAdapter(val surahList: List<Surah>): RecyclerView.Adapter<SurahAdapter.SurahViewHolder>() {

    var itemClickListener : ((Surah) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurahViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_surah,parent,false)
        return SurahViewHolder(view)
    }

    override fun onBindViewHolder(holder: SurahViewHolder, position: Int) {
        val surah = surahList[position]
        holder.bindView(surah)
        holder.card.setOnClickListener {
            itemClickListener?.invoke(surah)
        }
    }

    override fun getItemCount(): Int {
        return surahList.size
    }

    class SurahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textSurahName = itemView.findViewById<TextView>(R.id.txt_surah_name)
        val textSurahArabic = itemView.findViewById<TextView>(R.id.txt_surah_arab)
        val textSurahNumber = itemView.findViewById<TextView>(R.id.txt_surah_number)
        val totalAyah = itemView.findViewById<TextView>(R.id.txt_total_ayat)
        val card = itemView.findViewById<MaterialCardView>(R.id.card_surah)

        fun bindView(surah: Surah){
            textSurahName.text = surah.surahName
            textSurahArabic.text = surah.surahNameArabic
            textSurahNumber.text = surah.surahNumber.toString()
            totalAyah.text = "${surah.totalAyah} ayat"
        }

    }
}