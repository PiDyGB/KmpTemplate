plugins {
    alias(libs.plugins.template.kotlinMultiplatformLibrary)
    alias(libs.plugins.template.kotlinxSerialization)
    alias(libs.plugins.template.kotlinMultiplatformTest)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.model)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.napier)
            implementation(libs.bundles.ktor)
        }
        commonTest.dependencies {
            implementation(libs.ktor.client.mock)
        }
    }
}
