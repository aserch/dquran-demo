package com.example.dquran2.ui.juz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dquran2.R
import com.example.dquran2.data.Juz
import com.google.android.material.card.MaterialCardView


class JuzAdapter(val juzList: List<Juz>): RecyclerView.Adapter<JuzAdapter.JuzViewHolder>() {

    var itemClickListener : ((Juz) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JuzViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_juz,parent,false)
        return JuzViewHolder(view)
    }

    override fun onBindViewHolder(holder: JuzViewHolder, position: Int) {
        val juz = juzList[position]
        holder.bindView(juz)
        holder.card.setOnClickListener {
            itemClickListener?.invoke(juz)
        }
    }

    override fun getItemCount(): Int {
        return juzList.size
    }


    class JuzViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textJuzName = itemView.findViewById<TextView>(R.id.txt_juz_name)
        val textJuzStart = itemView.findViewById<TextView>(R.id.txt_juz_start)
        val textJuzNumber = itemView.findViewById<TextView>(R.id.txt_juz_number)
        val card = itemView.findViewById<MaterialCardView>(R.id.card_juz)

        fun bindView(juz: Juz){
            textJuzName.text = "Juz ${juz.juzNumber}"
            textJuzStart.text = "${juz.surahName} : ${juz.ayahNumber}"
            textJuzNumber.text = juz.juzNumber.toString()
        }

    }
}