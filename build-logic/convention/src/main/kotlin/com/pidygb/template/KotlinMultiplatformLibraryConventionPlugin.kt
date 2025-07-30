@file:Suppress("unused")

package com.pidygb.template

import com.android.build.gradle.LibraryExtension
import com.pidygb.template.ext.alias
import com.pidygb.template.ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBuildType
import org.jetbrains.kotlin.konan.target.Family

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

                    }
                }
            }
            extensions.configure<LibraryExtension> {
                namespace = "com.pidygb.template." + path.split(":").drop(1).joinToString(".")
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