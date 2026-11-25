package com.example.hirely

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.hirely.presentation.navigation.NavGraph
import com.example.hirely.presentation.ui.screen.wallet.WalletScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Default ActionBar / "Hirely" title bar hide kar rahe hain
        actionBar?.hide()

        enableEdgeToEdge()

        setContent {
            _root_ide_package_.com.example.hirely.presentation.ui.theme.HirelyTheme {
                // App ka navigation yahan se start hoga
               // NavGraph()
                WalletScreen(
                    onBackClick = {
                        finish()
                    }
                )
            }
        }
    }
}