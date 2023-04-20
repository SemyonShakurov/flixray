package com.jintly.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jintly.app.ui.App
import dagger.hilt.android.AndroidEntryPoint
import ru.jintly.core.data.utils.NetworkMonitor
import ru.jintly.core.designsystem.theme.JintlyTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            JintlyTheme {
                App(networkMonitor = networkMonitor)
            }
        }
    }
}
