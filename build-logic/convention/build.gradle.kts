@file:Suppress("MagicNumber")

plugins {
    `kotlin-dsl`
}

group = "com.dbottillo.replacename.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    jvmToolchain(11)
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "replacename.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "replacename.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "replacename.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "replacename.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "replacename.android.feature"
            implementationClass = "AndroidFeatureLibraryPlugin"
        }
        register("androidHilt") {
            id = "replacename.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }
        register("kotlinLibrary") {
            id = "replacename.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }
        register("kotlinFeatureLibrary") {
            id = "replacename.kotlin.feature.library"
            implementationClass = "KotlinFeatureLibraryConventionPlugin"
        }
    }
}
