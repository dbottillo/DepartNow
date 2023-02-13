plugins {
    id("replacename.android.feature")
    id("replacename.android.library.compose")
    id("replacename.android.hilt")
}

dependencies {
    implementation(project(":core:core_ui"))
    implementation(project(":domain:domain_ui"))
    implementation(project(":feature_home:home_data"))

    implementation(libs.bundles.compose.ui)
}
