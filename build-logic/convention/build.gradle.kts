import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.pidygb.template.buildlogic"

// Configure the build-logic plugins to focus on JDK 17
// This matches the JDK used to build the project and is not related to what is running on a device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {

        register("kotlinMultiplatformLibrary") {
            id = libs.plugins.template.kotlinMultiplatformLibrary.get().pluginId
            implementationClass = "com.pidygb.template.KotlinMultiplatformLibraryConventionPlugin"
        }

        register("kotlinComposeMultiplatformLibrary") {
            id = libs.plugins.template.kotlinComposeMultiplatformLibrary.get().pluginId
            implementationClass = "com.pidygb.template.KotlinComposeMultiplatformLibraryConventionPlugin"
        }

        register("kotlinMultiplatformTest") {
            id = libs.plugins.template.kotlinMultiplatformTest.get().pluginId
            implementationClass = "com.pidygb.template.KotlinMultiplatformTestConventionPlugin"
        }

        register("kotlinComposeMultiplatformTest") {
            id = libs.plugins.template.kotlinComposeMultiplatformTest.get().pluginId
            implementationClass = "com.pidygb.template.KotlinComposeMultiplatformTestConventionPlugin"
        }

        register("kotlinxSerialization") {
            id = libs.plugins.template.kotlinxSerialization.get().pluginId
            implementationClass = "com.pidygb.template.KotlinxSerializationConventionPlugin"
        }
    }
}