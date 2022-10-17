package com.example.dquran2.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dquran2.R
import com.example.dquran2.ui.SelectPref
import com.example.dquran2.ui.SharedPref
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var switchTheme: SwitchMaterial
    private lateinit var switchTranslate: SwitchMaterial
    private lateinit var qariSelect: TextInputLayout
    private lateinit var translationSelect: TextInputLayout
    private lateinit var arabicSelect: TextInputLayout
    val viewModel : SettingViewModel by viewModels()

//    private lateinit var binding : FragmentSettingsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTheme()
        setSelect()

        switchTheme = view.findViewById(R.id.switch_theme)
        switchTranslate = view.findViewById(R.id.switch_no_translate)

        val qari = view.findViewById<AutoCompleteTextView>(R.id.actv_qari)
        val translation = view.findViewById<AutoCompleteTextView>(R.id.actv_ts)
        val arabic = view.findViewById<AutoCompleteTextView>(R.id.actv_as)

        qariSelect = view.findViewById(R.id.input_layout_qari)
        translationSelect = view.findViewById(R.id.input_layout_ts)
        arabicSelect = view.findViewById(R.id.input_layout_as)

        val items = listOf("Hani ar Rifai", "Mishary Rasheed Alafasy", "Sahih Ibrahim")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_popup_item, items)
        qari.setAdapter(adapter)

        val items2 = listOf("Small", "Medium", "Large")
        val adapter2 = ArrayAdapter(requireContext(), R.layout.list_popup_item, items2)
        translation.setAdapter(adapter2)

        val items3 = listOf("Small", "Medium", "Large")
        val adapter3 = ArrayAdapter(requireContext(), R.layout.list_popup_item, items3)
        arabic.setAdapter(adapter3)

        qari.setOnItemClickListener { parent, _, pos, id ->
            SelectPref(requireContext()).selectQ = parent.getItemAtPosition(0).toString()
            SelectPref(requireContext()).selectQ = parent.getItemAtPosition(1).toString()
            SelectPref(requireContext()).selectQ = parent.getItemAtPosition(2).toString()

        }

        translation.setOnItemClickListener { parent, view, position, id ->
           SelectPref(requireContext()).selectT = parent.getItemAtPosition(position).toString()
        }

        arabic.setOnItemClickListener { parent, _, pos, id ->
            SelectPref(requireContext()).selectA = parent.getItemAtPosition(0).toString()
        }

        switchTranslate.setOnCheckedChangeListener { buttonView, isChecked ->
            SelectPref(requireContext()).selectN = isChecked
        }


        switchTheme.setOnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked) {
                true -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    SharedPref(requireContext()).theme = "dark"
                    // set ketika switch.isChecked = true, maka tema gelap

                }
                false -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    SharedPref(requireContext()).theme = "light"
                    // set ketika switch.isChecked = false , maka tema terang
                }
            }
        }
    }

    private fun setTheme() {

        switchTheme = view?.findViewById(R.id.switch_theme)!!
        val imgTheme = view?.findViewById(R.id.img_theme) as ImageView
        val txtTheme = view?.findViewById(R.id.txt_theme) as TextView

        when (SharedPref(requireContext()).theme) {
            "light" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                imgTheme.setImageResource(R.drawable.ic_dark_mode)
                txtTheme.text = "Dark Mode"
                switchTheme.isChecked = false
                // set ketika tema terang maka switch gak nyala
            }
            "dark" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                imgTheme.setImageResource(R.drawable.ic_sunny)
                txtTheme.text = "Light Mode"
                switchTheme.isChecked = true
                // set ketika tema gelap maka switch nyala
            }
        }
    }

    private fun setSelect() {

        qariSelect = view?.findViewById(R.id.input_layout_qari)!!
        translationSelect = view?.findViewById(R.id.input_layout_ts)!!
        arabicSelect = view?.findViewById(R.id.input_layout_as)!!
        switchTranslate = view?.findViewById(R.id.switch_no_translate)!!

//        "Hani_Rifai_192kbps", "Alafasy_128kbps","Sahih_Intnl_Ibrahim_Walk_192kbps"

        when(SelectPref(requireContext()).selectQ){
            "Hani ar Rifai" -> qariSelect.editText?.setText("Hani ar Rifai")
            "Mishary Rasheed Alafasy" -> qariSelect.editText?.setText("Mishary Rasheed Alafasy")
            "Sahih Ibrahim" -> qariSelect.editText?.setText("Sahih Ibrahim")
        }

//        viewModel.getQari(requireContext()).observe(viewLifecycleOwner) { get ->
//            when (get) {
//                "Hani ar Rifai" -> qariSelect.editText?.setText("Hani ar Rifai")
//                "Mishary Rasheed Alafasy" -> qariSelect.editText?.setText("Mishary Rasheed Alafasy")
//                "Sahih Ibrahim" -> qariSelect.editText?.setText("Sahih Ibrahim")
//            }
//        }

        when(SelectPref(requireContext()).selectT){
            "Medium" -> translationSelect.editText?.setText("Medium")
            "Small" -> translationSelect.editText?.setText("Small")
            "Large" -> translationSelect.editText?.setText("Large")
        }

//        viewModel.getTranslate(requireContext()).observe(viewLifecycleOwner){
//            when (it) {
//                "Medium" -> translationSelect.editText?.setText("Medium")
//                "Small" -> translationSelect.editText?.setText("Small")
//                "Large" -> translationSelect.editText?.setText("Large")
//            }
//        }

        when(SelectPref(requireContext()).selectA){
            "Medium" -> arabicSelect.editText?.setText("Medium")
            "Small" -> arabicSelect.editText?.setText("Small")
            "Large" -> arabicSelect.editText?.setText("Large")
        }

//        viewModel.getArabic(requireContext()).observe(viewLifecycleOwner){
//            when (it) {
//                "Medium" -> arabicSelect.editText?.setText("Medium")
//                "Small" -> arabicSelect.editText?.setText("Small")
//                "Large" -> arabicSelect.editText?.setText("Large")
//            }
//        }



        switchTranslate.isChecked = SelectPref(requireContext()).selectN


    }
}