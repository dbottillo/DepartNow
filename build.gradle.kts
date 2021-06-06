import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id("org.jlleitschuh.gradle.ktlint") version BuildPluginsVersion.KTLINT
    id("io.gitlab.arturbosch.detekt") version BuildPluginsVersion.DETEKT
    id("com.github.ben-manes.versions") version BuildPluginsVersion.VERSIONS_PLUGIN
}

allprojects {
    repositories {
        google()
        mavenCentral()
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
        enableExperimentalRules.set(false)
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

tasks {
    register("clean", Delete::class.java) {
        delete(rootProject.buildDir)
    }

    withType<DependencyUpdatesTask> {
        rejectVersionIf {
            candidate.version.isNonStable()
        }
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
    }
}

fun String.isNonStable(): Boolean = "^[0-9,.v-]+(-r)?$".toRegex().matches(this).not()
