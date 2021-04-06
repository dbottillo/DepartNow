plugins {
    id("common-android-precompiled")
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
