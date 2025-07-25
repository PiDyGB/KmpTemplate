@file:Suppress("unused")

package com.pidygb.mynasadailypics

import com.pidygb.mynasadailypics.ext.alias
import com.pidygb.mynasadailypics.ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinComposeMultiplatformLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            alias(libs.findPlugin("composeMultiplatform"))
            alias(libs.findPlugin("composeCompiler"))
            alias(libs.findPlugin("mynasadailypics-kotlinMultiplatformLibrary"))

            val composeDeps = extensions.getByType(ComposeExtension::class.java).dependencies
            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets {
                    commonMain.dependencies {
                        implementation(composeDeps.runtime)
                        implementation(composeDeps.material3)
                        implementation(composeDeps.components.resources)
                        implementation(composeDeps.components.uiToolingPreview)


                        implementation(dependencies.platform(libs.findLibrary("koin-bom").get()))
                        implementation(libs.findLibrary("koin-core").get())
                        implementation(libs.findLibrary("koin-compose").get())
                        implementation(libs.findLibrary("koin-compose-viewmodel").get())
                        implementation(libs.findLibrary("koin-compose-viewmodel-navigation").get())
                    }
                }
                androidTarget {
                    @OptIn(ExperimentalKotlinGradlePluginApi::class)
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_11)
                    }
                }
                dependencies {
                    "debugImplementation"(composeDeps.uiTooling)
                }
            }
        }
    }
}