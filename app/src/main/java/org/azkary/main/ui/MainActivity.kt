package org.azkary.main.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import org.azkary.R
import org.azkary.worker.NotificationWorker
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {


    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_App)
        super.onCreate(savedInstanceState)
        startNotificationWorker()

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

    }

    private fun startNotificationWorker() {
        NotificationWorker.startNotificationWorker(applicationContext, sharedPreferences, this)
    }
}