package com.example.dquran2

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.dquran2.R
import com.example.dquran2.data.Quran
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.divider.MaterialDivider


class SearchAdapter(
    val ayatList: List<Quran>,
): RecyclerView.Adapter<SearchAdapter.AyatViewHolder>() {

    var itemClickListener : ((Quran) -> Unit)? = null
    var playClickListener : ((Quran) -> Unit)? = null
    var shareItemClickListener : ((Quran) -> Unit)?= null
    var copyItemClickListener : ((Quran) -> Unit)? = null
    var playlistClickListener : ((List<Quran>) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ayat,parent,false)
        return AyatViewHolder(view)
    }

    override fun onBindViewHolder(holder: AyatViewHolder, position: Int) {
        val ayat = ayatList[position]
        holder.bindView(ayat)
        holder.textSurahTitle.isVisible = false
        holder.textBasmalah.isVisible = false
        holder.divider.isVisible = false
        holder.playAll.isVisible = false
        holder.txtTotalAyah.isVisible = false


        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(ayat)
        }
        holder.playAll.setOnClickListener {
            playlistClickListener?.invoke(ayatList)
        }
        holder.playItem.setOnClickListener {
            playClickListener?.invoke(ayat)
        }
        holder.copyItem.setOnClickListener{
            copyItemClickListener?.invoke(ayat)

        }
        holder.shareItem.setOnClickListener{
            shareItemClickListener?.invoke(ayat)
        }
    }

    override fun getItemCount(): Int {
        return ayatList.size
    }


    class AyatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textAyat = itemView.findViewById<TextView>(R.id.txt_ayat_quran)
        val textTranslation = itemView.findViewById<TextView>(R.id.txt_ayat_translation)
        val textSurahTitle = itemView.findViewById<TextView>(R.id.txt_ayat_surah_name)
        val textBasmalah = itemView.findViewById<TextView>(R.id.txt_basmalah)
        val txtTotalAyah = itemView.findViewById<TextView>(R.id.txt_ayat_total_ayat)
        val divider = itemView.findViewById<MaterialDivider>(R.id.mtrl_divider)
        val textSearchAyat = itemView.findViewById(R.id.txt_search_surah_ayat) as TextView

        val copyItem = itemView.findViewById<MaterialCardView>(R.id.card_copy_ayat)
        val shareItem = itemView.findViewById<MaterialCardView>(R.id.card_share_ayat)
        val playItem = itemView.findViewById<MaterialCardView>(R.id.card_play_ayat)
        val playAll = itemView.findViewById(R.id.btn_play_all) as MaterialButton




        @SuppressLint("SetTextI18n")
        fun bindView(quran: Quran){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                textAyat.text = quran.ayahText
            }else{
                textAyat.text = reverseAyatNumber(quran.ayahText.toString())
            }
            textTranslation.text = "${quran.ayahNumber}. ${quran.translation}"
            textSearchAyat.text = "${quran.SurahName} : ${quran.ayahNumber}"

        }

        fun reverseAyatNumber(ayatText : String) : String{
            val digit = mutableListOf<Char>()
            ayatText.forEach {
                if (it.isDigit()){
                    digit.add(it)
                }
            }
            val reverseDigit = digit.reversed()
            return ayatText.replace(digit.joinToString(""), reverseDigit.joinToString(""))
        }
    }
}