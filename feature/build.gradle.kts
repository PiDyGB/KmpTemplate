import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.Family

plugins {
    alias(libs.plugins.template.kotlinComposeMultiplatformLibrary)
    alias(libs.plugins.skie)
}

kotlin {

    targets.withType<KotlinNativeTarget>().configureEach {
        if (konanTarget.family == Family.IOS) {
            binaries.withType<Framework>().configureEach {
                linkerOpts.add("-lsqlite3")
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core.ui)
            api(projects.feature.template)
        }
    }

    targets.withType<KotlinNativeTarget> {
        binaries.withType<Framework> {
            export(projects.core.ui)
            export(projects.feature.template)
        }
    }
}