@file:Suppress("unused")

package com.pidygb.mynasadailypics

import com.pidygb.mynasadailypics.ext.alias
import com.pidygb.mynasadailypics.ext.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinSerializationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            alias(libs.findPlugin("kotlinSerialization"))
            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets {
                    commonMain.dependencies {
                        implementation(libs.findLibrary("kotlinx.serialization.json").get())
                    }
                }
            }
        }
    }
}