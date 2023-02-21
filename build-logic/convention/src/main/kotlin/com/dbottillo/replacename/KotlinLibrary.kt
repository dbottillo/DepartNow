package com.dbottillo.replacename

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.configureJavaAndKotlinVersions() {
    extensions.getByType<JavaPluginExtension>().apply {
        this.sourceCompatibility = JavaVersion.VERSION_11
        this.targetCompatibility = JavaVersion.VERSION_11
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}

fun Project.configureKapt() {
    extensions.getByType<KaptExtension>().apply {
        useBuildCache = true
    }
}

fun Project.registerTestTasks() {
    tasks.register("devTest") {
        dependsOn("test")
    }
    tasks.register("stagingTest") {
        dependsOn("test")
    }
    tasks.register("prodTest") {
        dependsOn("test")
    }
}

@Suppress("StringLiteralDuplication")
fun Project.addCoreDependencies() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    dependencies {
        "implementation"("javax.inject:javax.inject:1")
        "implementation"(libs.findLibrary("coroutines.core").get())
    }
}

fun Project.configureTest() {
    tasks.withType<Test> {
        useJUnitPlatform()
    }
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    dependencies {
        "implementation"(libs.findBundle("test").get())
        add("testRuntimeOnly", libs.findLibrary("junit.engine").get())
        add("lintChecks", project(":lint-rules"))
    }
}
