buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.modulegraph)
    alias(libs.plugins.detekt)
    alias(libs.plugins.versions)
}

apply(from = "githooks.gradle")

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    rejectVersionIf {
        candidate.version.isNonStable()
    }
}

fun String.isNonStable(): Boolean = "^[0-9,.v-]+(-r)?$".toRegex().matches(this).not()

val detektAll by tasks.registering(io.gitlab.arturbosch.detekt.Detekt::class) {
    description = "Custom DETEKT task for all modules"
    parallel = true
    buildUponDefaultConfig = true
    autoCorrect = true
    setSource(files(projectDir))
    config.from(files(rootProject.files("config/detekt/detekt.yml")))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
    baseline.fileValue(rootProject.file("config/detekt/baseline.xml"))
    reports {
        xml.required.set(false)
        txt.required.set(false)
        sarif.required.set(false)
        html {
            required.set(true)
            outputLocation.set(file("build/reports/detekt.html"))
        }
    }
}

val detektAllBaseline by tasks.registering(io.gitlab.arturbosch.detekt.DetektCreateBaselineTask::class) {
    description = "Custom DETEKT build to build baseline for all modules"
    setSource(files(projectDir))
    baseline.fileValue(rootProject.file("config/detekt/baseline.xml"))
    config.from(files(rootProject.files("config/detekt/detekt.yml")))
    include("**/*.kt", "**/*.kts")
    exclude("**/resources/**", "**/build/**")
}

dependencies {
    detektPlugins(libs.detekt.formatting)
}

moduleGraphConfig {
    readmePath.set("./README.md")
    heading.set("### Dependency Diagram")
}
