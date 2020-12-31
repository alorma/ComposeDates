package com.alorma.dates.ui

import androidx.compose.material.AmbientContentAlpha
import androidx.compose.material.ContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableAmbient
import androidx.compose.runtime.Providers

@Composable
fun MediumEmphasis(content: @Composable () -> Unit) {
    ProvideAmbient(AmbientContentAlpha, ContentAlpha.medium, content)
}

@Composable
fun <T> ProvideAmbient(
    ambient: ProvidableAmbient<T>,
    alpha: T,
    content: @Composable () -> Unit,
) {
    Providers(
        ambient provides alpha,
        content = content,
    )
}