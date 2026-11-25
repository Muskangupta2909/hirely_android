package com.example.hirely.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Dark theme color scheme
private val DarkColorScheme = darkColorScheme(
    primary = _root_ide_package_.com.example.hirely.presentation.ui.theme.PrimaryBlue,
    secondary = _root_ide_package_.com.example.hirely.presentation.ui.theme.DarkColor,
    tertiary = _root_ide_package_.com.example.hirely.presentation.ui.theme.TextGray
)

// Light theme color scheme
private val LightColorScheme = lightColorScheme(
    primary = _root_ide_package_.com.example.hirely.presentation.ui.theme.PrimaryBlue,
    secondary = _root_ide_package_.com.example.hirely.presentation.ui.theme.DarkColor,
    tertiary = _root_ide_package_.com.example.hirely.presentation.ui.theme.TextGray,

    // App ka main background
    background = _root_ide_package_.com.example.hirely.presentation.ui.theme.White,

    // Surface ka background
    surface = _root_ide_package_.com.example.hirely.presentation.ui.theme.White,

    // Primary color ke upar text/icon
    onPrimary = _root_ide_package_.com.example.hirely.presentation.ui.theme.White,

    // Secondary color ke upar text/icon
    onSecondary = _root_ide_package_.com.example.hirely.presentation.ui.theme.White,

    // Background ke upar text
    onBackground = _root_ide_package_.com.example.hirely.presentation.ui.theme.TextDark,

    // Surface ke upar text
    onSurface = _root_ide_package_.com.example.hirely.presentation.ui.theme.TextDark
)

@Composable
fun HirelyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),

    // Android 12+ ka dynamic color disable kar rahe hain
    // taaki phone ka default pink/purple color na aaye
    dynamicColor: Boolean = false,

    content: @Composable () -> Unit
) {

    // Decide karta hai ki Light ya Dark theme use karni hai
    val colorScheme = when {

        // Dynamic color agar future mein enable karein
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current

            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        // Dark mode
        darkTheme -> _root_ide_package_.com.example.hirely.presentation.ui.theme.DarkColorScheme

        // Normal light mode
        else -> _root_ide_package_.com.example.hirely.presentation.ui.theme.LightColorScheme
    }

    // Puri application par selected theme apply karta hai
    MaterialTheme(
        colorScheme = colorScheme,
        typography = _root_ide_package_.com.example.hirely.presentation.ui.theme.Typography,
        content = content
    )
}