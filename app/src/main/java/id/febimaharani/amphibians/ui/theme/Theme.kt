package id.febimaharani.amphibians.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import android.view.View

// warna untuk tema gelap
private val DarkColorScheme = darkColorScheme(
    background = md_theme_dark_background,
    surface = md_theme_dark_surface,
    surfaceVariant = md_theme_dark_surfaceVariant,
)

// warna untuk tema terang 
private val LightColorScheme = lightColorScheme(
    background = md_theme_light_background,
    surface = md_theme_light_surface,
    surfaceVariant = md_theme_light_surfaceVariant,
)

// fungsi tema utama aplikasi
// untuk menentukan tema gelap ditentukan berdasarkan pengaturan sistem
// menentukan apakah warna dinamis untuk android 12+
@Composable
fun AmphibiansTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // ambil tema gelap dari pengaturan sistem
    dynamicColor: Boolean = false, // mengatur warna dinamis dinonaktifkan.
    content: @Composable () -> Unit // konten yang diterapkan.
) {
    // menentukan warna berdasarkan tema dan dukungan warna dinamis
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context) 
        }
        // menggunakan skema warna gelap/terang jika tema gelap tidak di nonaktifkan
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current // ambil tampilan sekarang
    if (!view.isInEditMode) { // memastikan tidak dalam mode edit
        SideEffect {
            setUpEdgeToEdge(view, darkTheme) // mengatur tampilan edge-to-edge.
        }
    }

    // menerapkan materialTheme dengan skema warna dan typography yang ditentukan
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content // konten yang dirender dengan tema.
    )
}

// Sets up edge-to-edge untuk jendela [view]. warna ikon di atur light/dark tergantung apakah tema gelap aktif.
private fun setUpEdgeToEdge(view: View, darkTheme: Boolean) {
    val window = (view.context as Activity).window // ambil jendela aktivitas sekarang.
    WindowCompat.setDecorFitsSystemWindows(window, false) // mengatur warna status bar menjadi transparan
    window.statusBarColor = Color.Transparent.toArgb() // atur warna status bar menjadi transparan
    val navigationBarColor = when {
        Build.VERSION.SDK_INT >= 29 -> Color.Transparent.toArgb() // transparan untuk android 10+
        Build.VERSION.SDK_INT >= 26 -> Color(0xFF, 0xFF, 0xFF, 0x63).toArgb() 
        // semi-transparan untuk android 8.0-9.0
        else -> Color(0x00,0x00, 0x00, 0x50).toArgb() // semi-transparan untuk versi sdk 24 dan 25
    }
    window.navigationBarColor = navigationBarColor // atur warna bar navigasi
    val controller = WindowCompat.getInsetsController(window, view) // ambil controller untuk mengatur tampilan inset
    controller.isAppearanceLightStatusBars = !darkTheme // atur warna jadi terang
    controller.isAppearanceLightNavigationBars = !darkTheme // atur warna jadi gelap
}
