plugins {
    id("replacename.android.feature")
    id("replacename.android.library.compose")
}

dependencies {
    implementation(project(":core:core_ui"))
    implementation(project(":domain:domain_ui"))
    implementation(project(":feature_about:about_data"))

    implementation(libs.bundles.compose.ui)
}
