plugins {
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

dependencies {
    di()
}

// fix for Kotlin 1.5.20 bug https://youtrack.jetbrains.com/issue/KT-47416
kapt {
    javacOptions {
        // These options are normally set automatically via the Hilt Gradle plugin, but we
        // set them manually to workaround a bug in the Kotlin 1.5.20
        option("-Adagger.fastInit=ENABLED")
        option("-Adagger.hilt.android.internal.disableAndroidSuperclassValidation=true")
    }
}