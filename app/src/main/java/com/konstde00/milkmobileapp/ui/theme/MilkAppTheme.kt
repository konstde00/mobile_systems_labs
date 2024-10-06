package com.konstde00.milkmobileapp.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun MilkAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = Typography(),
        content = content
    )
}
