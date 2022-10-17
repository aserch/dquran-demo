package com.example.dquran2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatDelegate
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.dquran2.databinding.ActivityMainDrawerLayoutBinding
import com.example.dquran2.ui.SharedPref
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var drawerLayout: DrawerLayout
    private val binding : ActivityMainDrawerLayoutBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_drawer_layout)

        val toolbar = findViewById<CoordinatorLayout>(R.id.app_bar_main_layout)
            .findViewById<MaterialToolbar>(R.id.material_toolbar)

        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.dashboardFragment
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)
        binding.nbMain.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, _ ->
            when(destination.id){
                R.id.quranFragment  -> {
                    binding.nbMain.isVisible = false
                }
                R.id.ayatFragment -> {
                    binding.nbMain.isVisible = false
                }

                R.id.itm_nav_about_us -> {
                    binding.nbMain.isVisible= false
                }

                R.id.itm_nav_rate_app -> {
                    binding.nbMain.isVisible = false
                }
                R.id.footnotesFragment -> {
                    binding.nbMain.isVisible= false
                }
                else -> binding.nbMain.isVisible = true
            }
        }


        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.itm_search -> {
                    val intent = Intent(this,SearchActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_bar_menu, menu)
        return true
    }

    private fun setTheme() {
        // bikin fun buat get tema dari system android / HP / mobile
        // setTheme di panggil sebelum "super.onCreate(savedInstanceState)"

        when (SharedPref(this).theme) {
            "light" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                // atur nilai "light" / 0
            }
            "dark" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                // atur nilai "dark" / 1
            }
        }
    }
}