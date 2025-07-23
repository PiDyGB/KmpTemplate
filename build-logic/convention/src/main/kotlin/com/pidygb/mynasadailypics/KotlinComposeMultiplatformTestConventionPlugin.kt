@file:Suppress("unused")

package com.pidygb.mynasadailypics

import com.android.build.api.dsl.LibraryExtension
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
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

class KotlinComposeMultiplatformTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            alias(libs.findPlugin("mynasadailypics-kotlinMultiplatformTest"))
            val composeDeps = extensions.getByType(ComposeExtension::class.java).dependencies
            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets {
                    commonTest.dependencies {
                        @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                        implementation(composeDeps.uiTest)
                    }

                    // Adds the desktop test dependency
                    findByName("desktopTest")?.dependencies {
                        implementation(composeDeps.desktop.currentOs)
                    }
                }
                androidTarget {
                    @OptIn(ExperimentalKotlinGradlePluginApi::class)
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_11)
                    }
                    @OptIn(ExperimentalKotlinGradlePluginApi::class)
                    instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)

                    dependencies {
                        "androidTestImplementation"(composeDeps.desktop.uiTestJUnit4)
                        "debugImplementation"(libs.findLibrary("androidx-uiTestManifest").get())
                    }
                }
            }
            extensions.configure<LibraryExtension> {
                defaultConfig {
                    testInstrumentationRunner = "com.pidygb.mynasadailypics.core.testing.TestRunner"
                }
            }
        }
    }
}