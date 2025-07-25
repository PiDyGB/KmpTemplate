plugins {
    alias(libs.plugins.mynasadailypics.kotlinMultiplatformLibrary)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.mynasadailypics.kotlinMultiplatformTest)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.sqldelight.android.driver)
        }
        commonMain.dependencies {
            implementation(projects.core.model)
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
        create("PicturesDatabase") {
            packageName.set("com.pidygb.mynasadailypics.core.database")
        }
    }
}