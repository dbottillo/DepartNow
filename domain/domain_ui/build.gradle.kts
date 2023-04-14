plugins {
    id("replacename.android.library")
    id("replacename.android.library.compose")
    id("replacename.android.hilt")
}

android {
    buildFeatures {
        buildConfig = true
    }
    namespace = "com.dbottillo.replacename.domain"
}

dependencies {
    api(project(":core:core_ui"))
    api(project(":domain:domain_data"))
}
