package com.dbottillo.departnow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import com.dbottillo.departnow.designsystem.DepartNowAppTheme
import com.dbottillo.departnow.ui.DepartNowApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val darkTheme = isSystemInDarkTheme()
            DepartNowAppTheme(
                darkTheme = darkTheme
            ) {
                DepartNowApp()
            }
        }
    }
}
