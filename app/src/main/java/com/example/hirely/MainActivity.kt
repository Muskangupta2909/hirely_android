package com.example.hirely

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.hirely.navigation.NavGraph
import com.example.hirely.ui.theme.HirelyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Default ActionBar / "Hirely" title bar hide kar rahe hain
        actionBar?.hide()

        enableEdgeToEdge()

        setContent {
            HirelyTheme {
                // App ka navigation yahan se start hoga
                NavGraph()
            }
        }
    }
}