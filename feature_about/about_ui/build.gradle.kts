plugins {
    id("replacename.android.feature")
    id("replacename.android.library.compose")
}

dependencies {
    implementation(project(":feature_about:about_data"))
}
