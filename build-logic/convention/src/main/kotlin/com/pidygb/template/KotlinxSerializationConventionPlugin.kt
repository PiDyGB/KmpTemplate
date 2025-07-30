@file:Suppress("unused")

package com.pidygb.template

import com.pidygb.template.ext.alias
import com.pidygb.template.ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinxSerializationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            alias(libs.findPlugin("kotlinSerialization"))
            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets {
                    commonMain.dependencies {
                        implementation(libs.findLibrary("kotlinx-serialization-json").get())
                    }
                }
            }
        }
    }
}