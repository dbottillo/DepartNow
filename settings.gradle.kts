pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
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
