import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

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
