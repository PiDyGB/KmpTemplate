package com.pidygb.template.core.ui

import androidx.compose.foundation.background
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIViewController

@OptIn(ExperimentalComposeUiApi::class, ExperimentalForeignApi::class)
fun CommonTextViewController(label: String, onSizeChanged: (width: Int, height: Int) -> Unit): UIViewController =
    ComposeUIViewController {
        CommonText(
            label = label,
            modifier = Modifier.background(Color.Magenta).onGloballyPositioned { coordinates ->
                // Report the measured size back to SwiftUI
                onSizeChanged(
                    coordinates.size.width,
                    coordinates.size.height
                )
            }
        )
    }