package com.example.voicesofheart.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = HeartRed80,
    onPrimary = OnDarkPrimary,
    primaryContainer = HeartRedContainer80,
    secondary = PlumGrey80,
    tertiary = GoldAccent80,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onBackground = OnDarkSurface,
    onSurface = OnDarkSurface
)

private val LightColorScheme = lightColorScheme(
    primary = HeartRed40,
    primaryContainer = HeartRedContainer40,
    secondary = PlumGrey40,
    tertiary = GoldAccent40,
    background = LightBackground,
    surface = LightSurface,
    onBackground = OnLightSurface,
    onSurface = OnLightSurface
)

@Composable
fun VoicesofheartTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Dynamic color intentionally disabled: this app has its own brand
    // palette, and dynamic color only applies on Android 12+ anyway
    // (your phone is Android 11).
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}