package com.alorma.dates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.alorma.dates.feature.DateListScreen
import com.alorma.dates.ui.DatesTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DatesTheme {
                DateListScreen()
            }
        }
    }
}