package com.example.hirely.ui.theme

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
    primary = PrimaryBlue,
    secondary = DarkColor,
    tertiary = TextGray
)

// Light theme color scheme
private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = DarkColor,
    tertiary = TextGray,

    // App ka main background
    background = White,

    // Surface ka background
    surface = White,

    // Primary color ke upar text/icon
    onPrimary = White,

    // Secondary color ke upar text/icon
    onSecondary = White,

    // Background ke upar text
    onBackground = TextDark,

    // Surface ke upar text
    onSurface = TextDark
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
        darkTheme -> DarkColorScheme

        // Normal light mode
        else -> LightColorScheme
    }

    // Puri application par selected theme apply karta hai
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}