package com.example.dquran2

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView

class DashboardFragment: Fragment(R.layout.fragment_dashboard) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardQuran = view.findViewById<MaterialCardView>(R.id.card_al_quran)

        cardQuran.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_quranFragment)
        }
    }
    // test
}
