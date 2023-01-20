package com.setik.pupuk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.setik.pupuk.databinding.ActivityMainKiosBinding

class MainKiosActivity : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var pref:preferences

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainKiosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        context = this
        pref = preferences(context)

        binding = ActivityMainKiosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMainKios.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navViewkios
        val navController = findNavController(R.id.nav_host_fragment_content_main_kios)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_homek,), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_kios, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main_kios)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun logout(item: MenuItem) {
        pref.prefClear()
        startActivity(Intent(context, LoginKios::class.java))
        finish()
    }
}