plugins {
    id("replacename.android.library")
    id("replacename.android.library.compose")
    id("replacename.android.hilt")
}

android {
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(project(":core:core_ui"))
    api(project(":domain:domain_data"))

    implementation(libs.bundles.compose.ui)
}
