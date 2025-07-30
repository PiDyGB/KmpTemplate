plugins {
    alias(libs.plugins.template.kotlinComposeMultiplatformLibrary)
    alias(libs.plugins.template.kotlinComposeMultiplatformTest)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            // Ktor client dependency required for Coil
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(libs.bundles.coil)
            implementation(libs.kotlinx.coroutines.core)
        }
        nativeMain.dependencies {
            // Ktor client dependency required for iOS
            implementation(libs.ktor.client.darwin)
        }
    }
}
