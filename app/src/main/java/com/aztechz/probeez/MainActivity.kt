/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aztechz.probeez

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.aztechz.probeez.databinding.ActivityMainBinding
import com.aztechz.probeez.model.reports.GenerateReport
import com.aztechz.probeez.ui.task.TaskActivity
import com.aztechz.probeez.utils.Utility
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.greenrobot.eventbus.EventBus

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(findViewById(R.id.toolbar_main))
        setTypeface()
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)


        //Setting the navigation controller to Bottom Nav
        findViewById<BottomNavigationView>(R.id.bottomNav).setupWithNavController(navController)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.tasksFragment,
                R.id.reportsFragment,
                R.id.settingsFragment
            )
        )

        //Setting up the action bar
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        binding.fab.setOnClickListener {
            startActivity(Intent(this@MainActivity,TaskActivity::class.java))
            //navController.navigate(R.id.composeFragment)
        }

        binding.generateReport.setOnClickListener {
          EventBus.getDefault().post(GenerateReport(true))
        }

    }

    private fun setTypeface()
    {
        app_name.typeface = Utility.fontBold
        generateReport.typeface = Utility.fontMedium
    }

    fun setReportVisibility(visibility: Boolean)
    {
        if(visibility)
        {
            generateReport.visibility = View.VISIBLE
        }else{
            generateReport.visibility = View.GONE

        }

    }

    /**
     * Set this Activity's night mode based on a user's in-app selection.
     */
    private fun onDarkThemeMenuItemSelected(itemId: Int): Boolean {
        val nightMode = when (itemId) {
            R.id.menu_light -> AppCompatDelegate.MODE_NIGHT_NO
            R.id.menu_dark -> AppCompatDelegate.MODE_NIGHT_YES
            R.id.menu_battery_saver -> AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            R.id.menu_system_default -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            else -> return false
        }

        delegate.localNightMode = nightMode
        return true
    }

}
