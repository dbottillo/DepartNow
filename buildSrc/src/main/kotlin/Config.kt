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

    object Android {
        const val compileSdk = 23
        const val buildTools = "29.0.2"
        const val minSDk = 23
        const val targetSdk = 29
        const val versionCode = 1
        const val versionName = "0.0.1"
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

fun DependencyHandler.di() {
    add("implementation", "com.google.dagger:hilt-android:${Versions.dagger}")
    add("kapt", "com.google.dagger:hilt-android-compiler:${Versions.dagger}")
}

fun DependencyHandler.test() {
    Config.Libs.test.forEach { dependency ->
        add("testImplementation", dependency)
    }
}

fun DependencyHandler.network() {
    add("implementation", "com.squareup.okhttp3:okhttp:${Versions.OkHttp.core}")
    add("implementation", "com.squareup.okhttp3:logging-interceptor:${Versions.OkHttp.logging}")
    add("implementation", "com.squareup.retrofit2:retrofit:${Versions.Retrofit.core}")
    add("implementation", "com.squareup.retrofit2:converter-gson:${Versions.Retrofit.gsonConverter}")
}

fun DependencyHandler.ui() {
    add("implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}")
    add("implementation", "androidx.hilt:hilt-lifecycle-viewmodel:${Versions.hilt}")
    add("kapt", "com.google.dagger:hilt-android-compiler:${Versions.dagger}")
    add("implementation", "androidx.appcompat:appcompat:${Versions.AndroidX.compat}")
    add("implementation", "androidx.cardview:cardview:${Versions.AndroidX.cardview}")
    add("implementation", "androidx.recyclerview:recyclerview:${Versions.AndroidX.recyclerview}")
    add("implementation", "com.google.android.material:material:${Versions.material}")
    add("implementation", "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}")
    add("implementation", "androidx.preference:preference:${Versions.AndroidX.preference}")
    add("implementation", "androidx.navigation:navigation-fragment-ktx:${Versions.AndroidX.navigation}")
    add("implementation",  "androidx.navigation:navigation-ui-ktx:${Versions.AndroidX.navigation}")
}

fun DependencyHandler.lifecycle() {
    add("implementation", "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.lifecycle}")
    add("implementation", "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.AndroidX.lifecycle}")
    add("implementation", "androidx.lifecycle:lifecycle-common-java8:${Versions.AndroidX.lifecycle}")
    add("implementation", "androidx.lifecycle:lifecycle-extensions:${Versions.AndroidX.lifecycle}")
}