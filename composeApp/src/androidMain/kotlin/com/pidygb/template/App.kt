package com.pidygb.template

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.pidygb.template.feature.template.Template
import com.pidygb.template.feature.template.TemplateSurface
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val backStack = remember { mutableStateListOf<Any>(Template) }

        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryProvider = { key ->
                when (key) {
                    is Template -> NavEntry(key) { TemplateSurface { } }
                    else -> NavEntry(Unit) { Text("Unknown route") }
                }
            }
        )
    }
}