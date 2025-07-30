import org.jetbrains.kotlin.gradle.plugin.mpp.Framework
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.template.kotlinMultiplatformLibrary)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.template.kotlinMultiplatformTest)
    alias(libs.plugins.template.kotlinxSerialization)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.sqldelight.android.driver)
        }
        commonMain.dependencies {
            implementation(projects.core.model)
            implementation(projects.core.common)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.sqldelight.runtime)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.sqldelight.coroutines.extensions)
        }

        nativeMain.dependencies {
            implementation(libs.sqldelight.native.driver)
        }
    }
}

sqldelight {
    databases {
        create("TemplateDatabase") {
            packageName.set("com.pidygb.template.core.database")
        }
    }
}