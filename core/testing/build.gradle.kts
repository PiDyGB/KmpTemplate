plugins {
    alias(libs.plugins.template.kotlinComposeMultiplatformLibrary)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.sqldelight.driver)
            implementation(libs.androidx.test.runner)
        }

        commonMain.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.test)
            implementation(libs.koin.compose)
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines.extensions)
            implementation(libs.bundles.coil)
            implementation(libs.coil.test)
        }

        nativeMain.dependencies {
            implementation(libs.sqldelight.native.driver)
        }
    }
}
