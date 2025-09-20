import android.view.Window
import androidx.core.view.WindowInsetsControllerCompat

fun setStatusBarTextDark(window: Window, darkText: Boolean) {
    val decorView = window.decorView
    // API 23+
    val wic = WindowInsetsControllerCompat(window, decorView)
    wic.isAppearanceLightStatusBars = darkText
}