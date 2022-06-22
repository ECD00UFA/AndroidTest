package com.example.androidtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.androidtest.app.AppData
import com.example.androidtest.app.NavigationManager
import com.example.androidtest.app.Navigator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        AppData.navigationManager = NavigationManager(navController)
    }

    override fun onBackPressed() {
        when (Navigator.getCurrentDestinationId()) {
            R.id.fragmentDashboard -> finish()
            else -> super.onBackPressed()
        }
    }
}