package com.example.myfinances2020

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.myfinances2020.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupNavigation()
    }

    private fun setupNavigation() {
        bottomNavController = Navigation.findNavController(this, R.id.navHostFragmentBottom)
        // val overflowNavController = Navigation.findNavController(this, R.id.navHostFragmentTop)

        binding.bottomNavigation.setupWithNavController(bottomNavController)
        // setupActionBarWithNavController(overflowNavController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_financeslogo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        NavigationUI.onNavDestinationSelected(item, bottomNavController)
        return super.onOptionsItemSelected(item)
    }
}
