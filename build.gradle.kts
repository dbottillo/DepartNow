import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
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
            jvmTarget = "11"
        }
    }

    withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_11.toString()
        targetCompatibility = JavaVersion.VERSION_11.toString()
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
        xml.enabled = false
        txt.enabled = false
        sarif.enabled = false
        html {
            enabled = true
            destination = file("build/reports/detekt.html")
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
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${BuildPluginsVersion.DETEKT}")
}
