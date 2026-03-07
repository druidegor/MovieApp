package com.mleval.movie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mleval.movie.presentation.screens.singin.SingInScreen
import com.mleval.movie.presentation.ui.theme.MovieTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieTheme {
                SingInScreen(
                    onSkipClick = {

                    },
                    onForgetPasswordClick = {

                    },
                    onSingUpClick = {

                    }
                )
            }
        }
    }
}