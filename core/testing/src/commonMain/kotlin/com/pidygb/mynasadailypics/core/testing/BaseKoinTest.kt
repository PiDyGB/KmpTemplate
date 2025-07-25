package com.pidygb.mynasadailypics.core.testing

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import coil3.ColorImage
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.annotation.DelicateCoilApi
import coil3.compose.LocalPlatformContext
import coil3.test.FakeImageLoaderEngine
import org.koin.compose.KoinApplication
import org.koin.core.KoinApplication
import org.koin.dsl.KoinAppDeclaration

expect fun KoinApplication.startModules()


private val engine = FakeImageLoaderEngine.Builder()
    .intercept({ it is String }, ColorImage(Color.Green.toArgb()))
    .default(ColorImage(Color.Blue.toArgb()))
    .build()

@OptIn(DelicateCoilApi::class)
@Composable
private fun SetupImageLoader() {
    val imageLoader = ImageLoader.Builder(LocalPlatformContext.current)
        .components { add(engine) }
        .build()
    SingletonImageLoader.setUnsafe(imageLoader)
}

@Composable
fun SetupApplication(
    application: KoinAppDeclaration,
    content: @Composable () -> Unit
) {
    KoinApplication(application = {
        startModules()
        application()
    }) {
        SetupImageLoader()
        content()
    }
}
