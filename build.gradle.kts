import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath("com.github.ben-manes:gradle-versions-plugin:0.36.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21-2")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.28-alpha")
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

apply(plugin = "com.github.ben-manes.versions")
tasks.named("dependencyUpdates", DependencyUpdatesTask::class.java).configure {
    resolutionStrategy {
        componentSelection {
            all {
                val rejected = listOf("alpha", "beta", "rc", "atlassian", "m1").any { qualifier ->
                    candidate.version.toLowerCase().contains(qualifier)
                }
                if (rejected) {
                    reject("Release candidate")
                }
            }
        }
    }
}

apply(from = "githooks.gradle")
