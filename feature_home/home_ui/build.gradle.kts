plugins {
    id("replacename.android.feature")
    id("replacename.android.library.compose")
    id("replacename.android.hilt")
}

dependencies {
    implementation(project(":feature_home:home_data"))
}
