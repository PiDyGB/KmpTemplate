plugins {
    alias(libs.plugins.template.kotlinMultiplatformLibrary)
    alias(libs.plugins.template.kotlinMultiplatformTest)
    alias(libs.plugins.template.kotlinxSerialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }
        commonTest.dependencies {
            implementation(libs.turbine)
        }
    }
}
