import org.gradle.api.artifacts.dsl.DependencyHandler

object Config {
    object Libs {
        val core = listOf(
            "androidx.core:core:${Versions.AndroidX.core}",
            "androidx.core:core-ktx:${Versions.AndroidX.core}",
            "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}",
            "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}",
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        )

        val test = listOf(
            "org.junit.jupiter:junit-jupiter-api:${Versions.junit}",
            "org.junit.jupiter:junit-jupiter-engine:${Versions.junit}",
            "org.junit.jupiter:junit-jupiter-params:${Versions.junit}",
            "org.mockito:mockito-core:${Versions.mockito}",
            "com.nhaarman:mockito-kotlin:${Versions.mockito_kotlin}",
            "org.mockito:mockito-android:${Versions.mockito_android}",
            "org.mockito:mockito-inline:${Versions.mockito}",
            "com.google.truth:truth:${Versions.truth}",
            "org.hamcrest:hamcrest-library:${Versions.hamcrest}",
            "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
        )
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.core() {
    Config.Libs.core.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.dagger() {
    add("implementation", "com.google.dagger:hilt-android:${Versions.dagger}")
    add("kapt", "com.google.dagger:hilt-android-compiler:${Versions.dagger}")
}

fun DependencyHandler.test() {
    Config.Libs.test.forEach { dependency ->
        add("testImplementation", dependency)
    }
}