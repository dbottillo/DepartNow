pluginManagement {
    includeBuild("build-logic")
    /*resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.android.library") {
                useModule("com.android.tools.build:gradle:${requested.version}")
            }
            if (requested.id.id == "com.android.application") {
                useModule("com.android.tools.build:gradle:${requested.version}")
            }
        }
    }*/
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "ReplaceName"

include(":app")
include(":core:core_data", ":domain:domain_data", ":core:core_ui", ":domain:domain_ui", ":lint-rules")

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
rootDir.listFiles()
    .filter { it.name.startsWith("feature_") }
    .flatMap { it.listFiles().toList() }
    .filter { it.isDirectory && it.isHidden.not() }
    .forEach { include("${it.parentFile.name}:${it.name}") }
