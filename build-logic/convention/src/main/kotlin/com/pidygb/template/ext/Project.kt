package com.pidygb.template.ext
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.use.PluginDependency
import java.util.*

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")


fun Project.alias(notation: Optional<Provider<PluginDependency>>) {
    pluginManager.apply(notation.get().get().pluginId)
}
