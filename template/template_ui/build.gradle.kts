plugins {
    id("replacename.android.feature")
    id("replacename.android.library.compose")
    id("replacename.android.hilt")
}

dependencies {
    implementation(project(":feature_{FEATURE_NAME}:{FEATURE_NAME}_data"))
}
