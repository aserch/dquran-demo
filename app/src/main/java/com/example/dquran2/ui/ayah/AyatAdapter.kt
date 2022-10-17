package com.example.dquran2.ui.ayah

import android.annotation.SuppressLint
import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.SuperscriptSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.dquran2.R
import com.example.dquran2.data.Quran
import com.example.dquran2.ui.SelectPref
import com.example.dquran2.utils.QuranArabicUtils
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.color.MaterialColors
import com.google.android.material.divider.MaterialDivider
import java.util.regex.Matcher
import java.util.regex.Pattern


class AyatAdapter(
    val ayatList: List<Quran>,
    val getayahList: List<Int>,
): RecyclerView.Adapter<AyatAdapter.AyatViewHolder>() {

    var itemClickListener : ((Quran) -> Unit)? = null
    var playClickListener : ((Quran) -> Unit)? = null
    var shareItemClickListener : ((Quran) -> Unit)?= null
    var copyItemClickListener : ((Quran) -> Unit)? = null
    var playlistClickListener : ((List<Quran>) -> Unit)? = null
    var footnotesClickListener : ((Quran) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AyatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ayat,parent,false)
        return AyatViewHolder(view)
    }

    override fun onBindViewHolder(holder: AyatViewHolder, position: Int) {
        val ayat = ayatList[position]
        val totalAyat = getayahList[ayat.surahNumber
        !! - 1]
        holder.bindView(ayat,totalAyat)
        holder.textSurahTitle.isVisible = ayat.ayahNumber == 1
        holder.textBasmalah.isVisible = ayat.ayahNumber == 1
        holder.divider.isVisible = ayat.ayahNumber == 1
        holder.playAll.isVisible = ayat.ayahNumber == 1
        holder.txtTotalAyah.isVisible = ayat.ayahNumber == 1
        holder.textSearchAyat.isVisible = false


        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(ayat)
        }
        holder.textTranslation.setOnClickListener {
            footnotesClickListener?.invoke(ayat)
        }
        holder.footnotesSuperscript(holder.textTranslation,ayat.translation.toString())
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
        fun bindView(quran: Quran,totalAyah : Int) {

            val ayat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                quran.ayahText
            } else {
                reverseAyatNumber(quran.ayahText.toString())
            }

            val context = itemView.context

//          untuk mencegah crash ketika terjadi error
            try {
                textAyat.text = QuranArabicUtils.getTajweed(context, ayat)
            } catch (e: Exception) {
                textAyat.text = ayat
            }

//            textTranslation.text = "${quran.ayahNumber}. ${quran.translation}"
            textSurahTitle.text = "Surat ${quran.SurahName}"
            txtTotalAyah.text = "$totalAyah ayah"

            if (quran.surahNumber == 1 || quran.surahNumber == 9) {
                textBasmalah.text =
                    itemView.resources.getString(R.string.Before_In_the_name_of_Allah)
            } else {
                textBasmalah.text = itemView.resources.getString(R.string.In_the_name_of_Allah)
            }

            footnotesSuperscript(textTranslation, quran.translation.toString())

            when (SelectPref(itemView.context).selectT) {
                "Medium" -> textTranslation.textSize = 20F
                "Small" -> textTranslation.textSize = 16F
                "Large" -> textTranslation.textSize = 26F
            }

            when (SelectPref(itemView.context).selectA) {
                "Medium" -> textAyat.textSize = 20F
                "Small" -> textAyat.textSize = 16F
                "Large" -> textAyat.textSize = 26F
            }

            when (SelectPref(itemView.context).selectN) {
                true -> textTranslation.isVisible = false
                false -> textTranslation.isVisible = true
            }

        }
        fun footnotesSuperscript(textView: TextView,translation : String){
            val color = textView.resources.getColor(R.color.color_footnotes)


            val sb = SpannableStringBuilder(translation)
            val p : Pattern = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE)
            val m: Matcher = p.matcher(translation)
            while (m.find()){
                val clickableSpan = object : ClickableSpan(){
                    override fun updateDrawState(textPaint: TextPaint) {
                        textPaint.color = color
                        textPaint.isUnderlineText = true
                        textPaint.textSize = 28F
                    }
                    override fun onClick(view: View) {

                    }
                }
                sb.setSpan(clickableSpan, m.start(), m.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                sb.setSpan(
                    SuperscriptSpan(),
                    m.start(),
                    m.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            textView.text = sb
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