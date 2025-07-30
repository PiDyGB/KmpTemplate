package com.pidygb.template.core.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CommonText(label: String, modifier: Modifier = Modifier) {
    Text(
        text = label,
        modifier = modifier
            .padding(16.dp)
    )
}