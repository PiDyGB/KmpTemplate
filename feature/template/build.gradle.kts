plugins {
    alias(libs.plugins.template.kotlinComposeMultiplatformLibrary)
    alias(libs.plugins.template.kotlinxSerialization)
    alias(libs.plugins.template.kotlinComposeMultiplatformTest)
}

kotlin {

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.data)
            implementation(projects.core.model)
            implementation(projects.core.ui)
            implementation(projects.core.network)

            implementation(libs.bundles.coil)
        }
    }
}