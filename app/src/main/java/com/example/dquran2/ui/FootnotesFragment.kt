package com.example.dquran2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.dquran2.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FootnotesFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.footnotes_bottom_sheets, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById(R.id.footnotes_toolbar) as MaterialToolbar
        val txtFootnotes = view.findViewById(R.id.txt_footnotes) as TextView
        val footnotes = arguments?.getString(TAG_FOOTNOTES)
        val surahName = arguments?.getString(TAG_SURAH_NAME)
        val ayahNumber = arguments?.getInt(TAG_AYAH_NUMBER, 1)


        txtFootnotes.text = footnotes.toString().replace("\n", "\n\n")
        FootnotesSuperscript().footnotesSuperscript(txtFootnotes, footnotes.toString())




        toolbar.subtitle = "$surahName : $ayahNumber"
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.itm_copy -> {
                    true
                }
                R.id.itm_share -> {
                    true
                }
                else -> false
            }
        }
        toolbar.setNavigationOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val TAG_FOOTNOTES = "footnotes"
        const val TAG_SURAH_NAME = "surah_name"
        const val TAG_AYAH_NUMBER = "ayat_number"
    }

}