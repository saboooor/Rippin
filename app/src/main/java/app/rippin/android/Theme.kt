package app.rippin.android

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

private val RippinDark = darkColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFFFFB3B5),
    onPrimary = androidx.compose.ui.graphics.Color(0xFF680014),
    primaryContainer = androidx.compose.ui.graphics.Color(0xFF920020),
    onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFFFFDADB),
    secondary = androidx.compose.ui.graphics.Color(0xFFFFB782),
    onSecondary = androidx.compose.ui.graphics.Color(0xFF492900),
    secondaryContainer = androidx.compose.ui.graphics.Color(0xFF693D00),
    onSecondaryContainer = androidx.compose.ui.graphics.Color(0xFFFFDCC0),
    tertiary = androidx.compose.ui.graphics.Color(0xFF7BD0FF),
    onTertiary = androidx.compose.ui.graphics.Color(0xFF003549),
    tertiaryContainer = androidx.compose.ui.graphics.Color(0xFF004D68),
    onTertiaryContainer = androidx.compose.ui.graphics.Color(0xFFC3E8FF),
    background = androidx.compose.ui.graphics.Color(0xFF141212),
    onBackground = androidx.compose.ui.graphics.Color(0xFFECE0E0),
    surface = androidx.compose.ui.graphics.Color(0xFF141212),
    onSurface = androidx.compose.ui.graphics.Color(0xFFECE0E0),
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFF524344),
    onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFFD5C2C3),
    surfaceDim = androidx.compose.ui.graphics.Color(0xFF141212),
    surfaceBright = androidx.compose.ui.graphics.Color(0xFF3B3838),
    surfaceContainerLowest = androidx.compose.ui.graphics.Color(0xFF0F0D0D),
    surfaceContainerLow = androidx.compose.ui.graphics.Color(0xFF1D1B1B),
    surfaceContainer = androidx.compose.ui.graphics.Color(0xFF211B1C),
    surfaceContainerHigh = androidx.compose.ui.graphics.Color(0xFF2C2526),
    surfaceContainerHighest = androidx.compose.ui.graphics.Color(0xFF383031),
    outline = androidx.compose.ui.graphics.Color(0xFF9F8C8D),
    outlineVariant = androidx.compose.ui.graphics.Color(0xFF524344),
)

private val RippinLight = lightColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFFBA1A1A),
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = androidx.compose.ui.graphics.Color(0xFFFFDAD6),
    onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFF410002),
    secondary = androidx.compose.ui.graphics.Color(0xFF8A5000),
    onSecondary = androidx.compose.ui.graphics.Color.White,
    secondaryContainer = androidx.compose.ui.graphics.Color(0xFFFFDDB5),
    onSecondaryContainer = androidx.compose.ui.graphics.Color(0xFF2C1600),
    tertiary = androidx.compose.ui.graphics.Color(0xFF00668B),
    onTertiary = androidx.compose.ui.graphics.Color.White,
    tertiaryContainer = androidx.compose.ui.graphics.Color(0xFFC3E8FF),
    onTertiaryContainer = androidx.compose.ui.graphics.Color(0xFF001E2C),
    background = androidx.compose.ui.graphics.Color(0xFFFFF8F7),
    onBackground = androidx.compose.ui.graphics.Color(0xFF221919),
    surface = androidx.compose.ui.graphics.Color(0xFFFFF8F7),
    onSurface = androidx.compose.ui.graphics.Color(0xFF221919),
    surfaceVariant = androidx.compose.ui.graphics.Color(0xFFF4DDDD),
    onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF524344),
    surfaceDim = androidx.compose.ui.graphics.Color(0xFFE7D6D5),
    surfaceBright = androidx.compose.ui.graphics.Color(0xFFFFF8F7),
    surfaceContainerLowest = androidx.compose.ui.graphics.Color.White,
    surfaceContainerLow = androidx.compose.ui.graphics.Color(0xFFFFF0EF),
    surfaceContainer = androidx.compose.ui.graphics.Color(0xFFF5EDEA),
    surfaceContainerHigh = androidx.compose.ui.graphics.Color(0xFFEFE7E5),
    surfaceContainerHighest = androidx.compose.ui.graphics.Color(0xFFE9E1DF),
    outline = androidx.compose.ui.graphics.Color(0xFF857374),
    outlineVariant = androidx.compose.ui.graphics.Color(0xFFD7C1C2),
)

private val RippinShapes = Shapes(
    extraSmall = RoundedCornerShape(10.dp),
    small = RoundedCornerShape(16.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(26.dp),
    extraLarge = RoundedCornerShape(34.dp),
)

private val RippinTypography = Typography().let { base ->
    base.copy(
        displaySmall = base.displaySmall.copy(fontWeight = FontWeight.Bold, letterSpacing = (-0.5).sp),
        headlineLarge = base.headlineLarge.copy(fontWeight = FontWeight.Bold, letterSpacing = (-0.4).sp),
        headlineMedium = base.headlineMedium.copy(fontWeight = FontWeight.SemiBold, letterSpacing = (-0.2).sp),
        headlineSmall = base.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
        titleLarge = base.titleLarge.copy(fontWeight = FontWeight.SemiBold, letterSpacing = 0.sp),
        titleMedium = base.titleMedium.copy(fontWeight = FontWeight.SemiBold),
        titleSmall = base.titleSmall.copy(fontWeight = FontWeight.Medium),
        labelLarge = base.labelLarge.copy(fontWeight = FontWeight.SemiBold, letterSpacing = 0.1.sp),
        labelMedium = base.labelMedium.copy(fontWeight = FontWeight.Medium),
        labelSmall = base.labelSmall.copy(fontWeight = FontWeight.Medium),
        bodyLarge = base.bodyLarge.copy(lineHeight = 24.sp),
        bodyMedium = base.bodyMedium.copy(lineHeight = 21.sp),
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun RippinTheme(
    dark: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val colors: ColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> if (dark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        dark -> RippinDark
        else -> RippinLight
    }
    MaterialExpressiveTheme(
        colorScheme = colors,
        motionScheme = MotionScheme.expressive(),
        typography = RippinTypography,
        shapes = RippinShapes,
        content = content,
    )
}
