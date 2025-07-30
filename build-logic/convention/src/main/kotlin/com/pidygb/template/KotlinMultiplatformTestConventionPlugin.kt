@file:Suppress("unused")

package com.pidygb.template

import com.android.build.gradle.BaseExtension
import com.pidygb.template.ext.alias
import com.pidygb.template.ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinMultiplatformTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            alias(libs.findPlugin("mokkery"))
            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets {
                    commonTest.dependencies {
                        implementation(project(":core:testing"))
                        implementation(libs.findLibrary("kotlin-test").get())
                        implementation(libs.findLibrary("kotlinx-coroutines-test").get())

                        implementation(dependencies.platform(libs.findLibrary("koin-bom").get()))
                        implementation(libs.findLibrary("koin-test").get())
                    }
                }
            }
            extensions.configure<BaseExtension> {
                testOptions {
                    unitTests {
                        all {
                            it.exclude("**/ui/**")
                        }
                    }
                }
            }
            dependencies {
                "implementation"(libs.findLibrary("mokkery").get())
            }
        }
    }
}