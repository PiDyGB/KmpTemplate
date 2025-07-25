import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.konan.target.Family

plugins {
    alias(libs.plugins.mynasadailypics.kotlinMultiplatformLibrary)
    alias(libs.plugins.mynasadailypics.kotlinMultiplatformTest)
}

kotlin {

    targets.withType<KotlinNativeTarget>().configureEach {
        if (konanTarget.family == Family.IOS) {
            binaries.getTest(NativeBuildType.DEBUG).linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.common)
            implementation(projects.core.network)
            implementation(projects.core.model)
            implementation(projects.core.database)
            implementation(libs.kotlinx.coroutines.core)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
        }
    }
}
