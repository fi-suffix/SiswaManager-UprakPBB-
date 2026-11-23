package com.example.aplikasidatassiswa.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class AppPalette(
    val primary: Color,
    val onPrimary: Color,
    val screen: Color,
    val surface: Color,
    val border: Color,
    val text: Color,
    val muted: Color,
    val success: Color,
    val successBg: Color,
    val infoBg: Color,
    val warning: Color,
    val warningBright: Color,
    val warningBg: Color
)

object AppColors {
    val Light = AppPalette(
        primary = Color(0xFF2563EB),
        onPrimary = Color(0xFFFFFFFF),
        screen = Color(0xFFF5F7FB),
        surface = Color(0xFFFFFFFF),
        border = Color(0xFFE6EAF1),
        text = Color(0xFF172033),
        muted = Color(0xFF718096),
        success = Color(0xFF159B67),
        successBg = Color(0xFFE7F7EF),
        infoBg = Color(0xFFEAF1FF),
        warning = Color(0xFFA96A12),
        warningBright = Color(0xFFB7791F),
        warningBg = Color(0xFFFFF5E5)
    )

    val Dark = AppPalette(
        primary = Color(0xFF5B8CFF),
        onPrimary = Color(0xFF0B1220),
        screen = Color(0xFF0B1220),
        surface = Color(0xFF151E2E),
        border = Color(0xFF26334A),
        text = Color(0xFFE8EDF5),
        muted = Color(0xFF94A3B8),
        success = Color(0xFF34D399),
        successBg = Color(0xFF123B31),
        infoBg = Color(0xFF172A52),
        warning = Color(0xFFF0B866),
        warningBright = Color(0xFFF0B866),
        warningBg = Color(0xFF3A2A12)
    )

    private val LocalPalette = compositionLocalOf { Light }

    val current: AppPalette
        @Composable get() = LocalPalette.current

    @Composable
    fun Provide(darkTheme: Boolean, content: @Composable () -> Unit) {
        CompositionLocalProvider(LocalPalette provides if (darkTheme) Dark else Light) {
            content()
        }
    }
}

object AppTypography {
    val screenTitle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
    val sectionTitle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
    val brand = TextStyle(fontSize = 25.sp, fontWeight = FontWeight.Bold)
    val logo = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold)
    val welcome = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
    val heroValue = TextStyle(fontSize = 38.sp, fontWeight = FontWeight.Bold)
    val statValue = TextStyle(fontSize = 23.sp, fontWeight = FontWeight.Bold)
    val profileName = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold)
    val hello = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
    val cardTitle = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    val cardTitleSmall = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    val button = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    val buttonSmall = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    val labelUpper = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    val navActive = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
    val greeting = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Medium)
    val meta = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Medium)
    val metaSmall = TextStyle(fontSize = 9.sp, fontWeight = FontWeight.Medium)
    val body = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal)
    val bodySmall = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Normal)
    val sectionHead = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold)
    val avatarLarge = TextStyle(fontSize = 21.sp, fontWeight = FontWeight.Bold)
    val avatarMedium = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)
    val avatarSmall = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Bold)
    val chevron = TextStyle(fontSize = 23.sp, fontWeight = FontWeight.Normal)
    val plus = TextStyle(fontSize = 21.sp, fontWeight = FontWeight.Medium)
    val logoutIcon = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
    val arrow = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
}

private val LightColors = lightColorScheme(
    primary = AppColors.Light.primary,
    onPrimary = AppColors.Light.onPrimary,
    primaryContainer = AppColors.Light.infoBg,
    onPrimaryContainer = AppColors.Light.primary,
    secondary = AppColors.Light.success,
    onSecondary = Color.White,
    secondaryContainer = AppColors.Light.successBg,
    onSecondaryContainer = AppColors.Light.success,
    tertiary = AppColors.Light.warning,
    onTertiary = Color.White,
    background = AppColors.Light.screen,
    onBackground = AppColors.Light.text,
    surface = AppColors.Light.surface,
    onSurface = AppColors.Light.text,
    surfaceVariant = AppColors.Light.screen,
    onSurfaceVariant = AppColors.Light.muted,
    outline = AppColors.Light.border,
    error = Color(0xFFD93025)
)

private val DarkColors = darkColorScheme(
    primary = AppColors.Dark.primary,
    onPrimary = AppColors.Dark.onPrimary,
    primaryContainer = AppColors.Dark.infoBg,
    onPrimaryContainer = AppColors.Dark.primary,
    secondary = AppColors.Dark.success,
    onSecondary = AppColors.Dark.onPrimary,
    secondaryContainer = AppColors.Dark.successBg,
    onSecondaryContainer = AppColors.Dark.success,
    tertiary = AppColors.Dark.warning,
    onTertiary = AppColors.Dark.onPrimary,
    background = AppColors.Dark.screen,
    onBackground = AppColors.Dark.text,
    surface = AppColors.Dark.surface,
    onSurface = AppColors.Dark.text,
    surfaceVariant = AppColors.Dark.surface,
    onSurfaceVariant = AppColors.Dark.muted,
    outline = AppColors.Dark.border,
    error = Color(0xFFF87171)
)

@Composable
fun AplikasiDataSiswaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    AppColors.Provide(darkTheme) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColors else LightColors,
            content = content
        )
    }
}
