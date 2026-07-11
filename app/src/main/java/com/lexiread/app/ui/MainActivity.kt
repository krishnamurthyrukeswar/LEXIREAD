package com.lexiread.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.lexiread.app.R
import com.lexiread.app.data.local.datastore.UserPreferences
import com.lexiread.app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Single Activity architecture. All screens are Fragments
 * managed by the Jetpack Navigation Component.
 *
 * Applies the user's saved theme on startup before setContentView().
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var userPreferences: UserPreferences

    // Top-level destinations where the back button is NOT shown
    private val topLevelDestinations = setOf(
        R.id.splashFragment,
        R.id.authFragment,
        R.id.homeFragment,
        R.id.settingsFragment,
        R.id.annotationsFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        // Apply saved theme after Hilt injection
        applySavedTheme()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        setSupportActionBar(binding.toolbar)
        setupNavigation()
    }

    /**
     * Read the theme mode from DataStore (blocking first() on the IO thread)
     * and apply the correct night mode before setContentView().
     */
    private fun applySavedTheme() {
        lifecycleScope.launch {
            val mode = userPreferences.themeMode.first()
            val nightMode = when (mode) {
                1 -> AppCompatDelegate.MODE_NIGHT_NO
                2 -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            }
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(topLevelDestinations)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Bottom navigation
        binding.bottomNavigation.setupWithNavController(navController)

        // Hide bottom nav and toolbar on splash/auth screens
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment, R.id.authFragment -> {
                    binding.bottomNavigation.visibility = android.view.View.GONE
                    supportActionBar?.hide()
                }
                R.id.readerFragment -> {
                    binding.bottomNavigation.visibility = android.view.View.GONE
                    supportActionBar?.hide()
                }
                else -> {
                    binding.bottomNavigation.visibility = android.view.View.VISIBLE
                    supportActionBar?.show()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
