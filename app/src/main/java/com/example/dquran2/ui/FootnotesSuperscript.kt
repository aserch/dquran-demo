package com.example.dquran2.ui

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.SuperscriptSpan
import android.view.View
import android.widget.TextView
import com.example.dquran2.R
import java.util.regex.Matcher
import java.util.regex.Pattern

class FootnotesSuperscript() {
    fun footnotesSuperscript(textView: TextView, translation : String){
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
}