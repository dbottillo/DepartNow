plugins {
    id("common-android-precompiled")
    id("common-dagger-precompiled")
}

android {
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(project(":core:core_ui"))
    api(project(":domain:domain_data"))
}
