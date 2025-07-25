plugins {
    alias(libs.plugins.mynasadailypics.kotlinMultiplatformLibrary)
    alias(libs.plugins.mynasadailypics.kotlinxSerialization)
    alias(libs.plugins.mynasadailypics.kotlinMultiplatformTest)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.model)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.napier)
            api(libs.bundles.ktor)
        }
        commonTest.dependencies {
            implementation(libs.ktor.client.mock)
        }
    }
}
