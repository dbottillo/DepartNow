import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("org.jlleitschuh.gradle.ktlint") version BuildPluginsVersion.KTLINT
    id("io.gitlab.arturbosch.detekt") version BuildPluginsVersion.DETEKT
}

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.androidGradlePlugin}")
        classpath("com.github.ben-manes:gradle-versions-plugin:${Versions.gradlePlugin}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.dagger}")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
    extra.set("engBuild", project.findProperty("engBuild") ?: "true")
}

if (rootProject.extra.get("engBuild") == "true") {
    println("eng build pattern on!")
}

apply(from = "githooks.gradle")
apply(plugin = "com.github.ben-manes.versions")

subprojects {
    apply {
        plugin("io.gitlab.arturbosch.detekt")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    ktlint {
        debug.set(false)
        version.set(Versions.ktlint)
        verbose.set(true)
        android.set(false)
        outputToConsole.set(true)
        ignoreFailures.set(false)
        enableExperimentalRules.set(true)
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }

    detekt {
        config = rootProject.files("config/detekt/detekt.yml")
        reports {
            html {
                enabled = true
                destination = file("build/reports/detekt.html")
            }
        }
    }
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

fun isNonStable(version: String) = "^[0-9,.v-]+(-r)?$".toRegex().matches(version).not()
