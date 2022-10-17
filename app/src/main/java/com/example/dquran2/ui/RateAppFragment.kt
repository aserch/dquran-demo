package com.example.dquran2.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dquran2.R
import com.google.android.material.button.MaterialButton

class RateAppFragment: Fragment(R.layout.fragment_rate_app) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnRate = view.findViewById<MaterialButton>(R.id.btn_rate_app)

        btnRate.setOnClickListener{
            Toast.makeText(requireContext(), "Belum Tersedia di Play Store", Toast.LENGTH_SHORT).show()
        }
    }
}