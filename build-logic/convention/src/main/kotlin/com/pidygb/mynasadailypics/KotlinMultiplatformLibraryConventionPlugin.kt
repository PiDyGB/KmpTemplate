@file:Suppress("unused")

package com.pidygb.mynasadailypics

import com.android.build.gradle.LibraryExtension
import com.pidygb.mynasadailypics.ext.alias
import com.pidygb.mynasadailypics.ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            alias(libs.findPlugin("kotlinMultiplatform"))
            alias(libs.findPlugin("androidLibrary"))
            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget {
                    @OptIn(ExperimentalKotlinGradlePluginApi::class)
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_11)
                    }
                }

                listOf(
                    iosX64(),
                    iosArm64(),
                    iosSimulatorArm64()
                ).forEach { iosTarget ->
                    iosTarget.binaries.framework {
                        baseName = path.split(':').drop(1).joinToString("") {
                            it.replaceFirstChar(Char::uppercase)
                        }
                        isStatic = true
                    }
                }

                jvm("desktop")
            }
            extensions.configure<LibraryExtension> {
                namespace = "com.pidygb.mynasadailypics." + path.split(":").drop(1).joinToString(".")
                compileSdk = libs.findVersion("android-compileSdk").get().toString().toInt()
                defaultConfig {
                    minSdk = libs.findVersion("android-minSdk").get().toString().toInt()
                }

                testOptions {
                    targetSdk = libs.findVersion("android-targetSdk").get().toString().toInt()
                }
            }
        }
    }
}