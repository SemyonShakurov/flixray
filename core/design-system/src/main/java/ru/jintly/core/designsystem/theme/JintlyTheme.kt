package ru.jintly.core.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.jintly.core.designsystem.colors.Background

@Composable
fun JintlyTheme(
    content: @Composable () -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(Background)
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background),
        ) {
            content()
        }
    }
}
